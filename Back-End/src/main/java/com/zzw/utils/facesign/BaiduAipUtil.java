package com.zzw.utils.facesign;


import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "ai")
public class BaiduAipUtil {

    @Value("${ai.appId}")
    private String appId;
    @Value("${ai.apiKey}")
    private String apiKey;
    @Value("${ai.secretKey}")
    private String secretKey;
    @Value("${ai.imageType}")
    private String imageType;
    @Value("${ai.groupId}")
    private String groupId;
    private AipFace client;
    private HashMap<String,String> map;
    public BaiduAipUtil(){
        //在使用这个对象前一定要把配置项填好喽，不然构造函数就会出错了...
        //这句代码真的是天坑！，因为spring管理类是确实是通过构造方法实现new出一个对象来的，
        //然后再给这个对象里面的属性通过application.yaml配置赋值的，所以在构造函数中拿到的this.appId等
        //属性都TM是空的，尼玛的草了！！！
//      this.client = new AipFace( this.appId,this.apiKey,this.secretKey);
        this.map = new HashMap<>();
        map.put("quality_control","NORMAL");
        map.put("liveness_control","LOW");
    }
    //当属性自动注入完成后就会被调用执行。。。
    @PostConstruct
    public void init(){
        this.client = new AipFace(this.appId,this.apiKey,this.secretKey);
    }

    //注册用户接口：参数一是原图的绝对路径，参数二是用户的在数据库中独一无二的id号
    public Boolean faceRegister(String origin,String uid){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(origin));
            String imgBase64 = Base64Util.encode(bytes);
            JSONObject object = client.addUser(imgBase64,imageType,groupId,uid,map);
            System.out.println(object.toString(2));
            return object.getString("error_msg").compareTo("SUCCESS")==0?true:false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    //人脸检测（图片的传递方式是通过origin路径）
    public Boolean isFaceImg(String origin){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(origin));
            return aiJudge(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //人脸检测方法重载（图片的传递方式是file文件）
    public Boolean isFaceImg(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            return aiJudge(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean aiJudge(byte[] bytes) {
        String img= Base64Util.encode(bytes);
        JSONObject object = client.detect(img,imageType,null);
        if(object.getString("error_msg").compareTo("pic not has face")!=0){
            //不仅要保证它是一张头像图片，还要保证头像的数量是1个捏哈哈
            return object.getJSONObject("result").getInt("face_num") == 1?true:false;
        }else{
            return false;
        }

    }

    public Boolean isFaceImgByBase64(String imgBase64){
        JSONObject object = client.detect(imgBase64,imageType,null);
        if(object.getString("error_msg").compareTo("pic not has face")!=0){
            //不仅要保证它是一张头像图片，还要保证头像的数量是1个捏哈哈
            return object.getJSONObject("result").getInt("face_num") == 1?true:false;
        }else{
            return false;
        }
    }



    //人脸库更新
    public Boolean faceUpdate(String origin,String uid){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(origin));
            String imgBase64 = Base64Util.encode(bytes);
            JSONObject object = client.updateUser(imgBase64,imageType,groupId,uid,map);
            return object.getInt("error_code") == 0?true:false;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    //核心的方法封装
    public String faceSearchByBase64(String imgBase64){
        JSONObject object = client.search(imgBase64,imageType,groupId,null);
        if(object.getInt("error_code") == 0){
            JSONObject subObj = object.getJSONObject("result");
            JSONArray userList = subObj.getJSONArray("user_list");
            if(userList.length() > 0){
                JSONObject aUser = userList.getJSONObject(0);
                Double score = aUser.getDouble("score");
                return score > 80?aUser.getString("user_id"):null;
            }
        }
        return null;
    }

    //加一层封装
    public String faceSearchByBytes(byte[] bytes){
        String imgBase64 = Base64Util.encode(bytes);
        return faceSearchByBase64(imgBase64);
    }

    //再加一层封装
    public String faceSearchByOrigin(String origin){
        try{
            byte[] bytes = Files.readAllBytes(Paths.get(origin));
            return faceSearchByBytes(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String faceSearchByFile(MultipartFile file){
        try{
            byte[] bytes = file.getBytes();
            return faceSearchByBytes(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    //删除指定id的人脸记录
    public Boolean faceDelete(String uid){

        JSONObject object = client.deleteUser(groupId,uid,null);
        if(object.getInt("error_code") == 0){
            return true;
        }
        return false;
    }



}

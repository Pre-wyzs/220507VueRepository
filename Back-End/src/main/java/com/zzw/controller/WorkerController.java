package com.zzw.controller;

import com.zzw.entity.Worker;
import com.zzw.service.WorkerService;
import com.zzw.utils.GeneralSelectUtils;
import com.zzw.utils.Result;
import com.zzw.utils.facesign.BaiduAipUtil;
import com.zzw.utils.jwttoken.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/worker/info")
public class WorkerController {

    @Autowired
    WorkerService workerService;
    @Autowired
    BaiduAipUtil baiduAipUtil;


    @GetMapping("/all")
    @CrossOrigin
    public Result selectAll(){
        return Result.success(workerService.selectAll());
    }

    @PutMapping("/general")
    @CrossOrigin
    public Result generalSelectInterface(@RequestBody Worker worker){

        Map<String,Object> map = new HashMap<>();
        synchronized (map){
            map.put("name",worker.getName());
            map.put("age",worker.getAge());
            map.put("phone",worker.getPhone());
            map.put("team",worker.getTeam());
            map.put("enterprise",worker.getEnterprise());
            map.put("status",worker.getStatus());
            map.put("idCard",worker.getIdCard());
            map.put("kind",worker.getKind());
            map.put("startIndex",(worker.getPageNum()- 1)*worker.getPageSize());
            map.put("pageSize",worker.getPageSize());
        }

        Integer totalPages = GeneralSelectUtils.setTotalPages(workerService.generalSelectInterfaceNum(map),worker.getPageSize());
        List<Worker> workers = workerService.generalSelectInterface(map);
        return Result.success(workers,totalPages);
    }

    @PostMapping("/add")
    @CrossOrigin
    public Result addWorker(@RequestBody Worker worker){
        String token = worker.getToken();
        Claims claims = JwtUtils.tokenParse(token);
        Worker tmp = null;
        if(claims.get("power").toString().compareTo("0") == 0){
            workerService.addWorker(worker);
            //因为点击添加了以后，生成的id一定是最大的，要从sql中查找出id和图片的路径，发送给百度人脸库才行
            tmp = workerService.selectIdByPhoto(worker.getPhoto());
            //把id和img存储到人脸库
            Boolean bool = baiduAipUtil.faceRegister(tmp.getPhoto(),tmp.getId().toString());
            if(bool){
                return Result.success();
            }
            return Result.error("-2","存储到人脸库失败!");
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }


    //通过excel一次性导入多条数据
    @PostMapping("/adds")
    @CrossOrigin
    public Result addWorkers(@RequestBody List<Worker> workerList){

        /**
         * 对于从excel导入数据来说应该不需要用户的权限认证的，直接把token校验去除掉就行了。
         * */

        /**
         * 不过在加入到数据库中之前，还有一件事情就是把excel表中的图片的路径存储到服务器的目标文件夹中
         * */
        for (Worker worker : workerList) {
            //获取原图片的路径
            String originPath = worker.getPhoto();
            try {
                MultipartFile file = getMultipartFile(originPath);
                //把图片传递到服务器指定的文件夹上
                Result uploadRet = imgFileUpload(file);

                //修改图片路径为上传后的新路径
                worker.setPhoto((String)uploadRet.getData());

                //然后把这个对象存储到数据库中去
                workerService.addWorker(worker);

                //然后查询出生成的id
                Worker tmp = workerService.selectIdByPhoto(worker.getPhoto());

                //然后把图片存储到人脸库中
                Boolean bool = baiduAipUtil.faceRegister(tmp.getPhoto(),tmp.getId().toString());
            } catch (IOException e) {
                e.printStackTrace();
                //打印错误的日志
                System.out.println(e.getMessage());
            }
        }
        return Result.success();
    }

    /**
     * 通过文件路径把获取multipartFile对象的方法
     * */
    public MultipartFile getMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                "application/sql", fileInputStream);
        long size = multipartFile.getSize();
        System.out.println("转储成功,size="+size);
        return multipartFile;
    }

    /**
     * 因为上传文件到服务器的接口不能直接在另一个函数中调用，所以直接copy一份，调用这个接口就行了。
     * */
    public Result imgFileUpload(MultipartFile file){

        String originName = file.getOriginalFilename(); //获取上传上来的文件的原始文件名
        if(!originName.endsWith(".jpg")){  //如果不是：以.jpg结尾，那就直接返回上传的格式有问题
            return Result.error("-1,","图片格式不正确!");
        }

        //在通过jar包执行的时候。。。这段代码的是相对的位置，所以FileStorage文件夹的位置就会变掉的。
        String ProjectPath = System.getProperty("user.dir");
        String savePath = ProjectPath.substring(0,ProjectPath.lastIndexOf('\\')+1)+"FileStorage";


        File folder = new File(savePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String newName = UUID.randomUUID().toString() + ".jpg";
        try{
            /**1、最核心的函数就是这个*/
            file.transferTo(new File(folder,newName));
            String realPath = savePath+"\\"+newName;
            return  Result.success(realPath);
        }catch (IOException e){
            e.printStackTrace();
            return  Result.error("-1",e.getMessage());  //异常居然还有这个函数，哈哈挺好用的学到了
        }
    }


    //图片上传接口：参考教程:https://www.bilibili.com/video/BV1ei4y1M7Kf/?spm_id_from=333.788.recommend_more_video.-1
    @PostMapping("/upload")
    @CrossOrigin
    public Result fileUpload(MultipartFile file,HttpServletRequest req){
        return imgFileUpload(file);
    }

    @PostMapping("/update")
    @CrossOrigin
    public Result updateWorker(@RequestBody Worker worker){
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = worker.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);
        /** 只有超级管理员和普通管理员可以去修改员工的信息捏*/
        String power = claims.get("power").toString();
        if(power.compareTo("0")==0 || power.compareTo("1")==0){
            workerService.updateWorker(worker);

            //顺便更新一下百度云AI的人脸识别库中的照片
            baiduAipUtil.faceUpdate(worker.getPhoto(),worker.getId().toString());

            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息

    }



    @PostMapping("/del")  //restful风格的参数传递
    @CrossOrigin
    public Result deleteWorker(@RequestBody Worker worker) {
        /**
         * 接受并解析前端传过来的token令牌。
         * */
        String token = worker.getToken();
        //token解析
        Claims claims = JwtUtils.tokenParse(token);

        if(claims.get("power").toString().compareTo("0") == 0){
            workerService.delWorker(worker.getId());

            //顺带把人脸库中的记录也一起删除掉
            baiduAipUtil.faceDelete(worker.getId().toString());

            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息

    }

    //批量删除多条记录
    @PostMapping("/dels")
    @CrossOrigin
    public Result delWorkers(@RequestBody String[] ids){
        /**
         * 接受并解析前端传过来的token令牌。
         * */

        System.out.println(ids);
        Claims claims = JwtUtils.tokenParse(ids[ids.length-1]);

        /** 同样只有超级管理和普通管理有权限进行设备历史记录的删除捏*/
        if(claims.get("power").toString().compareTo("2") != 0){
            for(int i=0;i<ids.length-1;i++){
                workerService.delWorker(Integer.parseInt(ids[i]));

                //顺带把人脸库中的记录也一起删除掉
                baiduAipUtil.faceDelete(ids[i]);

            }
            return Result.success();
        }
        return Result.error("-1","权限不够");  //返回给前端插入成功或失败的信息
    }

}

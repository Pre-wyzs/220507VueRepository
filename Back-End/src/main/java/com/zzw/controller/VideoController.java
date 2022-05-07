package com.zzw.controller;


import com.zzw.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
//视频录制上传处理的api
@RequestMapping("/api/video/info")
public class VideoController {


    @PostMapping("/upload")
    @CrossOrigin
    public Result uploadVideoFile(MultipartFile file){
        return videoFileUpload(file);
    }


    /**
     * 因为上传文件到服务器的接口不能直接在另一个函数中调用，所以直接copy一份，调用这个接口就行了。
     * */
    private Result videoFileUpload(MultipartFile file){

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
        String originName = file.getOriginalFilename(); //获取上传上来的文件的原始文件名
        /**
         * 可以接受.mp4 .mkv和.webm等多种视频的格式的
         * */
//        if(!originName.endsWith(".mkv")){  //如果不是.mp4格式的话说明不是视频文件，直接返回存储失败捏!
//            return Result.error("-1,","视频文件格式不正确!");
//        }

        String ProjectPath = System.getProperty("user.dir");
        String savePath = ProjectPath.substring(0,ProjectPath.lastIndexOf('\\')+1)+"VideoStorage";
        File folder = new File(savePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String newName = sdf.format(date) + ".mkv";
        try{
            /**1、最核心的函数就是这个*/
            file.transferTo(new File(folder,newName));
//            String realPath = savePath+"\\"+newName;
            return  Result.success();
        }catch (IOException e){
            e.printStackTrace();
            return  Result.error("-1",e.getMessage());  //异常居然还有这个函数，哈哈挺好用的学到了
        }
    }



}

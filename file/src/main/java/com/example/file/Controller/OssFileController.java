package com.example.file.Controller;

import com.example.file.Mapper.FileMapper;
import com.example.file.Utils.AliOssUtil;
import com.example.file.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/file/oss")
public class OssFileController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {

        log.info("文件上传------------");

//        try{
//            // 获取原文件名以及后缀
//            String originalFilename = file.getOriginalFilename();
//            String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
//            // 创建新的文件名
//            String fileName =  UUID.randomUUID().toString()+back;
//            String upload = aliOssUtil.upload(file.getBytes(), fileName);
//            log.info("文件上传到{}",upload);
//            System.out.println(originalFilename + " " + fileName);
//            fileMapper.InsertOss(originalFilename,fileName);
//        }catch (Exception e){
//            return Result.error("文件上传失败");
//        }
            // 获取原文件名以及后缀
            String originalFilename = file.getOriginalFilename();
            String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
            // 创建新的文件名
            String fileName =  UUID.randomUUID().toString()+back;
            String upload = aliOssUtil.upload(file.getBytes(), fileName);
            log.info("文件上传到{}",upload);
            System.out.println(originalFilename + " " + fileName);
            fileMapper.InsertOss(originalFilename,fileName);

        return Result.success(upload);
    }

    /**
     * 文件删除
     */
    @PostMapping("/delete")
    public Result<String> delete(MultipartFile file){
        String FromName = file.getOriginalFilename();
        String ToName = fileMapper.SelectToNameOss(FromName);
        fileMapper.DeleteFileOss(FromName);
        System.out.println(FromName + " " + ToName);
        return aliOssUtil.delete(ToName);
    }
}

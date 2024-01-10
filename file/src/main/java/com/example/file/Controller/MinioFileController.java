package com.example.file.Controller;

import com.example.file.Mapper.FileMapper;
import com.example.file.Service.FileStorageService;
import com.example.file.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file/minio")
public class MinioFileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        // 获取原文件名以及后缀
        String originalFilename = file.getOriginalFilename();
        String back = originalFilename.substring(originalFilename.lastIndexOf('.'));
        // 创建新的文件名
        String fileName =  UUID.randomUUID().toString()+back;
        String filePath = fileStorageService.uploadImgFile("", fileName, file.getInputStream());
        fileMapper.InsertMinio(originalFilename,filePath);
        System.out.println(filePath);
        return Result.success(filePath);
    }

    @PostMapping("/delete")
    public Result<String> delete(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String url = fileMapper.SelectToNameMinio(originalFilename);
        fileStorageService.delete(url);
        fileMapper.DeleteFileMinio(originalFilename);
        return Result.success();
    }

}

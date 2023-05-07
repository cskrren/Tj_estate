package com.rkr.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@PropertySource(value = {"classpath:application.properties"})
public class UploadUtils {

    @Value("${savePath}")
    private String savePath;

    /**
     * @description: 通过url获取输入流
     * @param file
     * @return String urlPath
     */
    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File dest = new File(savePath + fileName);
        System.out.println(savePath + fileName);
        if(!dest.exists()){
            //先得到文件的上级目录，并创建上级目录，在创建文件
            dest.getParentFile().mkdir();
            try {
                //创建文件
                dest.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.transferTo(dest);
            String url = "http://localhost:8082/images/" + fileName;
            return url;
        }
        catch (IllegalStateException e) {
            throw e;
        }
    }
}
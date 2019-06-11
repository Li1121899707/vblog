package com.hitwh.vblog.controller;

import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.CommonReturnType;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.util.MyMd5;
import com.hitwh.vblog.util.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class CosController {

    @Autowired
    QCloudUtil qCloudUtil;

    @RequestMapping("/upload_file")
    public CommonReturnType uploadImage(@RequestParam("image") MultipartFile multfile)throws Exception{
        if(multfile == null){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);
        }
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        String virtualFileName = MyMd5.md5Encryption(fileName + System.currentTimeMillis()) +  prefix;
        // 用uuid作为文件名，防止生成的临时文件重复
        final File tempFile = File.createTempFile("imagesFile-"+System.currentTimeMillis(), prefix);
        // 将MultipartFile转为File
        multfile.transferTo(tempFile);

        //调用腾讯云工具上传文件
        CommonReturnType result = qCloudUtil.uploadFile(tempFile,"image/" + virtualFileName);
        //程序结束时，删除临时文件
        deleteFile(tempFile);
        //返回图片url
        return result;
    }



    /* 删除临时文件*/
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
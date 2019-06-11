package com.hitwh.vblog.util;
import com.hitwh.vblog.response.CommonReturnType;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class QCloudUtil {

    @Value("${qcloud.appID}")
    private Long appID;
    @Value("${qcloud.secretId}")
    private String secretId;
    @Value("${qcloud.secretKey}")
    private String secretKey;
    @Value("${qcloud.bucketName}")
    private String bucketName;
    @Value("${qcloud.region}")
    private String region;

    /**
     * 上传文件到腾讯云
     * @return
     */
    public CommonReturnType uploadFile(File file, String key){
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照
        // https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        // 指定要上传到 COS 上的路径

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
//        Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);
//        URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
        String url="https://"+bucketName+".cos."+region+".myqcloud.com/"+key;
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        return CommonReturnType.create(map);
    }

}

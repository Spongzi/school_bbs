package com.spongzi.utlis;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.spongzi.exception.BlogException;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.RequestBody.Companion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static com.spongzi.constant.GlobalConstant.IMAGE_DOMAIN;

/**
 * @author spongzi
 * @date 2022/11/07
 */
@Log
@Component
public class UploadImage {

    @Value("${qiqiu.accessKey}")
    String accessKey;

    @Value("${qiqiu.secretKey}")
    String secretKey;

    @Value("${qiqiu.bucket}")
    String bucket;


    /**
     * 上传
     *
     * @param file 文件
     * @param key  默认不指定key的情况下，以文件内容的hash值作为文件名
     * @return {@link String}
     */
    public String upload(MultipartFile file, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);


        try {
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return IMAGE_DOMAIN + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.warning(r.toString());
                try {
                    log.warning(r.bodyString());
                } catch (QiniuException ex2) {
                    throw new BlogException(401, ex2.error());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new BlogException(401, ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BlogException(401, e.getMessage());
        }
        return key;
    }

}

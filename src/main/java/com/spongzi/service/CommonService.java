package com.spongzi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 公共服务
 *
 * @author spongzi
 * @date 2022/11/10
 */
public interface CommonService {
    /**
     * 上传
     *
     * @param file 文件
     * @param type 类型
     * @return 返回是否上传成功
     */
    String upload(MultipartFile file, String type);
}

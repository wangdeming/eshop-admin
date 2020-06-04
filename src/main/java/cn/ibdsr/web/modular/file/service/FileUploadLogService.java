/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 类功能和用法的说明
 * @Version V1.0
 * @CreateDate 2018/5/23 16:25
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/5/23      ZhangLin               类说明
 *
 */
public interface FileUploadLogService {



   /**
    * @Description 记录上传所有文件的日志信息
    * @Date 2018/5/23 16:30
    * @param file 上传的文件
    * @param path  已经上传的文件路径 比如 group1/M00/06/11/rBAByFsFHkSAPfmpAAASADB43qY717.xls
    * @param description 描述信息 可以为空
    * @throws
    * @return 返回值的说明
    */
    void addUploadLog(MultipartFile file, String path, String description);
}

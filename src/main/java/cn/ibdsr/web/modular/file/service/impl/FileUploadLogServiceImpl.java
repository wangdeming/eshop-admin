/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.file.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.web.common.persistence.dao.FileUploadLogMapper;
import cn.ibdsr.web.common.persistence.model.FileUploadLog;
import cn.ibdsr.web.modular.file.service.FileUploadLogService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 类功能和用法的说明
 * @Version V1.0
 * @CreateDate 2018/5/23 16:26
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/5/23      ZhangLin               类说明
 *
 */
@Service
public class FileUploadLogServiceImpl implements FileUploadLogService {

    @Resource
    FileUploadLogMapper fileUploadLogMapper;

    @Override
    public void addUploadLog(MultipartFile file,  String path, String description) {
        ShiroUser shiroUser = ShiroKit.getUser();
        Long id = shiroUser.getId();
        String account = shiroUser.getAccount();
        Integer platformType = shiroUser.getPlatformType();
        String fileName = file.getOriginalFilename();//获取文件名
        Long fileSize = file.getSize();//文件大小
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        String imgeArray[][] = {
                {"bmp", "0"}, {"dib", "1"}, {"gif", "2"},
                {"jfif", "3"}, {"jpe", "4"}, {"jpeg", "5"},
                {"jpg", "6"}, {"png", "7"} ,{"tif", "8"},
                {"tiff", "9"}, {"ico", "10"}
        };
        String fileType = "";
        for(int i = 0; i<imgeArray.length; i++) {
            if(imgeArray[i][0].equals(suffix.toLowerCase())) {
                fileType = "图片";
                break;
            }else {
                fileType = "文件";
            }
        }
        FileUploadLog fileUploadLog=new FileUploadLog();
        fileUploadLog.setFileType(fileType);
        fileUploadLog.setFileSuffix(suffix);
        fileUploadLog.setFileName(fileName);
        fileUploadLog.setFileSize(fileSize);
        fileUploadLog.setAccount(account);
        fileUploadLog.setAccountId(id);
        fileUploadLog.setPath(path);
        fileUploadLog.setPlatformType(platformType);
        fileUploadLog.setCreatetime(new Date());
        fileUploadLog.setDescription(description);
        fileUploadLogMapper.insert(fileUploadLog);
    }
}

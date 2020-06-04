/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.file.transfer;

/**
 * @Description 类功能和用法的说明
 * @Version V1.0
 * @CreateDate 2018/4/18 8:37
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/4/18      ZhangLin               类说明
 *
 */
public class ImageDTO {

    /**图片存储全路径 比如 group1/M00/00/22/rBAByFrWkEyAGY8OAB1STgAizPE255.png*/
    private String path;

    /**压缩之后的子图信息*/
    private String thumbPath;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
}

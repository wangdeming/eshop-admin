package cn.ibdsr.web.modular.file.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.fastdfs.base.service.ImageService;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.file.service.FileUploadLogService;
import cn.ibdsr.web.modular.file.transfer.ImageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

/**
 * @Description 系统图片上传接口
 * @Version V1.0
 * @CreateDate 2018/4/18 8:27
 *
 * Date                  Author               Description
 * ------------------------------------------------------
 * 2019-03-01 9:27:00    XuZhipeng               类说明
 *
 */
@Controller
@RequestMapping("/file")
public class ImageUploadController {

    @Resource
    private ImageService imageService;

    @Resource
    FileUploadLogService fileUploadLogService;

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @param description
     * @return
     */
    @PostMapping(value="/image/upload")
    @ResponseBody
    public Object imageUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "description", required = false) String description) {
        String fType = file.getContentType();
        if (fType.indexOf("jpg") < 0
                && fType.indexOf("jpeg") < 0
                && fType.indexOf("png") < 0
                && fType.indexOf("svg") < 0) {
            throw new BussinessException(BizExceptionEnum.IMAGE_FILE_TYPE_ERROR);
        }

        // 判断是否正确图片
        if (!isImage(file)) {
            throw new BussinessException(BizExceptionEnum.IMAGE_FILE_CONTENT_ERROR);
        }

        Long size = file.getSize();
        if (size > Const.IMAGE_SIZE_MAX_LIMIT) {
            throw new BussinessException(BizExceptionEnum.IMAGE_UPLOAD_SIZE_TOO_BIG);
        }

        ImageDTO imageDTO = new ImageDTO();
        String path = imageService.upload(file);
        imageDTO.setPath(path != null ? ImageUtil.PREFIX_IMAGE_URL + path : null);
        SuccessDataTip successDataTip = new SuccessDataTip(imageDTO);
        fileUploadLogService.addUploadLog(file, path, description);
        return successDataTip;
    }

    @PostMapping(value="/delete")
    @ResponseBody
    public Object delete(@RequestParam("path") String path) {

        /**剪切图片保存路径*/
        String filePath = ImageUtil.cutImageURL(path);
        imageService.delete(filePath);
        SuccessDataTip successDataTip = new SuccessDataTip(filePath);
        return successDataTip;
    }

    /**
     * 根据ImageIO判断图片是否正确
     *
     * @param file
     * @return
     */
    private Boolean isImage(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            if (inputStream == null) {
                return false;
            }
            Image img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }
}

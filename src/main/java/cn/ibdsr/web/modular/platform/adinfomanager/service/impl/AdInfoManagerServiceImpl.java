package cn.ibdsr.web.modular.platform.adinfomanager.service.impl;

import cn.ibdsr.core.base.service.impl.AbstractBaseService;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.platform.AdStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.AdInfoMapper;
import cn.ibdsr.web.common.persistence.model.AdInfo;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.adinfomanager.dao.AdInfoManagerDao;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoDTO;
import cn.ibdsr.web.modular.platform.adinfomanager.transfer.AdInfoVO;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.platform.adinfomanager.service.IAdInfoManagerService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 测试Service
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */
@Service
public class AdInfoManagerServiceImpl extends AbstractBaseService<AdInfoDTO, AdInfo> implements IAdInfoManagerService {

    @Resource
    private AdInfoMapper adInfoMapper;

    @Resource
    private AdInfoManagerDao adInfoManagerDao;

    @Override
    public BaseMapper<AdInfo> getMapper() {
        return adInfoMapper;
    }

    @Override
    public AdInfo getConversionDO() {
        return new AdInfo();
    }

    @Override
    public AdInfoDTO getConversionDTO() {
        return new AdInfoDTO();
    }

    @Override
    public void checkInsert(AdInfoDTO adInfoDTO) {

    }

    @Override
    public void checkUpdate(AdInfoDTO adInfoDTO) {

    }

    /**
     * 首页广告新增
     * @param adInfoDTO
     * @return
     */
    @Override
    public void homeAdAdd(AdInfoDTO adInfoDTO){
        adInfoDTO.setImg(ImageUtil.cutImageURL(adInfoDTO.getImg()));
        adInfoDTO.setCreatedTime(new Date());
        adInfoDTO.setModifiedTime(new Date());
        adInfoDTO.setCreatedUser(ShiroKit.getUser().getId());
        adInfoDTO.setModifiedUser(ShiroKit.getUser().getId());
        adInfoDTO.setIsDeleted(IsDeleted.NORMAL.getCode());
        adInfoDTO.setStatus(AdStatus.NORMAL.getCode());
        insert(adInfoDTO);
    }

    /**
     * 首页广告编辑
     * @param adInfoDTO
     * @return
     */
    @Override
    public void homeAdUpdate(AdInfoDTO adInfoDTO){
        adInfoDTO.setImg(ImageUtil.cutImageURL(adInfoDTO.getImg()));
        adInfoDTO.setModifiedTime(new Date());
        adInfoDTO.setModifiedUser(ShiroKit.getUser().getId());
        updateById(adInfoDTO);
    }

    /**
     * 推荐商家广告新增（第一次编辑）
     * @param adInfoDTO
     * @return
     */
    @Override
    public void shopAdAdd(AdInfoDTO adInfoDTO) {
        adInfoDTO.setImg(ImageUtil.cutImageURL(adInfoDTO.getImg()));
        adInfoDTO.setCreatedTime(new Date());
        adInfoDTO.setModifiedTime(new Date());
        adInfoDTO.setCreatedUser(ShiroKit.getUser().getId());
        adInfoDTO.setModifiedUser(ShiroKit.getUser().getId());
        adInfoDTO.setPublishTime(new Date());
        adInfoDTO.setIsDeleted(IsDeleted.NORMAL.getCode());
        adInfoDTO.setStatus(AdStatus.PUBLISH.getCode());
        insert(adInfoDTO);
    }

    /**
     * 推荐商家广告编辑
     * @param adInfoDTO
     * @return
     */
    @Override
    public void shopAdUpdate(AdInfoDTO adInfoDTO) {
        adInfoDTO.setImg(ImageUtil.cutImageURL(adInfoDTO.getImg()));
        adInfoDTO.setModifiedTime(new Date());
        adInfoDTO.setPublishTime(new Date());
        adInfoDTO.setModifiedUser(ShiroKit.getUser().getId());
        updateById(adInfoDTO);
    }

    /**
     * 修改广告的排序
     * @param id 主键id
     * @param sequence 排序号
     * @return
     */
    @Override
    public void sequenceEdit(Long id, Integer sequence){
        if (getMapper().selectById(id) == null) {
            throw new BussinessException(BizExceptionEnum.AD_NOT_EXIST);
        }
        if (getMapper().selectById(id).getStatus() == AdStatus.PUBLISH.getCode()){
            AdInfo adInfo = new AdInfo();
            adInfo.setId(id);
            adInfo.setSequence(sequence);
            adInfo.setModifiedTime(new Date());
            adInfo.setModifiedUser(ShiroKit.getUser().getId());
            adInfoMapper.updateById(adInfo);
        }
        else {
            throw new BussinessException(BizExceptionEnum.SEQUENCE_CANNOT_EDIT);
        }
    }

    @Override
    /**
     * 逻辑删除首页轮播广告
     * @param id
     * @return
     */
    public void logDelete(Long id) {
        if (getMapper().selectById(id) == null) {
            throw new BussinessException(BizExceptionEnum.AD_NOT_EXIST);
        }
        if (getMapper().selectById(id).getStatus() != AdStatus.PUBLISH.getCode()){
            AdInfo adInfo = new AdInfo();
            adInfo.setId(id);
            adInfo.setIsDeleted(IsDeleted.DELETED.getCode());
            adInfo.setModifiedTime(new Date());
            adInfo.setModifiedUser(ShiroKit.getUser().getId());
            adInfoMapper.updateById(adInfo);
        }
        else {
            throw new BussinessException(BizExceptionEnum.AD_IS_PUBLISHING);
        }
    }

    /**
     * 首页轮播广告发布
     * @param id
     * @return
     */
    @Override
    public void adPublish(Long id){
        if (getMapper().selectById(id) == null) {
            throw new BussinessException(BizExceptionEnum.AD_NOT_EXIST);
        }
        if (getMapper().selectById(id).getStatus() != AdStatus.PUBLISH.getCode()){
            AdInfo adInfo = new AdInfo();
            adInfo.setId(id);
            adInfo.setModifiedTime(new Date());
            adInfo.setModifiedUser(ShiroKit.getUser().getId());
            adInfo.setStatus(AdStatus.PUBLISH.getCode());
            adInfo.setPublishTime(new Date());
            adInfoMapper.updateById(adInfo);
        }
        else {
            throw new BussinessException(BizExceptionEnum.AD_CANNOT_PUBLISH);
        }

    }

    /**
     * 首页轮播广告下线
     * @param id
     * @return
     */
    @Override
    public void adOffShelf(Long id){
        if (getMapper().selectById(id) == null) {
            throw new BussinessException(BizExceptionEnum.AD_NOT_EXIST);
        }
        if (getMapper().selectById(id).getStatus() == AdStatus.PUBLISH.getCode()){
            AdInfo adInfo = new AdInfo();
            adInfo.setId(id);
            adInfo.setModifiedTime(new Date());
            adInfo.setModifiedUser(ShiroKit.getUser().getId());
            adInfo.setStatus(AdStatus.OFFSHELF.getCode());
            adInfoMapper.updateById(adInfo);
        }
        else {
            throw new BussinessException(BizExceptionEnum.AD_CANNOT_OFFSHELF);
        }

    }

    /**
     * @Description 首页广告列表查询
     * @param adPosition 广告位位置的英文缩写（homePageAd-平台首页轮播广告；recommendShopAd-推荐店铺广告；
     *                   shopGoodsAd-特产首页轮播广告）
     * @return
     */
    @Override
    public List<AdInfoVO> homeAdList(String adPosition){
        List<AdInfoVO> homeAdList = adInfoManagerDao.homeAdList(adPosition);
        for (AdInfoVO adListVO : homeAdList){
            adListVO.setImg(ImageUtil.setImageURL(adListVO.getImg()));
        }
        return homeAdList;
    }

    /**
     * @Description recommendShopAd-推荐店铺广告列表查询
     * @param
     * @return
     */
    @Override
    public List<AdInfoVO> shopAdList(){
        List<AdInfoVO> shopAdList = adInfoManagerDao.shopAdList();
        for (AdInfoVO adListVO : shopAdList){
            adListVO.setImg(ImageUtil.setImageURL(adListVO.getImg()));
        }
        return shopAdList;
    }

}

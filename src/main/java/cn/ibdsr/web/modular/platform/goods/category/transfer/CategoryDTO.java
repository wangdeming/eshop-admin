package cn.ibdsr.web.modular.platform.goods.category.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.core.check.CheckUtil;

/**
 * 商品类别信息DTO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class CategoryDTO extends BaseDTO {

    /**
     * 类别ID（修改时使用）
     */
    private Long id;

    /**
     * 父类别ID
     */
    @Verfication(name = "父类别ID", notNull = true)
    private Long pid;

    /**
     * 类别名称
     */
    @Verfication(name = "类别名称", notNull = true, regx = {CheckUtil.GOODS_CATEGORY_NAME_STR, "格式不正确，只充许汉字或字母的组合，20个字以内"})
    private String name;

    /**
     * 类别前台名称
     */
    @Verfication(name = "类别前台名称", notNull = true, regx = {CheckUtil.GOODS_CATEGORY_FRONT_NAME_STR, "格式不正确，只充许汉字或字母的组合，2-6个字以内"})
    private String frontName;

    /**
     * 图标图片路径
     */
    private String iconImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }
}

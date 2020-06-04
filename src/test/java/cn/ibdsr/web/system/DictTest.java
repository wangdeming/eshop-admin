package cn.ibdsr.web.system;

import cn.ibdsr.web.base.BaseJunit;
import cn.ibdsr.web.modular.system.dao.DictDao;
import cn.ibdsr.web.modular.system.service.IDictService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典服务测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class DictTest extends BaseJunit {

    @Resource
    IDictService dictService;

    @Resource
    DictDao dictDao;

    @Test
    public void addTest() {
    }

    @Test
    public void editTest() {
    }

    @Test
    public void deleteTest() {
    }

    @Test
    public void listTest() {
    }
}

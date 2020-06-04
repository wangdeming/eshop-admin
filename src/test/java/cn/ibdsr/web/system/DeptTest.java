package cn.ibdsr.web.system;

import cn.ibdsr.web.base.BaseJunit;
import cn.ibdsr.web.common.persistence.dao.DeptMapper;
import cn.ibdsr.web.common.persistence.model.Dept;
import cn.ibdsr.web.modular.system.dao.DeptDao;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 字典服务测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class DeptTest extends BaseJunit {

    @Resource
    DeptDao deptDao;

    @Resource
    DeptMapper deptMapper;

    @Test
    public void addDeptTest() {
    }

    @Test
    public void updateTest() {
    }

    @Test
    public void deleteTest() {
    }

    @Test
    public void listTest() {
    }
}

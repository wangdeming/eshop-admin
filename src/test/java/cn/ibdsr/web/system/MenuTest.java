package cn.ibdsr.web.system;

import cn.ibdsr.web.base.BaseJunit;
import cn.ibdsr.web.common.persistence.dao.MenuMapper;
import cn.ibdsr.web.common.persistence.model.Menu;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Stack;

/**
 * 菜单测试
 *
 * @author fengshuonan
 * @date 2017-06-13 21:23
 */
public class MenuTest extends BaseJunit {

    @Autowired
    MenuMapper menuMapper;

    /**
     * 初始化pcodes
     *
     * @author stylefeng
     * @Date 2017/6/13 21:24
     */
    @Test
    public void generatePcodes() {
    }

}

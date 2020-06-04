package cn.ibdsr.web.modular.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 心跳接口，运维使用
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/3/28 10:18
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/3/28 xujincai init
 */
@RestController
public class HealthController {
    @RequestMapping(value = "health")
    public String health() {
        return "ok";
    }
}

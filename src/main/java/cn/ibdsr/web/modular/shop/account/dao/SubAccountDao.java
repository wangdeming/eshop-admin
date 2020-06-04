package cn.ibdsr.web.modular.shop.account.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface SubAccountDao {
    List<JSONObject> listRole(JSONObject paramJson);
}

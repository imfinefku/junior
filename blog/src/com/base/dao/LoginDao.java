package com.base.dao;

import com.base.bean.LoginInfo;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author xuduo
 */
public interface LoginDao {

    public List<LoginInfo> login(HashMap map);
    
    public int updatePassword(LoginInfo loginInfo);
}

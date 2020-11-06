package com.base.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.bean.LoginInfo;
import com.base.dao.LoginDao;

/**
*
* @author xuduo
*/
@Service("loginService")
public class LoginService {

   @Autowired
   public LoginDao loginDao;

   public List<LoginInfo> login(HashMap map) {
       return loginDao.login(map);
   }
   
   public int updatePassword(LoginInfo loginInfo) {
       return loginDao.updatePassword(loginInfo);
   }

}

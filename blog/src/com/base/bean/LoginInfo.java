package com.base.bean;

/**
 *
 * @author xuduo
 */
public class LoginInfo {

    private String id;
    private String username;//用户名
    private String password;//密码
    private String name;//姓名
    private String type;//权限 1:超级管理员  2：管理员  3：用户

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
}

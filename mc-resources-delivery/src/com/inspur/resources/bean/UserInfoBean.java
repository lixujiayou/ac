// 

// 

package com.inspur.resources.bean;

public class UserInfoBean
{
    private String offLinePwd;
    private String password;
    private String phoneNumber;
    private String realname;
    private String username;
    
    public UserInfoBean() {
        this.username = null;
        this.realname = null;
        this.password = null;
        this.offLinePwd = null;
    }
    
    public UserInfoBean(final UserInfoBean userInfoBean) {
        this.username = null;
        this.realname = null;
        this.password = null;
        this.offLinePwd = null;
        this.username = userInfoBean.username;
        this.realname = userInfoBean.realname;
        this.password = userInfoBean.password;
        this.offLinePwd = userInfoBean.offLinePwd;
        this.phoneNumber = userInfoBean.phoneNumber;
    }
    
    public String getOffLinePwd() {
        return this.offLinePwd;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public String getRealname() {
        return this.realname;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setOffLinePwd(final String offLinePwd) {
        this.offLinePwd = offLinePwd;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setRealname(final String realname) {
        this.realname = realname;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}

package com.page.user.dto;

public class LoginDTO {

    private String user_id;
    private String user_pw;
    private boolean user_cookie;

    
    public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public boolean isUser_cookie() {
		return user_cookie;
	}

	public void setUser_cookie(boolean user_cookie) {
		this.user_cookie = user_cookie;
	}

	@Override
    public String toString() {
        return "LoginDTO{" +
                "user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", use_cookie=" + user_cookie +
                '}';
    }
}

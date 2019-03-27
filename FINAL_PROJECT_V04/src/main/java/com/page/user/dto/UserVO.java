package com.page.user.dto;

import java.util.Date;

public class UserVO {

	private int 	user_no;
    private String 	user_id;
    private String 	user_pw;
    private String 	user_name;
    private String 	user_email;
    private String 	user_sex;
    private Date 	user_join_date;
    private Date 	user_login_date;
    private String 	user_signature;
    private String 	user_img;
    private int 	user_point;

    
    public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public Date getUser_join_date() {
		return user_join_date;
	}

	public void setUser_join_date(Date user_join_date) {
		this.user_join_date = user_join_date;
	}

	public Date getUser_login_date() {
		return user_login_date;
	}

	public void setUser_login_date(Date user_login_date) {
		this.user_login_date = user_login_date;
	}

	public String getUser_signature() {
		return user_signature;
	}

	public void setUser_signature(String user_signature) {
		this.user_signature = user_signature;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public int getUser_point() {
		return user_point;
	}

	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}

	@Override
    public String toString() {
        return "UserVO{" +
        		"user_no='" + user_no + '\'' +
                ",user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_join_date=" + user_join_date +
                ", user_login_date=" + user_login_date +
                ", user_signature='" + user_signature + '\'' +
                ", user_img='" + user_img + '\'' +
                ", user_point=" + user_point +
                '}';
    }
}

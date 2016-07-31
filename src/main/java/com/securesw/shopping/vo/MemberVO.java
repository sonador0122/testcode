package com.securesw.shopping.vo;

import java.io.Serializable;
import java.util.Date;




public class MemberVO implements Serializable{

	private static final long serialVersionUID = -1141067737449130083L;
	
	private int idx;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String address;
	private int postcode;
	private Date createdDate;
	private Date lastDate;
	private String authority;
	private String imagePath;
	
	public MemberVO(){}
	
	public MemberVO(String firstname,String lastname,String email){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
	}
	public MemberVO(String firstname,String lastname,String email,String address,int postcode, String imagePath){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.imagePath = imagePath;
	}
	public MemberVO(String firstname,String lastname,String email, String password,Date lastDate){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.lastDate = lastDate;
	}
	public MemberVO(String firstname,String lastname,String email, String password,String address,int postcode,String imagePath){
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.postcode = postcode;
		this.imagePath = imagePath;
	}
	
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}

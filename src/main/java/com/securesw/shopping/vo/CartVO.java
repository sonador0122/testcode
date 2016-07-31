package com.securesw.shopping.vo;

import java.io.Serializable;

public class CartVO implements Serializable{

	private static final long serialVersionUID = 7189614286736101498L;
	
	private int idx;
	private int quantity;
	private int boardIdx;
	private String memberEmail;
	private String imagePath;
	private int price;
	private String title;
	
	public CartVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartVO(int quantity, int boardIdx,String memberEmail) {
		super();
		this.quantity = quantity;
		this.boardIdx = boardIdx;
		this.memberEmail = memberEmail;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}

package com.securesw.shopping.vo;



public class GiftlistVO {
	private int idx;
	private String memberEmail;
	private int boardIdx;
	private int price;
	private String imagePath;
	private String title;
	
	public GiftlistVO(){
		super();
	}
	public GiftlistVO(String memberEmail, int boardIdx) {
		super();
		this.memberEmail = memberEmail;
		this.boardIdx = boardIdx;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}

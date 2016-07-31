package com.securesw.shopping.vo;

import java.util.Date;


public class OrdersVO {
	
	private int idx;
	private Date orderDate;
	private String orderNow;
	private String memberEmail;
	private String memberName;
	private String receiver;
	private int quantity;
	private String address;
	private int postcode;
	private int totalPrice;
	private int boardIdx;	
	private String title;
	
	public OrdersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdersVO(String orderNow, String memberEmail,
			String memberName, int boardIdx, int quantity, String address,
			int postcode, int totalPrice, String receiver) {
		super();
		this.orderNow = orderNow;
		this.memberEmail = memberEmail;
		this.memberName = memberName;
		this.boardIdx = boardIdx;
		this.quantity = quantity;
		this.address = address;
		this.postcode = postcode;
		this.totalPrice = totalPrice;
		this.receiver = receiver;
	}

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderNow() {
		return orderNow;
	}
	public void setOrderNow(String orderNow) {
		this.orderNow = orderNow;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
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
	
}

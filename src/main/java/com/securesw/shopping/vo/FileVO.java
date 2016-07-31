package com.securesw.shopping.vo;


public class FileVO {
	private int idx;
	private String realName;
	private String name;
	private String ext;
	private String uploader;
	private int boardIdx;
	
	public FileVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileVO(String realName, String name, String ext,
			String uploader, int boardIdx) {
		super();
		this.realName = realName;
		this.name = name;
		this.ext = ext;
		this.uploader = uploader;
		this.boardIdx = boardIdx;
	}

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	
}

package com.securesw.shopping.vo;

import java.util.Date;



public class BoardVO extends ReplyVO{

    private int idx;
    private int groupIdx;
    private String title;
    private String content;
    private Date postingDate;
    private int readCount;
    private int commentCount;
    private int fileCount;
    private int productsCount;
    private int memberIdx;
    private String memberEmail;
    private String separatorName;
    private int fileIdx;
    private int totalPrice;
    private String imagePath;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getGroupIdx() {
        return groupIdx;
    }

    public void setGroupIdx(int groupIdx) {
        this.groupIdx = groupIdx;
    }

    public String getSequenceIdx() {
        return sequenceIdx;
    }

    public void setSequenceIdx(String sequenceIdx) {
        this.sequenceIdx = sequenceIdx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public int getMemberIdx() {
        return memberIdx;
    }

    public void setMemberIdx(int memberIdx) {
        this.memberIdx = memberIdx;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getSeparatorName() {
        return separatorName;
    }

    public void setSeparatorName(String separatorName) {
        this.separatorName = separatorName;
    }

    public int getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(int fileIdx) {
        this.fileIdx = fileIdx;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

package com.securesw.shopping.vo;

import java.util.Date;


public class CommentVO extends ReplyVO{

    private int idx;
    private int groupIdx;
    private String content;
    private Date postingDate;
    private int memberIdx;
    private String memberEmail;
    private int boardIdx;
    private String separatorName;

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

    public int getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(int boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getSeparatorName() {
        return separatorName;
    }

    public void setSeparatorName(String separatorName) {
        this.separatorName = separatorName;
    }
}

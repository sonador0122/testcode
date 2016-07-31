package com.securesw.shopping.service;

import com.securesw.shopping.vo.CommentVO;

import java.util.List;


public interface CommentService {
    public int selectCount(int boardIdx) throws Exception;
    public int selectCount(int boardIdx, String separatorName) throws Exception;
    public List<CommentVO> selectList(int boardIdx) throws Exception;
    public List<CommentVO> selectList(int boardIdx, String separatorName) throws Exception;
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow) throws Exception;
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow, String separatorName) throws Exception;
    public int insert(CommentVO commentVO) throws Exception;
    public void increaseCommentCount(int boardIdx) throws Exception;
    public CommentVO selectOne(int commentIdx) throws Exception;
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception;
    public int update(CommentVO commentVO) throws Exception;
    public int delete(int commentIdx) throws Exception;
    public void decreaseCommentCount(int commentIdx) throws Exception;
    public int selectGroupIdx(String groupName) throws Exception;
    public int updateGroupIdx(String groupName) throws Exception;
    public int generateNextGroupIdx(String groupName) throws Exception;
}

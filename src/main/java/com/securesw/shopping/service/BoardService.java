package com.securesw.shopping.service;

import com.securesw.shopping.vo.BoardVO;

import java.util.List;



public interface BoardService {

   public int selectCount() throws Exception;
   public int selectCount(String separatorName) throws Exception;
    public int selectCount(String separatorName, String keyword) throws Exception;
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception;
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception;
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName, String keyword) throws Exception;
    public int insert(BoardVO boardVO) throws Exception;
    public BoardVO selectOne(int boardIdx) throws Exception;
    public void increaseReadCount(int boardIdx) throws Exception;
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception;
    public int update(BoardVO boardVO) throws Exception;
    public int delete(int boardIdx) throws Exception;
    public int generateNextGroupIdx(String groupName) throws Exception;
    public int selectLastBoardIdxByEmail(String memberEmail) throws Exception;
    public int setProductsCountZero(int boardIdx) throws Exception;
}

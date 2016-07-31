package com.securesw.shopping.dao;

import com.securesw.shopping.vo.BoardVO;

import java.util.HashMap;
import java.util.List;



public interface BoardDAO {
  
    public int selectCount() throws Exception;
    public int selectCount(String separatorName) throws Exception;
    public int selectCount(HashMap<String,Object> map);
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception;
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception;
    public List<BoardVO> selectList(HashMap<String,Object> map) throws Exception;
    public int insert(BoardVO boardVO) throws Exception;
    public BoardVO selectOne(int boardIdx) throws Exception;
    public void increaseReadCount(int boardIdx) throws Exception;
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception;
    public int update(BoardVO boardVO) throws Exception;
    public int delete(int boardIdx) throws Exception;
    public int selectGroupIdx(String groupName) throws Exception;
    public int updateGroupIdx(String groupName) throws Exception;
    public int selectLastBoardIdxByEmail(String memberEmail) throws Exception;
    public int setProductsCountZero(int boardIdx) throws Exception;
}

package com.securesw.shopping.service;

import com.securesw.shopping.dao.BoardDAO;
import com.securesw.shopping.vo.BoardVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;



@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Resource(name = "boardDAO")
    private BoardDAO boardDAO;

    @Override
    public int selectCount() throws Exception {
        return boardDAO.selectCount();
    }

    @Override
    public int selectCount(String separatorName) throws Exception {
        return boardDAO.selectCount(separatorName);
    }

    @Override
    public int selectCount(String separatorName, String keyword) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("separatorName", separatorName);
        map.put("keyword", keyword);

        return boardDAO.selectCount(map);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception {
        return boardDAO.selectList(firstRow, endRow);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception {
        return boardDAO.selectList(firstRow, endRow, separatorName);
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName, String keyword) throws Exception {

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("separatorName", separatorName);
        map.put("rowBounds", rowBounds);
        map.put("keyword", keyword);
        return boardDAO.selectList(map);
    }

    @Override
    public int insert(BoardVO boardVO) throws Exception {
        return boardDAO.insert(boardVO);
    }

    @Override
    public BoardVO selectOne(int boardIdx) throws Exception {
        return boardDAO.selectOne(boardIdx);
    }

    @Override
    public void increaseReadCount(int boardIdx) throws Exception {
        boardDAO.increaseReadCount(boardIdx);
    }

    @Override
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        return boardDAO.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
    }

    @Override
    public int update(BoardVO boardVO) throws Exception {
        return boardDAO.update(boardVO);
    }

    @Override
    public int delete(int boardIdx) throws Exception {
        return boardDAO.delete(boardIdx);
    }

    @Override
    public int generateNextGroupIdx(String groupName) throws Exception {
        boardDAO.updateGroupIdx(groupName);
        return boardDAO.selectGroupIdx(groupName);
    }

    @Override
    public int selectLastBoardIdxByEmail(String memberEmail) throws Exception {
        return boardDAO.selectLastBoardIdxByEmail(memberEmail);
    }

    @Override
    public int setProductsCountZero(int boardIdx) throws Exception {
        return boardDAO.setProductsCountZero(boardIdx);
    }
}

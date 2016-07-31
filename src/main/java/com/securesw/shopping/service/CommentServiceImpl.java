package com.securesw.shopping.service;

import com.securesw.shopping.dao.CommentDAO;
import com.securesw.shopping.vo.CommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource(name = "commentDAO")
    private CommentDAO commentDAO;

    @Override
    public int selectCount(int boardIdx) throws Exception {
        return commentDAO.selectCount(boardIdx);
    }

    @Override
    public int selectCount(int boardIdx, String separatorName) throws Exception {
        return commentDAO.selectCount(boardIdx, separatorName);
    }

    @Override
    public List<CommentVO> selectList(int boardIdx) throws Exception {
        return commentDAO.selectList(boardIdx);
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, String separatorName) throws Exception {
        return commentDAO.selectList(boardIdx, separatorName);
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow) throws Exception {
        return commentDAO.selectList(boardIdx, firstRow, endRow);
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow, String separatorName) throws Exception {
        return commentDAO.selectList(boardIdx, firstRow, endRow, separatorName);
    }

    @Override
    public int insert(CommentVO commentVO) throws Exception {
        return commentDAO.insert(commentVO);
    }

    @Override
    public void increaseCommentCount(int boardIdx) throws Exception {
        commentDAO.increaseCommentCount(boardIdx);
    }

    @Override
    public CommentVO selectOne(int commentIdx) throws Exception {
        return commentDAO.selectOne(commentIdx);
    }

    @Override
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        return commentDAO.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
    }

    @Override
    public int update(CommentVO commentVO) throws Exception {
        return commentDAO.update(commentVO);
    }

    @Override
    public int delete(int commentIdx) throws Exception {
        return commentDAO.delete(commentIdx);
    }

    @Override
    public void decreaseCommentCount(int commentIdx) throws Exception {
        commentDAO.decreaseCommentCount(commentIdx);
    }

    @Override
    public int selectGroupIdx(String groupName) throws Exception {
        return commentDAO.selectGroupIdx(groupName);
    }

    @Override
    public int updateGroupIdx(String groupName) throws Exception {
        return commentDAO.updateGroupIdx(groupName);
    }

    @Override
    public int generateNextGroupIdx(String groupName) throws Exception {
        commentDAO.updateGroupIdx(groupName);
        return commentDAO.selectGroupIdx(groupName);
    }
}

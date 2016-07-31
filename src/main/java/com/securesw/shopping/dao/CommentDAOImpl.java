package com.securesw.shopping.dao;

import com.securesw.shopping.vo.CommentVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;



@Repository("commentDAO")
public class CommentDAOImpl implements CommentDAO {

    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public int selectCount(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.commentDao.selectCount", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(int boardIdx, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardIdx", boardIdx);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectOne("com.securesw.commentDao.selectCountBySeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.commentDao.selectList", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardIdx", boardIdx);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectList("com.securesw.commentDao.selectListBySeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardIdx", boardIdx);
        map.put("rowBounds", rowBounds);

        try{
            return sqlSession.selectList("com.securesw.commentDao.selectListByRow", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<CommentVO> selectList(int boardIdx, int firstRow, int endRow, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardIdx", boardIdx);
        map.put("rowBounds", rowBounds);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectList("com.securesw.commentDao.selectListByRowSeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(CommentVO commentVO) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.commentDao.insert", commentVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void increaseCommentCount(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.commentDao.increaseCommentCount", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public CommentVO selectOne(int commentIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.commentDao.selectOne", commentIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("searchMaxSeqNum", searchMaxSeqNum);
        map.put("searchMinSeqNum", searchMinSeqNum);

        try{
            return sqlSession.selectOne("com.securesw.commentDao.selectLastSequenceIdx", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int update(CommentVO commentVO) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.commentDao.update", commentVO);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int delete(int commentIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.commentDao.delete", commentIdx);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseCommentCount(int commentIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.commentDao.decreaseCommentCount", commentIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectGroupIdx(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.commentDao.selectGroupIdx", groupName);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int updateGroupIdx(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.commentDao.updateGroupIdx", groupName);
        } finally{
            sqlSession.close();
        }
    }
}

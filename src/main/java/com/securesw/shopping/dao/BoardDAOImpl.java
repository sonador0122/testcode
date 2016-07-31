package com.securesw.shopping.dao;

import com.securesw.shopping.vo.BoardVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;



@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public int selectCount() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectCount");
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectCountBySeparator", separatorName);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(HashMap<String, Object> map) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectCountByHashMap", map);
        }finally{
            sqlSession.close();
        }
    }

    public List<BoardVO> selectList(int firstRow, int endRow) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        try{
            return sqlSession.selectList("com.securesw.boardDao.selectList", rowBounds);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<BoardVO> selectList(int firstRow, int endRow, String separatorName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rowBounds", rowBounds);
        map.put("separatorName", separatorName);

        try{
            return sqlSession.selectList("com.securesw.boardDao.selectListBySeparator", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<BoardVO> selectList(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.boardDao.selectListByHashMap", map);
        }finally{
            sqlSession.close();
        }
    }

    public int insert(BoardVO boardVO) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.boardDao.insert", boardVO);
        }finally{
            sqlSession.close();
        }
    }

    public BoardVO selectOne(int boardIdx) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectOne", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    public void increaseReadCount(int boardIdx) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.boardDao.increaseReadCount", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    public String selectLastSequenceIdx(String searchMaxSeqNum, String searchMinSeqNum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("searchMaxSeqNum", searchMaxSeqNum);
        map.put("searchMinSeqNum", searchMinSeqNum);

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectLastSequenceIdx", map);
        }finally{
            sqlSession.close();
        }
    }

    public int update(BoardVO boardVO) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.boardDao.update", boardVO);
        } finally{
            sqlSession.close();
        }
    }

    public int delete(int boardIdx) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.boardDao.delete", boardIdx);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectGroupIdx(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectGroupIdx", groupName);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int updateGroupIdx(String groupName) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.boardDao.updateGroupIdx", groupName);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectLastBoardIdxByEmail(String memberEmail) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.boardDao.selectLastBoardIdxByEmail", memberEmail);
        } finally{
            sqlSession.close();
        }
    }

    @Override
    public int setProductsCountZero(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.boardDao.setProductsCountZero", boardIdx);
        } finally{
            sqlSession.close();
        }
    }
}

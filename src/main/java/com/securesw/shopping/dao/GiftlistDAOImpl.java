package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.securesw.shopping.vo.GiftlistVO;



@Repository("giftlistDAO")
public class GiftlistDAOImpl implements GiftlistDAO {
	
	SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
	@Override
	public int insert(HashMap<String,Object> paraMap) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.giftlistDao.insert", paraMap);
        }finally{
            sqlSession.close();
        }
    }

	@Override
	public int count() throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.giftlistDao.count");
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int count(String keyword) throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.giftlistDao.countByKeyword",keyword);
        }finally{
            sqlSession.close();
        }
	}
	
	@Override
	public List<GiftlistVO> selectListMap(HashMap<String, Object> paraMap) throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		  
	     try{
	         return sqlSession.selectList("com.securesw.giftlistDao.selectList", paraMap);
	     }finally{
	    	 sqlSession.close();
	     }
	}

	@Override
	public int delete(HashMap<String, Object> paraMap) throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.giftlistDao.delete", paraMap);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public GiftlistVO selectOne(HashMap<String, Object> paraMap) throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.giftlistDao.selectOne",paraMap);
        }finally{
            sqlSession.close();
        }
	}

}

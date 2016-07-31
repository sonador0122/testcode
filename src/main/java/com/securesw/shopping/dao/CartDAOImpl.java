package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.securesw.shopping.vo.CartVO;

@Repository("cartDAO")
public class CartDAOImpl implements CartDAO {
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
	
	@Override
	public int insert(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("com.securesw.cartDao.insert", cartVO);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public List<CartVO> selectList(String email) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		  
	     try{
	         return sqlSession.selectList("com.securesw.cartDao.selectList", email);
	     }finally{
	    	 sqlSession.close();
	     }
	}

	@Override
	public int delete(int idx) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.cartDao.delete", idx);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public CartVO selectOne(HashMap<String, Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.cartDao.selectOne",paraMap);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int deleteMap(HashMap<String, Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.cartDao.deleteMap",paraMap);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int changeMap(HashMap<String, Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.cartDao.changeMap",paraMap);
        }finally{
            sqlSession.close();
        }
	}

}

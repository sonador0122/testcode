package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.securesw.shopping.vo.OrdersVO;

@Repository("ordersDAO")
public class OrdersDAOImpl implements OrdersDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
	@Override
	public List<OrdersVO> selectListMap(HashMap<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();
		  
	     try{
	         return sqlSession.selectList("com.securesw.ordersDao.selectList", paraMap);
	     }finally{
	    	 sqlSession.close();
	     }
	}


	@Override
	public List<OrdersVO> selectTotalListMap(HashMap<String, Object> map) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.selectList("com.securesw.ordersDao.selectTotalList", map);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int count() throws Exception{
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.ordersDao.count");
        }finally{
            sqlSession.close();
        }
	}
	
	@Override
	public int count(String keyword) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.ordersDao.countByKeyword",keyword);
        }finally{
            sqlSession.close();
        }
	}


	@Override
	public int countTotalList(HashMap<String,Object> map) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.selectOne("com.securesw.ordersDao.countTotalList",map);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int delete(int idx) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("com.securesw.ordersDao.delete", idx);
		}finally{
			sqlSession.close();
		}
	}

	@Override
	public int insert(OrdersVO ordersVO) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try{
			return sqlSession.insert("com.securesw.ordersDao.insert", ordersVO);
		}finally{
			sqlSession.close();
		}
	}
}

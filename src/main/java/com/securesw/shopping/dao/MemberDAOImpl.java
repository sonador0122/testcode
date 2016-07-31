package com.securesw.shopping.dao;

import com.securesw.shopping.vo.MemberVO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;



@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public MemberVO selectOne(String email) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.memberDao.selectOne",email);
        }finally{
            sqlSession.close();
        }
    }

    public int count() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.memberDao.count");
        }finally{
            sqlSession.close();
        }
    }

    public int count(String keyword) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.memberDao.countByKeyword",keyword);
        }finally{
            sqlSession.close();
        }
    }
    
    public int insert(MemberVO memberVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.memberDao.insert", memberVO);
        }finally{
            sqlSession.close();
        }
    }

    public int update(MemberVO memberVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.memberDao.update", memberVO);
        }finally{
            sqlSession.close();
        }
    }

    public int delete(String email) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.memberDao.delete",email);
        }finally{
            sqlSession.close();
        }
    }

	@Override
	public int updateDate(String email) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.memberDao.updateDate", email);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public List<MemberVO> selectListMap(HashMap<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		 SqlSession sqlSession = sqlSessionFactory.openSession();
	  
	     try{
	         return sqlSession.selectList("com.securesw.memberDao.selectList", paraMap);
	     }finally{
	    	 sqlSession.close();
	     }
		
	}

	@Override
	public int updateData(HashMap<String,Object> paraMap) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.memberDao.updateData", paraMap);
        }finally{
            sqlSession.close();
        }
	}
}

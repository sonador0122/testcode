package com.securesw.shopping.dao;

import com.securesw.shopping.vo.FileVO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("fileDAO")
public class FileDAOImpl implements FileDAO {
	SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
	@Override
	public FileVO selectOne(int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.fileDao.selectOne",boardIdx);
        }finally{
            sqlSession.close();
        }
	}

	@Override
	public int insert(FileVO fileVO) throws Exception {
		// TODO Auto-generated method stub
		 SqlSession sqlSession = sqlSessionFactory.openSession();

	     try{
	    	 return sqlSession.insert("com.securesw.fileDao.insert", fileVO);
	     }finally{
	    	 sqlSession.close();
	     }
	}

}

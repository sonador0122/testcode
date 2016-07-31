package com.securesw.shopping.dao;

import com.securesw.shopping.vo.ProductsVO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;



@Repository("productsDAO")
public class ProductsDAOImpl implements ProductsDAO {
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public int selectCount(String memberId) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.productsDao.selectCount", memberId);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCount(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.productsDao.selectCountByKeyword", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public ProductsVO selectOne(int productsIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.productsDao.selectOne", productsIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rowBounds", rowBounds);
        map.put("memberId", memberId);

        try{
            return sqlSession.selectList("com.securesw.productsDao.selectList", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<ProductsVO> selectList(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.productsDao.selectListByKeyword", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(ProductsVO productsVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.insert("com.securesw.productsDao.insert", productsVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int insert(int boardIdx, int productsIdx) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("boardIdx", boardIdx);
        map.put("productsIdx", productsIdx);

        try{
            return sqlSession.insert("com.securesw.productsDao.insertBoardProducts", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int update(ProductsVO productsVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.update("com.securesw.productsDao.update", productsVO);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int delete(int productsIdx) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.productsDao.delete", productsIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void increaseProductsCount(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.productsDao.increaseProductsCount", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseProductsCount(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.productsDao.decreaseProductsCount", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<Integer> selectBoardProductsByBoard(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.productsDao.selectBoardProductsByBoard", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<Integer> selectBoardProductsByProducts(int productsIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.productsDao.selectBoardProductsByProducts", productsIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int deleteBoardProductsByBoard(int boardIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.productsDao.deleteBoardProductsByBoard", boardIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int deleteBoardProductsByProducts(int productsIdx) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.delete("com.securesw.productsDao.deleteBoardProductsByProducts", productsIdx);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int selectCountForStock(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectOne("com.securesw.productsDao.selectCountForStock", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public List<ProductsVO> selectListForStock(HashMap<String, Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            return sqlSession.selectList("com.securesw.productsDao.selectListForStock", map);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public void decreaseProductsStock(HashMap<String,Object> map) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            sqlSession.update("com.securesw.productsDao.decreaseStockCount", map);
        }finally{
            sqlSession.close();
        }
    }
}

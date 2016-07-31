package com.securesw.shopping.dao;

import com.securesw.shopping.vo.ProductsVO;

import java.util.HashMap;
import java.util.List;



public interface ProductsDAO {

   
    public int selectCount(String memberId) throws Exception;
    public int selectCount(HashMap<String,Object> map) throws Exception;
    public ProductsVO selectOne(int productsIdx) throws Exception;
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow) throws Exception;
    public List<ProductsVO> selectList(HashMap<String,Object> map) throws Exception;
    public int insert(ProductsVO productsVO);
    public int insert(int boardIdx, int productsIdx);
    public int update(ProductsVO productsVO);
    public int delete(int productsIdx);
    public void increaseProductsCount(int boardIdx) throws Exception;
    public void decreaseProductsCount(int boardIdx) throws Exception;
    public List<Integer> selectBoardProductsByBoard(int boardIdx) throws Exception;
    public List<Integer> selectBoardProductsByProducts(int productsIdx) throws Exception;
    public int deleteBoardProductsByBoard(int boardIdx) throws Exception;
    public int deleteBoardProductsByProducts(int productsIdx) throws Exception;
    public int selectCountForStock(HashMap<String,Object> map) throws Exception;
    public List<ProductsVO> selectListForStock(HashMap<String,Object> map) throws Exception;
    public void decreaseProductsStock(HashMap<String,Object> map) throws Exception;
}

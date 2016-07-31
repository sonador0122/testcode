package com.securesw.shopping.service;

import com.securesw.shopping.vo.CartVO;
import com.securesw.shopping.vo.ProductsVO;
import com.securesw.shopping.vo.OrdersVO;

import java.util.List;



public interface ProductsService {

    public int selectCount(String memberId) throws Exception;
    public int selectCount(String memberId, String keyword) throws Exception;
    public ProductsVO selectOne(int productsIdx) throws Exception;
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow) throws Exception;
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow, String keyword) throws Exception;
    public int insert(ProductsVO productsVO);
    public int insert(int boardIdx, int productsIdx);
    public int update(ProductsVO productsVO);
    public int delete(int productsIdx);
    public void increaseProductsCount(int boardIdx) throws Exception;
    public void decreaseProductsCount(int boardIdx) throws Exception;
	public int addorderlist(OrdersVO ordersVO) throws Exception;
	public int addcartlist(CartVO cartVO) throws Exception;
    public List<Integer> selectBoardProductsByBoard(int boardIdx) throws Exception;
    public List<Integer> selectBoardProductsByProducts(int productsIdx) throws Exception;
    public int deleteBoardProductsByBoard(int boardIdx) throws Exception;
    public int deleteBoardProductsByProducts(int productsIdx) throws Exception;
	public int cartDelete(int idx);
	public int cartDelete(int boardIdx,String email);
	public CartVO cartOne(int boardIdx, String email);
	public int cartChange(int quantity, int cartIdx);
    public int selectCountForStock(int maxStock, int minStock, String keyword) throws Exception;
    public List<ProductsVO> selectListForStock(int maxStock, int minStock, int firstRow, int endRow, String keyword) throws Exception;
    public void decreaseProductsStock(int productsIdx, int quantity) throws Exception;

}

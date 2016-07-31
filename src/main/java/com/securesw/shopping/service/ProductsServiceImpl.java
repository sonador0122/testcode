package com.securesw.shopping.service;

import com.securesw.shopping.dao.CartDAO;
import com.securesw.shopping.dao.ProductsDAO;
import com.securesw.shopping.dao.OrdersDAO;
import com.securesw.shopping.vo.CartVO;
import com.securesw.shopping.vo.ProductsVO;
import com.securesw.shopping.vo.OrdersVO;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;



@Service("productsService")
public class ProductsServiceImpl implements ProductsService {

    @Resource(name = "productsDAO")
    private ProductsDAO productsDAO;
    
    @Resource(name = "ordersDAO")
    private OrdersDAO ordersDAO;
    
    @Resource(name = "cartDAO")
    private CartDAO cartDAO;

    @Override
    public int selectCount(String memberId) throws Exception {
        return productsDAO.selectCount(memberId);
    }

    @Override
    public int selectCount(String memberId, String keyword) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("memberId", memberId);
        map.put("keyword", keyword);

        return productsDAO.selectCount(map);
    }

    @Override
    public ProductsVO selectOne(int productsIdx) throws Exception {
        return productsDAO.selectOne(productsIdx);
    }

    @Override
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow) throws Exception {
        return productsDAO.selectList(memberId, firstRow, endRow);
    }

    @Override
    public List<ProductsVO> selectList(String memberId, int firstRow, int endRow, String keyword) throws Exception {

        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("memberId", memberId);
        map.put("rowBounds", rowBounds);
        map.put("keyword", keyword);
        return productsDAO.selectList(map);
    }

    @Override
    public int insert(ProductsVO productsVO) {
        return productsDAO.insert(productsVO);
    }

    @Override
    public int insert(int boardIdx, int productsIdx) {
        return productsDAO.insert(boardIdx, productsIdx);
    }

    @Override
    public int update(ProductsVO productsVO) {
        return productsDAO.update(productsVO);
    }

    @Override
    public int delete(int productsIdx) {
        return productsDAO.delete(productsIdx);
    }

    @Override
    public void increaseProductsCount(int boardIdx) throws Exception {
        productsDAO.increaseProductsCount(boardIdx);
    }

    @Override
    public void decreaseProductsCount(int boardIdx) throws Exception {
        productsDAO.decreaseProductsCount(boardIdx);
    }
    
    @Override
	public int addorderlist(OrdersVO ordersVO) throws Exception {
		return ordersDAO.insert(ordersVO);
	}

    @Override
    public List<Integer> selectBoardProductsByBoard(int boardIdx) throws Exception {
        return productsDAO.selectBoardProductsByBoard(boardIdx);
    }

    @Override
    public List<Integer> selectBoardProductsByProducts(int productsIdx) throws Exception {
        return productsDAO.selectBoardProductsByProducts(productsIdx);
    }

    @Override
    public int deleteBoardProductsByBoard(int boardIdx) throws Exception {
        return productsDAO.deleteBoardProductsByBoard(boardIdx);
    }

    @Override
    public int deleteBoardProductsByProducts(int productsIdx) throws Exception {
        return productsDAO.deleteBoardProductsByProducts(productsIdx);
    }

	@Override
	public int addcartlist(CartVO cartVO) throws Exception {
		// TODO Auto-generated method stub
		return cartDAO.insert(cartVO);
	}

	@Override
	public int cartDelete(int idx) {
		// TODO Auto-generated method stub
		return cartDAO.delete(idx);
	}

	@Override
	public CartVO cartOne(int boardIdx, String email) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("boardIdx", boardIdx);
		paraMap.put("memberEmail", email);
		return cartDAO.selectOne(paraMap);
	}

	@Override
	public int cartDelete(int boardIdx, String email) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("boardIdx", boardIdx);
		paraMap.put("memberEmail", email);
		return cartDAO.deleteMap(paraMap);
	}

	@Override
	public int cartChange(int quantity, int cartIdx) {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("idx", cartIdx);
		paraMap.put("quantity", quantity);
		return cartDAO.changeMap(paraMap);
	}

    @Override
    public int selectCountForStock(int maxStock, int minStock, String keyword) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("maxStock", maxStock);
        map.put("minStock", minStock);
        map.put("keyword", keyword);

        return productsDAO.selectCountForStock(map);
    }

    @Override
    public List<ProductsVO> selectListForStock(int maxStock, int minStock, int firstRow, int endRow, String keyword) throws Exception {
        RowBounds rowBounds = new RowBounds(firstRow - 1, endRow - firstRow + 1);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("maxStock", maxStock);
        map.put("minStock", minStock);
        map.put("rowBounds", rowBounds);
        map.put("keyword", keyword);

        return productsDAO.selectListForStock(map);
    }

    @Override
    public void decreaseProductsStock(int productsIdx, int quantity) throws Exception {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("productsIdx", productsIdx);
        map.put("quantity", quantity);

        productsDAO.decreaseProductsStock(map);
    }
}

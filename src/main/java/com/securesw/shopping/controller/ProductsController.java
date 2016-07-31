package com.securesw.shopping.controller;

import com.securesw.shopping.error.ProductsNotFoundException;
import com.securesw.shopping.service.BoardService;
import com.securesw.shopping.service.MemberService;
import com.securesw.shopping.vo.CartVO;
import com.securesw.shopping.vo.ProductsVO;
import com.securesw.shopping.vo.PagingVO;
import com.securesw.shopping.vo.MemberVO;
import com.securesw.shopping.service.ProductsService;
import com.securesw.shopping.util.Util;
import com.securesw.shopping.vo.OrdersVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(value = "/products")
public class ProductsController {

    @Resource(name = "productsService")
    private ProductsService productsService;

    @Resource(name = "boardService")
    private BoardService boardService;
    
    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "util")
    private Util util;


    @RequestMapping(value = "/list.do")
    public ModelAndView productsList(@RequestParam(value = "p", required = false) String requestPageString,
                                  @RequestParam(value="q",required=false) String keyword,
                                  @RequestParam(value = "s", required = false, defaultValue = "default") String s,
                                  Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        if(s.equals("product")){
            modelAndView.addObject("isProduct", true);
        } else  {
            modelAndView.addObject("isProduct", false);
        }


        String memberId = util.isMemberId(auth);

        if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
        int requestPage = Integer.parseInt(requestPageString);
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/products/list.do");

        if(keyword == null || keyword.equals("")) {
            keyword = null;
        }

        int totalCount = productsService.selectCount(memberId, keyword);



        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/products/list");


        if(totalCount == 0){
            modelAndView.addObject("productsVOList", Collections.<ProductsVO>emptyList());
            modelAndView.addObject("hasProducts", false);
            return modelAndView;
        }

        List<ProductsVO> productsVOList = productsService.selectList(memberId, pagingVO.getFirstRow(), pagingVO.getEndRow(), keyword);

        modelAndView.addObject("productsVOList", productsVOList);
        modelAndView.addObject("hasProducts", true);

        return modelAndView;
    }


    @RequestMapping(value = "/write.do")
    public String productsWrite(Authentication auth) throws Exception{

        util.isMemberId(auth);
        return "/products/writeForm";
    }


    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String productsWrite(@RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "size", required = true) String size,
                             @RequestParam(value = "material", required = true) String material,
                             @RequestParam(value = "component", required = true) String component,
                             @RequestParam(value = "options", required = true) String options,
                             @RequestParam(value = "manufacturer", required = true) String manufacturer,
                             @RequestParam(value = "madein", required = true) String madein,
                             @RequestParam(value = "description", required = true) String description,
                             @RequestParam(value = "price", required = true) Integer price,
                             @RequestParam(value = "stock", required = true) Integer stock,
                             Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        int memberIdx = memberService.selectOneNo(memberId);

        ProductsVO productsVO = new ProductsVO();
        productsVO.setName(name);
        productsVO.setSize(size);
        productsVO.setMaterial(material);
        productsVO.setComponent(component);
        productsVO.setOptions(options);
        productsVO.setManufacturer(manufacturer);
        productsVO.setMadein(madein);
        productsVO.setDescription(description);
        productsVO.setPrice(price);
        productsVO.setStock(stock);
        productsVO.setMemberIdx(memberIdx);
        productsVO.setMemberEmail(memberId);

        productsService.insert(productsVO);

        return "redirect:/products/list.do";
    }


    @RequestMapping(value = "/read.do")
    public ModelAndView productsRead(@RequestParam(value = "productsIdx", required = true) Integer productsIdx)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        ProductsVO productsVO = productsService.selectOne(productsIdx);

        if(productsVO == null) throw new ProductsNotFoundException("상품이 존재하지 않음 : " + productsIdx);

        modelAndView.addObject("productsVO", productsVO);
        modelAndView.setViewName("/products/read");

        return modelAndView;
    }


    @RequestMapping(value = "/update.do")
    public ModelAndView productsUpdate(@RequestParam(value = "productsIdx", required = true) Integer productsIdx,
                                    Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        ProductsVO productsVO = productsService.selectOne(productsIdx);
        if(productsVO == null) throw new ProductsNotFoundException("상품이 존재하지 않음 : " + productsIdx);

        util.isEqualMemberId(productsVO.getMemberEmail(), memberId);

        modelAndView.addObject("productsVO", productsVO);
        modelAndView.setViewName("/products/updateForm");
        return modelAndView;
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String productsUpdate(@RequestParam(value = "productsIdx", required = true) Integer productsIdx,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "size", required = true) String size,
                              @RequestParam(value = "material", required = true) String material,
                              @RequestParam(value = "component", required = true) String component,
                              @RequestParam(value = "options", required = true) String options,
                              @RequestParam(value = "manufacturer", required = true) String manufacturer,
                              @RequestParam(value = "madein", required = true) String madein,
                              @RequestParam(value = "description", required = true) String description,
                              @RequestParam(value = "price", required = true) Integer price,
                              @RequestParam(value = "stock", required = true) Integer stock,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        ProductsVO productsVO = productsService.selectOne(productsIdx);
        if(productsVO == null) throw new ProductsNotFoundException("상품이 존재하지 않음 : " + productsIdx);

        util.isEqualMemberId(productsVO.getMemberEmail(), memberId);

        ProductsVO newProductsVO = new ProductsVO();
        newProductsVO.setIdx(productsIdx);

        if(!productsVO.getName().equals(name)) newProductsVO.setName(name);
        if(!productsVO.getSize().equals(size)) newProductsVO.setSize(size);
        if(!productsVO.getMaterial().equals(material)) newProductsVO.setMaterial(material);
        if(!productsVO.getComponent().equals(component)) newProductsVO.setComponent(component);
        if(!productsVO.getOptions().equals(options)) newProductsVO.setOptions(options);
        if(!productsVO.getManufacturer().equals(manufacturer)) newProductsVO.setManufacturer(manufacturer);
        if(!productsVO.getMadein().equals(madein)) newProductsVO.setMadein(madein);
        if(!productsVO.getDescription().equals(description)) newProductsVO.setDescription(description);
        newProductsVO.setPrice(price);
        newProductsVO.setStock(stock);

        int updateCount = productsService.update(newProductsVO);
        if (updateCount == 0) throw new ProductsNotFoundException("상품이 존재하지 않음 : " + productsIdx);

        return "redirect:/products/read.do?p=" + page + "&productsIdx=" + productsIdx;
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String productsDelete(@RequestParam(value = "productsIdx", required = true) Integer productsIdx,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        ProductsVO productsVO = productsService.selectOne(productsIdx);
        if(productsVO == null) throw new ProductsNotFoundException("상품이 존재하지 않음 : " + productsIdx);

        util.isEqualMemberId(productsVO.getMemberEmail(), memberId);

        List<Integer> boardProductsList = productsService.selectBoardProductsByProducts(productsIdx);

        if(!boardProductsList.isEmpty()) {
            for(int boardIdx: boardProductsList) {
                productsService.decreaseProductsCount(boardIdx);
            }
            productsService.deleteBoardProductsByProducts(productsIdx);
        }

        productsService.delete(productsIdx);

        return "redirect:/products/list.do?p=" + page;
    }


    @RequestMapping(value = "/listByBoard.do")
    public ModelAndView productsListByBoard(@RequestParam(value = "boardIdx", required = true) Integer boardIdx)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        List<Integer> boardProductsList = productsService.selectBoardProductsByBoard(boardIdx);
        List<ProductsVO> productsVOList = new ArrayList<ProductsVO>();


        if(!boardProductsList.isEmpty()) {
            for(int productsIdx: boardProductsList) {
                productsVOList.add(productsService.selectOne(productsIdx));
            }

            modelAndView.addObject("productsVOList", productsVOList);
            modelAndView.addObject("hasProducts", true);
        } else {
            modelAndView.addObject("productsVOList", Collections.<ProductsVO>emptyList());
            modelAndView.addObject("hasProducts", false);
        }

        modelAndView.setViewName("/products/listByBoard");

        return modelAndView;
    }
    

    @RequestMapping("/cart.do")
	public ModelAndView cartList(HttpServletRequest request, Authentication auth) throws Exception{
   
    	ModelAndView modelandview = new ModelAndView();
    	UserDetails vo = (UserDetails) auth.getPrincipal();
    	String email = vo.getUsername();
            
        List<CartVO> lists = memberService.cartList(email);
        if(lists.isEmpty()){
        	request.setAttribute("hasMember", false);
        	return modelandview;
        }
        MemberVO memberVO = memberService.selectOneVo(email);
        modelandview.setViewName("/products/cart");
        modelandview.addObject("cartlist", lists);
        modelandview.addObject("member", memberVO);
    	request.setAttribute("hasMember", true);
    		
    	return modelandview;
	}
    
    //ajax add cart
    @RequestMapping(value="/addCartAjax.do", method=RequestMethod.POST)
    public void addCartAjax(@RequestParam(value="idx") int boardIdx,
    		@RequestParam(value = "choice") String choice,
    		HttpServletResponse response, Authentication auth)throws Exception{
    	
    	if(choice == null || choice.isEmpty()){
    		response.getWriter().print("400");
			return;
		}
    	//int requestPage = Integer.parseInt(boardIdx);
    	UserDetails vo = (UserDetails) auth.getPrincipal();
    	String email = vo.getUsername();
    	
    	if(productsService.cartOne(boardIdx,email) != null){
    		response.getWriter().print("404");
			return;
    	}
    	
		CartVO cartVO = new CartVO(1,boardIdx,email);
		
    	if(choice.compareTo("go") != 0){
    		productsService.addcartlist(cartVO);
			response.getWriter().print("200");
			return;
		}else{
			productsService.addcartlist(cartVO);
    		memberService.delGiftlist(email, boardIdx);
    		response.getWriter().print("202");
			return;
		}
    	
    }
    
    //ajax change Quantity of specific data
    @RequestMapping(value="/changeQuan.do", method=RequestMethod.POST)
    public void changeQuan(@RequestParam(value="quantity") int quantity,
    		@RequestParam(value = "idx") int cartIdx,
    		HttpServletResponse response, Authentication auth)throws Exception{
    	
    	if(quantity == 0){
    		response.getWriter().print("400");
			return;
    	}
    	
    	if(productsService.cartChange(quantity,cartIdx) == 0){
    		response.getWriter().print("400");
			return;
		}
    	
    	response.getWriter().print(quantity);
    }
    //add cart
	@RequestMapping(value="/addCart.do")
    public String addCart(@RequestParam(value="idx") int boardIdx,Model model,
    		@RequestParam(value="quantity", required = false) String quantity,
    		@RequestParam(value = "p", required = false) String page,
    		@RequestParam(value = "s", required = false) String seperate,
    		HttpServletRequest request, Authentication auth)throws Exception{
        
    	if(quantity == null || quantity.isEmpty()){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardIdx=" + boardIdx);
			return "/error/alert";
		}

    	String termQan = quantity.trim().toLowerCase();
    	
    	int quan = 1;
    	
    	try{
    		quan = Integer.parseInt(termQan);
    	}catch(Exception e){
    		model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardIdx=" + boardIdx);
			return "/error/alert";
    	}
    	
    	UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		
    	if(productsService.cartOne(boardIdx, email) != null){
    		model.addAttribute("say", "Already listed");
			model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardIdx=" + boardIdx);
			return "/error/alert";
		}
		
		CartVO cartVO = new CartVO(quan,boardIdx,email);
    	productsService.addcartlist(cartVO);
    	
        model.addAttribute("say", "Added it");
		model.addAttribute("url", request.getContextPath()+"/board/read.do?s="+seperate+"&p=" + page + "&boardIdx=" + boardIdx);
    	return "/error/alert";
    			
    }
    
    //delete cart
	@RequestMapping(value="/delCart.do")
  	public String delCart(@RequestParam("choice")String choice,@RequestParam("idx")int idx,
  			HttpServletRequest request, Model model) throws Exception{
  		
  		if(choice == null || choice.isEmpty()){
  			model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/products/cart.do");
  			return "/error/alert";
  		}
  		
  		if(choice.compareTo("true") != 0){
  			return "redirect:/products/cart.do";
  		}
  		
  		if(productsService.cartDelete(idx) == 0){
			model.addAttribute("say", "Wrong already deleted");
			model.addAttribute("url", request.getContextPath()+"/products/cart.do");
			return "/error/alert";
		}
  		return "redirect:/products/cart.do";	
  	}
  	
  	//add order
	@RequestMapping(value="/addOrders.do")
  	public String addOrders(@RequestParam("idx")int[] boardIdxs,
  			@RequestParam("addr")String address,
  			@RequestParam("post")int postcode,
  			@RequestParam("name")String receiver,
  			HttpServletRequest request, Model model
  			,Authentication auth) throws Exception{
  		
  		if(address == null || address.isEmpty() || address.isEmpty() || receiver == null || receiver.isEmpty()){
  			model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/products/cart.do");
  			return "/error/alert";
  		}
  		
  		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		MemberVO memberVO = memberService.selectOneVo(email);
		String sender = memberVO.getLastName();
		
        boolean check = false;
        String say = "[";
        List<OrdersVO> ordersVOList = new ArrayList<>();
		
		if(boardIdxs != null) {
            for (int boardIdx : boardIdxs) {
            	CartVO cartVO = productsService.cartOne(boardIdx, email);
          		int price = cartVO.getPrice();
          		int quantity = cartVO.getQuantity();



                if(!checkStock(boardIdx, quantity)){
                    check = true;
                    say += boardService.selectOne(boardIdx).getTitle();
                    say += ", ";
                }

                ordersVOList.add(new OrdersVO("Ok", email, sender, boardIdx, quantity, address, postcode, price, receiver));
            }



            if (check){
                say += "] 상품의 수량이 부족합니다. 관리자에 문의하여 주세요.";

                model.addAttribute("say", say);
                model.addAttribute("url", request.getContextPath()+"/products/cart.do");
                return "/error/alert";
            }

            for(OrdersVO ordersVO : ordersVOList){
                productsService.addorderlist(ordersVO);
                productsService.cartDelete(ordersVO.getBoardIdx(), ordersVO.getMemberEmail());
                decreaseStock(ordersVO.getBoardIdx(), ordersVO.getQuantity());
            }
        }else{
        	model.addAttribute("say", "Wrong Input");
  			model.addAttribute("url", request.getContextPath()+"/products/cart.do");
  			return "/error/alert";
        }
	
		
  		model.addAttribute("say", "Buy it successfully");
		model.addAttribute("url", request.getContextPath()+"/member/orders.do");
		return "/error/alert";
  	}


    private boolean checkStock(int boardIdx, int quantity) throws Exception{

        List<Integer> boardProductsList = productsService.selectBoardProductsByBoard(boardIdx);
        ProductsVO productsVO;

        if(!boardProductsList.isEmpty()) {
            for(int productsIdx: boardProductsList) {
                productsVO = productsService.selectOne(productsIdx);
                if(productsVO.getStock() < quantity) return false;
            }
        } else {
            return false;
        }
        return true;
    }


    private void decreaseStock(int boardIdx, int quantity) throws Exception {

        List<Integer> boardProductsList = productsService.selectBoardProductsByBoard(boardIdx);

        if (!boardProductsList.isEmpty()) {
            for (int productsIdx : boardProductsList) {
                productsService.decreaseProductsStock(productsIdx, quantity);
            }
        }
    }
}

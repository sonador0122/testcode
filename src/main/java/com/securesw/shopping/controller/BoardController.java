package com.securesw.shopping.controller;

import com.securesw.shopping.service.BoardService;
import com.securesw.shopping.service.ProductsService;
import com.securesw.shopping.service.MemberService;
import com.securesw.shopping.util.ImageUtil;
import com.securesw.shopping.vo.BoardVO;
import com.securesw.shopping.vo.ProductsVO;
import com.securesw.shopping.vo.PagingVO;
import com.securesw.shopping.error.BoardNotFoundException;
import com.securesw.shopping.util.Util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;



@Controller
@RequestMapping(value = "/board")
public class BoardController {
	public static final String loadPath = "/WEB-INF/files/";				

    @Resource(name = "boardService")
    private BoardService boardService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "productsService")
    private ProductsService productsService;

    @Resource(name = "util")
    private Util util;

    @Resource(name = "imageUtil")
	private ImageUtil imageUtil;

    @RequestMapping(value = "/list.do")
    public ModelAndView boardList(@RequestParam(value = "p", required = false) Integer requestPage,
                                  @RequestParam(value = "s", required = false) String separator,
                                  @RequestParam(value="q",required=false) String keyword) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + separator);

        if(separator == null || separator.equals("")) {
            separator = null;
        }

        if(keyword == null || keyword.equals("")) {
            keyword = null;
        }

        int totalCount;
        totalCount = boardService.selectCount(separator, keyword);


        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/board/list");


        if(totalCount == 0){
            modelAndView.addObject("boardVOList", Collections.<BoardVO>emptyList());
            modelAndView.addObject("hasBoard", false);
            return modelAndView;
        }

        List<BoardVO> boardVOList;

        boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow(), separator, keyword);


        modelAndView.addObject("boardVOList", boardVOList);
        modelAndView.addObject("hasBoard", true);

        return modelAndView;
    }


    @RequestMapping(value = "/write.do")
    public ModelAndView boardWrite(Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        util.isMemberId(auth);

        modelAndView.addObject("act", "write");
        modelAndView.setViewName("/board/writeForm");

        return modelAndView;
    }


    @SuppressWarnings("static-access")
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String boardWrite(@RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                             @RequestParam(value = "thumnail", required = false) MultipartFile multipartFile,
                             @RequestParam(value = "total_price", required = false ) Integer totalPrice,
                             @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                             Authentication auth, HttpServletRequest request, Model model, HttpSession session) throws Exception{

    	
        String memberId = util.isMemberId(auth);
        
        int groupId = boardService.generateNextGroupIdx("board");
        int memberIdx = memberService.selectOneNo(memberId);
        String imagePath = null;
        if(multipartFile != null){
	       
	  		if ( multipartFile.getSize() > 0 ) {
	  			
	  			String fileName = multipartFile.getOriginalFilename();
	  			String uploadPath = session.getServletContext().getRealPath("/")+"/WEB-INF/files/";
				
	  			if ( imageUtil.isImageFile ( fileName))
	  			{
	  				Calendar cal = Calendar.getInstance();
	  				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
	  				File uploadFile =  File.createTempFile(uploadPath + "temp/", fileType);
	  				multipartFile.transferTo( uploadFile);
	  				String tempName =  cal.getTimeInMillis() + "";
	  				String replaceName = tempName+"_thum"+ fileType;
	  				File thumbnail =  new File (uploadPath+replaceName);
	  				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
	  				imagePath = replaceName;
	  			}
	  			else{
	  				model.addAttribute("say", "Wrong Image");
	  				model.addAttribute("url", request.getContextPath()+"/board/list.do?s=" + separator);
	  				return "/error/alert";
	  			}
	  		}
        }
        BoardVO boardVO = new BoardVO();
        boardVO.setGroupIdx(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceIdx(decimalFormat.format(groupId) + "99");
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);
        boardVO.setMemberIdx(memberIdx);
        boardVO.setMemberEmail(memberId);
        boardVO.setSeparatorName(separator);
        boardVO.setImagePath(imagePath);
        
        if(separator.equals("product")){
            boardVO.setTotalPrice(totalPrice);
        } else {
            boardVO.setTotalPrice(0);
        }

        boardService.insert(boardVO);
        int boardIdx = boardService.selectLastBoardIdxByEmail(memberId);

        if(separator.equals("product")){
            String products[] = request.getParameterValues("products");

            if(products != null) {
                for (String product : products) {
                    productsService.insert(boardIdx, Integer.parseInt(product));
                    productsService.increaseProductsCount(boardIdx);
                }
            }
        }

        return "redirect:/board/list.do?s=" + separator;
    }


    @RequestMapping(value = "/read.do")
    public ModelAndView boardRead(@RequestParam(value = "boardIdx", required = true) Integer boardIdx)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        BoardVO boardVO = boardService.selectOne(boardIdx);

        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardIdx);

        boardService.increaseReadCount(boardIdx);
        boardVO.setReadCount(boardVO.getReadCount() + 1);

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/read");

        return modelAndView;
    }


    @RequestMapping(value = "/update.do")
    public ModelAndView boardUpdate(HttpServletRequest request, Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int boardIdx = Integer.parseInt(request.getParameter("boardIdx"));

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = boardService.selectOne(boardIdx);
        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardIdx);

        util.isEqualMemberId(boardVO.getMemberEmail(), memberId);

        if(boardVO.getSeparatorName().equals("product")){

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
        }

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/updateForm");
        return modelAndView;
    }


    @SuppressWarnings("static-access")
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String boardUpdate(@RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                              @RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                              @RequestParam(value = "thumnail",required=false) MultipartFile multipartFile,
                              @RequestParam(value = "total_price", required = false ) Integer totalPrice,
                              @RequestParam(value = "s", required = false) String separator,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth, HttpServletRequest request, Model model, HttpSession session ) throws Exception {

        String memberId = util.isMemberId(auth);
        
        BoardVO boardVO = boardService.selectOne(boardIdx);
        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardIdx);

        util.isEqualMemberId(boardVO.getMemberEmail(), memberId);

        BoardVO updateBoardVO = new BoardVO();
        String imagePath = updateBoardVO.getImagePath();
        updateBoardVO.setIdx(boardIdx);
        

  		if ( multipartFile.getSize() > 0 ) {
  			String fileName = multipartFile.getOriginalFilename();
  			String uploadPath = session.getServletContext().getRealPath("/")+"/WEB-INF/files/";
  			
  			if ( imageUtil.isImageFile (fileName))
  			{
  				Calendar cal = Calendar.getInstance();
  				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
  				File uploadFile =  File.createTempFile( uploadPath + "temp/", fileType);
  				multipartFile.transferTo( uploadFile);
  				String tempName =  cal.getTimeInMillis() + "";
  				String replaceName = tempName+"_thum"+ fileType;
  				File thumbnail =  new File (uploadPath+replaceName);
  				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
  				imagePath = replaceName;
  			}
  			else{
  				model.addAttribute("say", "Wrong Image");
  				model.addAttribute("url", request.getContextPath()+"/board/read.do?s=" + separator + "&p=" + page +"&boardIdx=" + boardIdx);
  				return "/error/alert";
  			}
  		}
  		
        if(!boardVO.getTitle().equals(title)) updateBoardVO.setTitle(title);
        if(!boardVO.getContent().equals(daumeditor)) updateBoardVO.setContent(daumeditor);
        if(!boardVO.getContent().equals(imagePath)) updateBoardVO.setContent(imagePath);
        
        if(separator.equals("product")){
            if(updateBoardVO.getTotalPrice()!=totalPrice) updateBoardVO.setTotalPrice(totalPrice);
        }

        int updateCount = boardService.update(updateBoardVO);
        if (updateCount == 0) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : "+ boardIdx);
        }

        if(separator.equals("product")){
            String products[] = request.getParameterValues("products");

            if(boardVO.getProductsCount()>0){
                productsService.deleteBoardProductsByBoard(boardIdx);
                boardService.setProductsCountZero(boardIdx);
            }

            if(products != null) {
                for (String product : products) {
                    productsService.insert(boardIdx, Integer.parseInt(product));
                    productsService.increaseProductsCount(boardIdx);
                }
            }
        }

        return "redirect:/board/read.do?s=" + separator + "&p=" + page +"&boardIdx=" + boardIdx;
    }


    @RequestMapping(value = "/reply.do")
    public ModelAndView boardReply(@RequestParam(value = "parentBoardIdx", required = true) Integer parentBoardIdx)
            throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        BoardVO parent = boardService.selectOne(parentBoardIdx);

        util.checkParent(parent, parentBoardIdx);

        String searchMaxSeqNum = parent.getSequenceIdx();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
        String sequenceIdx = util.getSequenceIdx(parent, lastChildSeq);

        modelAndView.addObject("act", "reply");
        modelAndView.setViewName("/board/writeForm");

        return modelAndView;

    }


    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public String boardReply(@RequestParam(value = "parentBoardIdx", required = true) Integer parentBoardIdx,
                             @RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                             @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                             Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = new BoardVO();
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);

        BoardVO parent = boardService.selectOne(parentBoardIdx);

        util.checkParent(parent, parentBoardIdx);

        String searchMaxSeqNum = parent.getSequenceIdx();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
        String sequenceIdx = util.getSequenceIdx(parent, lastChildSeq);
        int memberIdx = memberService.selectOneNo(memberId);

        boardVO.setGroupIdx(parent.getGroupIdx());
        boardVO.setSequenceIdx(sequenceIdx);
        boardVO.setMemberIdx(memberIdx);
        boardVO.setMemberEmail(memberId);
        boardVO.setSeparatorName(separator);

        int boardIdx = boardService.insert(boardVO);
        if(boardIdx == -1){
            throw new RuntimeException("DB 삽입 실패 : " + boardIdx);
        }

        return "redirect:/board/list.do?s=" + separator;
    }

     @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String boardDelete(@RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                              @RequestParam(value = "s", required = false) String separator,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = boardService.selectOne(boardIdx);
        util.isEqualMemberId(boardVO.getMemberEmail(), memberId);

        if(separator.equals("product")&&boardVO.getProductsCount()>0){
            productsService.deleteBoardProductsByBoard(boardIdx);
        }

        boardService.delete(boardIdx);

        return "redirect:/board/list.do?s=" + separator + "&p=" + page;
    }
}

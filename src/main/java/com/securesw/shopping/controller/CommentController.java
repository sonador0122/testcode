package com.securesw.shopping.controller;

import com.securesw.shopping.error.BoardNotFoundException;
import com.securesw.shopping.error.CommentNotFoundException;
import com.securesw.shopping.service.CommentService;
import com.securesw.shopping.service.MemberService;
import com.securesw.shopping.util.Util;
import com.securesw.shopping.vo.CommentVO;
import com.securesw.shopping.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(value = "/comment")
public class CommentController {

	String memberId;
	
    @Resource(name = "commentService")
    private CommentService commentService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "util")
    private Util util;


    @RequestMapping(value = "/list.do")
    public ModelAndView commentList(@RequestParam(value = "cp", required = false) Integer requestPage,
                                    @RequestParam(value = "cs", required = false) String separator,
                                    @RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                                    Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);

        if(boardIdx == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardIdx);
        if(boardIdx < 0) throw new IllegalArgumentException("board idx < 1 : " + boardIdx);

        modelAndView.addObject("cs", separator);

        memberId = null;
        
        try {
            memberId = util.isMemberId(auth);
            modelAndView.addObject("isLogin", true);
            modelAndView.addObject("memberId", memberId);
        } catch (Exception e) {
        	System.err.println("list do fail :"+ e.getMessage());
            memberId = null;
            modelAndView.addObject("isLogin", false);
        }

        int totalCount;

        if(separator  == null || separator.equals(""))  {
            totalCount = commentService.selectCount(boardIdx);
        } else {
            totalCount = commentService.selectCount(boardIdx, separator);
        }

        PagingVO pagingVO = util.paging(requestPage, 3, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/comment/list");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            modelAndView.addObject("hasComment", false);
            return modelAndView;
        }

        List<CommentVO> commentVOList;

        if(separator  == null || separator.equals(""))  {
            commentVOList = commentService.selectList(boardIdx, pagingVO.getFirstRow(), pagingVO.getEndRow());
        } else {
            commentVOList = commentService.selectList(boardIdx, pagingVO.getFirstRow(), pagingVO.getEndRow(), separator);
        }

        modelAndView.addObject("commentVOList", commentVOList);
        modelAndView.addObject("hasComment", true);

        return modelAndView;
    }


    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String commentWrite(@RequestParam(value = "content", required = true) String content,
                               @RequestParam(value = "boardIdxr", required = true ) Integer boardIdx,
                               @RequestParam(value = "cs", required = true, defaultValue = "default") String separator,
                               Authentication auth) throws Exception{

        memberId = util.isMemberId(auth);

        int groupId = commentService.generateNextGroupIdx("comment");
        int memberIdx = memberService.selectOneNo(memberId);

        CommentVO commentVO = new CommentVO();
        commentVO.setGroupIdx(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        commentVO.setSequenceIdx(decimalFormat.format(groupId) + "99");
        commentVO.setContent(content);
        commentVO.setMemberIdx(memberIdx);
        commentVO.setMemberEmail(memberId);
        commentVO.setBoardIdx(boardIdx);
        commentVO.setSeparatorName(separator);

        commentService.insert(commentVO);
        commentService.increaseCommentCount(boardIdx);
        return "/common/success";
    }

    @RequestMapping(value = "/update.do")
    public ModelAndView commentUpdateForm(@RequestParam(value = "commentIdx", required = true) Integer commentIdx,
                                          @RequestParam(value = "cs", required = true) String separator,
                                          Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentIdx);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentIdx);

        util.isEqualMemberId(commentVO.getMemberEmail(), memberId);

        modelAndView.addObject("commentVO", commentVO);
        modelAndView.addObject("cs", separator);
        modelAndView.addObject("commentIdx", commentIdx);
        modelAndView.setViewName("/comment/updateForm");
        return modelAndView;
    }


    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String commentUpdate(@RequestParam(value = "commentIdx", required = true) Integer commentIdx,
                              @RequestParam(value = "content", required = true) String content,
                              Authentication auth) throws Exception {

        memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentIdx);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentIdx);

        util.isEqualMemberId(commentVO.getMemberEmail(), memberId);

        commentVO = new CommentVO();
        commentVO.setIdx(commentIdx);
        commentVO.setContent(content);

        int updateCount = commentService.update(commentVO);
        if (updateCount == 0) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentIdx);

        return "/common/success";
    }


    @RequestMapping(value = "/reply.do")
    public ModelAndView commentReply(@RequestParam(value = "parentCommentIdx", required = true) Integer parentCommentIdx,
                               @RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                               @RequestParam(value = "cs", required = true, defaultValue = "default") String separator)
            throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        CommentVO parent = commentService.selectOne(parentCommentIdx);

        util.checkParent(parent, parentCommentIdx);

        String searchMaxSeqNum = parent.getSequenceIdx();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
        String sequenceIdx = util.getSequenceIdx(parent, lastChildSeq);

        modelAndView.addObject("parentCommentIdx", parentCommentIdx);
        modelAndView.addObject("boardIdx", boardIdx);
        modelAndView.addObject("cs", separator);
        modelAndView.setViewName("/comment/replyForm");

        return modelAndView;
    }


    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public String commentReply(@RequestParam(value = "content", required = true) String content,
                             @RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                             @RequestParam(value = "parentCommentIdx", required = true) Integer parentCommentIdx,
                             @RequestParam(value = "cs", required = true, defaultValue = "default") String separator,
                             Authentication auth) throws Exception{

        memberId = util.isMemberId(auth);

        CommentVO commentVO = new CommentVO();
        commentVO.setContent(content);
        commentVO.setBoardIdx(boardIdx);

        CommentVO parent = commentService.selectOne(parentCommentIdx);

        util.checkParent(parent, parentCommentIdx);

        String searchMaxSeqNum = parent.getSequenceIdx();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceIdx(searchMaxSeqNum, searchMinSeqNum);
        String sequenceIdx = util.getSequenceIdx(parent, lastChildSeq);
        int memberIdx = memberService.selectOneNo(memberId);

        commentVO.setGroupIdx(parent.getGroupIdx());
        commentVO.setSequenceIdx(sequenceIdx);
        commentVO.setMemberIdx(memberIdx);
        commentVO.setMemberEmail(memberId);
        commentVO.setSeparatorName(separator);

        int commentIdx = commentService.insert(commentVO);
        if(commentIdx == -1) throw new RuntimeException("DB 삽입 실패 : " + commentIdx);
        commentService.increaseCommentCount(boardIdx);

        return "/common/success";
    }


    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String commentDelete(@RequestParam(value = "boardIdx", required = true) Integer boardIdx,
                                @RequestParam(value = "commentIdx", required = true) Integer commentIdx,
                                Authentication auth) throws Exception {

        memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentIdx);
        util.isEqualMemberId(commentVO.getMemberEmail(), memberId);

        commentService.delete(commentIdx);
        commentService.decreaseCommentCount(boardIdx);

        return "/common/success";
    }

    @RequestMapping(value = "/isEqualMember.do")
    public String isEqualMember(@RequestParam(value = "commentIdx", required = true) Integer commentIdx,
                                Authentication auth) throws Exception {

        memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentIdx);
        if (commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentIdx);

        util.isEqualMemberId(commentVO.getMemberEmail(), memberId);

        return "common/success";
    }
}

package com.sybinal.shop.controller.admin;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Comments;
import com.sybinal.shop.service.comments.AdminCommentsService;

@Controller
public class CommentsController {

    @Autowired
    AdminCommentsService adminCommentsService;

    @RequestMapping(value = "/admin/comments", method = RequestMethod.GET)
    public ModelAndView comments() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/comments/comments");
        return model;
    }

    @RequestMapping(value = "/admin/commentsDataTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentsDataTable(PageInformation pageInfo, Comments comments) throws ParseException {
        return adminCommentsService.getComments(pageInfo, comments);
    }

    @RequestMapping(value = "/admin/comments/examine", method = RequestMethod.POST)
    @ResponseBody
    public Integer examineComments(@RequestBody List<Comments> commentsList) throws ParseException {
        return adminCommentsService.examineComments(commentsList);
    }

    @RequestMapping(value = "/admin/comments/delete", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteComments(@RequestBody List<Comments> commentsList) throws ParseException {
        return adminCommentsService.deleteComments(commentsList);
    }

}

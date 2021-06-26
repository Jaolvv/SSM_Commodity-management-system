package com.sybinal.shop.controller.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Article;
import com.sybinal.shop.service.article.ArticleService;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/admin/article", method = RequestMethod.GET)
    public ModelAndView receiveMain() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/article/articlelist");
        return model;
    }

    @RequestMapping(value = "/admin/articleadd", method = RequestMethod.GET)
    public ModelAndView receiveadd() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/article/articleadd");
        return model;
    }

    @RequestMapping(value = "/admin/receiveGetArticle")
    public @ResponseBody
    Map<String, Object> receiveGetArticleCategory(PageInformation pageInfo, Article searchVo) {
        return articleService.getAllArticleByModel(pageInfo, searchVo);
    }

    @RequestMapping(value = "/admin/receiveUpdateArticle")
    public ModelAndView receiveUpdateArticle(int id) {
        Map<String, Object> data = new HashMap<String, Object>();
        Article model = articleService.getArticleById(id);
        data.put("model", model);
        return new ModelAndView("admin/article/articleadd", data);
    }

    @RequestMapping(value = "/admin/receiveSaveArticle")
    public @ResponseBody
    int receiveSaveArticle(@RequestBody Article saveVo) throws IllegalAccessException, InvocationTargetException {
        return articleService.saveArticle(saveVo);
    }

    @RequestMapping(value = "/admin/receiveSetArticle")
    public @ResponseBody
    int receiveSetArticle(@RequestBody Article saveVo) throws IllegalAccessException, InvocationTargetException {
        return articleService.modArticle(saveVo);
    }

    @RequestMapping(value = "/admin/receiveDelArticle")
    public @ResponseBody
    int receiveDelArticle(@RequestBody int id) throws IllegalAccessException, InvocationTargetException {
        return articleService.delArticle(id);
    }


}

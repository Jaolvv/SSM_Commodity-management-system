package com.sybinal.shop.controller.admin;

import java.lang.reflect.InvocationTargetException;
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
import com.sybinal.shop.model.ArticleCategory;
import com.sybinal.shop.service.article.ArticleCategoryService;

@Controller
public class ArticleCategoryController {

    @Autowired
    ArticleCategoryService articleCategoryService;

    @RequestMapping(value = "/admin/articlecategory", method = RequestMethod.GET)
    public ModelAndView receiveMain() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/article/articlecategory");
        return model;
    }

    @RequestMapping(value = "/admin/receiveGetArticleCategory")
    public @ResponseBody
    Map<String, Object> receiveGetArticleCategory(PageInformation pageInfo, ArticleCategory searchVo) {
        return articleCategoryService.getAllArticleCategory(pageInfo, searchVo);

    }

    @RequestMapping(value = "/admin/receiveUpdateArticleCategory")
    public @ResponseBody
    ArticleCategory receiveUpdateArticleCategory(@RequestBody int id) {
        return articleCategoryService.getArticleCategoryById(id);
    }

    @RequestMapping(value = "/admin/receiveSaveArticleCategory")
    public @ResponseBody
    Map<String, String> receiveSaveArticleCategory(@RequestBody ArticleCategory saveVo) throws IllegalAccessException, InvocationTargetException {
        return articleCategoryService.saveArticleCategory(saveVo);
    }

    @RequestMapping(value = "/admin/receiveSetArticleCategory")
    public @ResponseBody
    Map<String, String> receiveSetArticleCategory(@RequestBody ArticleCategory saveVo) throws IllegalAccessException, InvocationTargetException {
        return articleCategoryService.modArticleCategory(saveVo);
    }

    @RequestMapping(value = "/admin/receiveDelArticleCategory")
    public @ResponseBody
    Map<String, String> receiveDelArticleCategory(@RequestBody ArticleCategory delVo) throws IllegalAccessException, InvocationTargetException {
        return articleCategoryService.delArticleCategory(delVo);
    }

    @RequestMapping(value = "/admin/receiveAllCategory")
    public @ResponseBody
    List<ArticleCategory> receiveAllCategory() {
        return articleCategoryService.getAllArticleCategory();
    }

}

package com.sybinal.shop.controller.admin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.ProductCategory;
import com.sybinal.shop.service.catalog.ProductCategoryService;
import com.sybinal.shop.utils.UserUtils;

@Controller
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/admin/productCategoryList", method = RequestMethod.GET)
    public ModelAndView productCategoryManage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/productCategory/productCategoryList");
        return model;
    }

    @RequestMapping(value = "/admin/addCategory", method = RequestMethod.GET)
    public ModelAndView addProductCategory() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/productCategory/productCategoryEdit");
        return model;
    }

    @RequestMapping(value = "/admin/productCategorynfoDataTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> productCategorynfoDataTable(PageInformation pageInfo, ProductCategory category) {
        return productCategoryService.getProductCategoryAll(pageInfo, category);
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProductCategory(@RequestBody ProductCategory productCategory) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
        Date date = new Date();
        productCategory.setUpdateTime(date);
        productCategory.setUpdateUserName(UserUtils.getUserName());
        productCategory.setCreateTime(adf.format(date));
        if (productCategory.getId() == null) {
            i = productCategoryService.addProductCategory(productCategory);
        } else {
            i = productCategoryService.updateProductCategory(productCategory);
        }
        if (i > 0) {
            map.put("error", "0"); // 成功
            map.put("data", productCategory); // 数据
        } else {
            map.put("error", "-1"); // 失败
            map.put("data", productCategory); // 数据成
        }
        return map;
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editProductCategory(Integer id) {
        ModelAndView model = new ModelAndView();
        ProductCategory productCategory = productCategoryService.getProductCategoryContent(id);
        if (productCategory != null) {
            model.addObject("productCategory", productCategory);
        }
        model.setViewName("admin/productCategory/productCategoryEdit");
        return model;
    }

    @RequestMapping(value = "/admin/checkVerify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkVerifyProduct(@RequestBody String idTemp) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(idTemp) == true) {
            map.put("error", "-1"); // 失败
        } else {
            int i = productCategoryService.getProductCount(idTemp);
            if (i == 0) {
                map.put("error", "0"); // 成功
            } else {
                map.put("error", "-1"); // 失败
            }
        }
        return map;
    }

    @RequestMapping(value = "/admin/removeProductCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProductCategory(@RequestBody String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(id) == true) {
            map.put("error", "-1"); // 失败
        } else {
            int i = productCategoryService.delteProductCategory(id);
            if (i > 0) {
                map.put("error", "0"); // 成功
            } else {
                map.put("error", "-1"); // 失败
            }
        }
        return map;
    }

    @RequestMapping(value = "/admin/validationName", method = RequestMethod.POST)
    @ResponseBody
    public String validationName(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, IOException {
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        int i = 0;
        if (StringUtils.isEmpty(id) == true) {
            i = productCategoryService.getProductNameCount(name);
        } else {
            ProductCategory pc = new ProductCategory();
            pc.setId(Integer.parseInt(id));
            pc.setName(name);
            i = productCategoryService.getProductCategoryName(pc);
        }
        if (i > 0) {
            response.getWriter().write("false");
        } else {
            response.getWriter().write("true");
        }
        return null;
    }
}

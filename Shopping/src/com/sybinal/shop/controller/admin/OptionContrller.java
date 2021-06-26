package com.sybinal.shop.controller.admin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import com.sybinal.shop.model.Option;
import com.sybinal.shop.service.option.OptionService;

@Controller
public class OptionContrller {

    @Autowired
    OptionService optionService;

    @RequestMapping(value = "/admin/optionInfo", method = RequestMethod.GET)
    public ModelAndView optionManage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/option/optionList");
        return model;
    }

    @RequestMapping(value = "/admin/optionDataTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> productCategorynfoDataTable(PageInformation pageInfo, Option option) {
        return optionService.getOptionList(pageInfo, option);
    }

    @RequestMapping(value = "/admin/addOption", method = RequestMethod.GET)
    public ModelAndView addProductCategory() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/option/optionEdit");
        return model;
    }

    @RequestMapping(value = "/admin/saveOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOption(@RequestBody Option option) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = 0;
        if (option.getId() == null) {
            i = optionService.addOptionInfo(option);
        } else {
            i = optionService.updateOptionInfo(option);
        }
        if (i > 0) {
            map.put("error", "0"); // 成功
            map.put("data", option); // 数据
        } else {
            map.put("error", "-1"); // 失败
            map.put("data", option); // 数据成
        }
        return map;
    }

    @RequestMapping(value = "/admin/editOption", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView editProductCategory(Integer ids) {
        ModelAndView model = new ModelAndView();
        Option option = optionService.getOptionContent(ids);
        if (option != null) {
            model.addObject("option", option);
            model.setViewName("admin/option/optionEdit");
        } else {
            model.setViewName("admin/option/optionList");
        }
        return model;
    }

    @RequestMapping(value = "/admin/removeOption", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProductCategory(@RequestBody String idTemp) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(idTemp) == true) {
            map.put("error", "-1"); // 失败
        } else {
            int i = optionService.delteOption(idTemp);
            if (i > 0) {
                map.put("error", "0"); // 成功
            } else {
                map.put("error", "-1"); // 失败
            }
        }
        return map;
    }

    @RequestMapping(value = "/admin/optionName", method = RequestMethod.POST)
    @ResponseBody
    public String validationOptionName(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, IOException {
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        int i = 0;
        if (StringUtils.isEmpty(id) == true) {
            i = optionService.getOptionNameCount(name);
        } else {
            Option option = new Option();
            option.setId(Integer.parseInt(id));
            option.setName(name);
            i = optionService.getOptionNameRows(option);
        }
        if (i > 0) {
            response.getWriter().write("false");
        } else {
            response.getWriter().write("true");
        }
        return null;
    }

}

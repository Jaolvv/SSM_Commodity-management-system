package com.sybinal.shop.controller.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.model.Logistics;
import com.sybinal.shop.model.Order;
import com.sybinal.shop.service.order.OrderService;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/order/select", method = RequestMethod.GET)
    public ModelAndView orderManage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/order/orderInfoMain");
        return model;
    }


    @RequestMapping(value = "/order/orderInfoDataTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userInfoDataTable(PageInformation pageInfo, Order order) throws Exception {
        if (!StringUtils.isEmpty(order.getStartPrice())) {
            order.setStartPrice(String.valueOf((Integer.parseInt(order.getStartPrice()) * 100)));
        }
        if (!StringUtils.isEmpty(order.getEndPrice())) {
            order.setEndPrice(String.valueOf((Integer.parseInt(order.getEndPrice()) * 100)));
        }
        return orderService.getOrderManage(pageInfo, order);
    }

    @RequestMapping(value = "admin/order/History", method = RequestMethod.POST)
    public ModelAndView orderHistgoryManage(Order order) throws IllegalAccessException, InvocationTargetException {
        ModelAndView model = new ModelAndView();
        order = orderService.getOrderItemData(order);
        order.setOrderHistoryList(orderService.getOrderHistoryManage(order));
        model.setViewName("admin/order/orderDetail");
        model.addObject("order", order);
        return model;
    }

    @RequestMapping(value = "/order/orderInfoHistoryDataTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userInfoHistoryDataTable(PageInformation pageInfo, Order order) throws Exception {
        return orderService.getOrderHistoryManage(pageInfo);
    }

    @RequestMapping(value = "admin/order/saveOrderLogistics", method = RequestMethod.POST)
    public @ResponseBody
    int saveOrderLogistics(@RequestBody Logistics logistics) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logistics.setUserName(userDetails.getUsername());
        orderService.saveLogistics(logistics);
        return 1;
    }
}

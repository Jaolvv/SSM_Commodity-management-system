package com.sybinal.shop.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sybinal.shop.model.EWallet;
import com.sybinal.shop.service.ewallet.EwalletService;

@Controller
public class EWalletController {

    @Autowired
    private EwalletService ewalletService;

    @RequestMapping("/admin/eWallet")
    public ModelAndView eWallet() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/eWallet/eWallet");
        return model;
    }

    @RequestMapping("/admin/eWallet/save")
    @ResponseBody
    public Map<String, Object> saveEwallet(@RequestBody EWallet eWallet) {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String dateParse = sdf.format(date);
        eWallet.setUpdtime(dateParse);
        int result = ewalletService.saveEwallet(eWallet);
        if (result > 0) {
            map.put("error", "0"); // 成功
            map.put("data", eWallet); // 数据
        } else {
            map.put("error", "-1"); // 失败
            map.put("data", eWallet); // 数据成
        }
        return map;
    }
}

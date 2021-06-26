package com.sybinal.shop.controller.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/admin/newUploadfile", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> newUploadFile(@RequestParam List<MultipartFile> file, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //分别传给后台 前端和移动端，只需将下面的path地址分别上传3次到不同的地址空间
        String path = request.getSession().getServletContext().getRealPath("/");
        Map<String, String> map = new HashMap<String, String>();
        //update by laoyang
        path = path + "resources/upload/";
        FileOutputStream output = null;
        try {
            if (file != null && file.size() > 0) {
                for (MultipartFile fileObj : file) {
                    String fileType = fileObj.getOriginalFilename().split("\\.")[1];
                    output = new FileOutputStream(path + uuid + "." + fileType);
                    //update by laoyang
                    String url = "/resources/upload/";
                    IOUtils.copy(fileObj.getInputStream(), output);
                    map.put("errorCode", "0");
                    map.put("errorMsg", "success");
                    map.put("fileName", url + uuid + "." + fileType);
                }
            }
        } catch (Exception e) {
            map.put("errorCode", "-1");
            map.put("errorMsg", "error");
        } finally {
            if (output != null) {
                output.close();
            }
        }
        return map;
    }
}

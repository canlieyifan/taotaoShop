package com.kk.controller;


import com.kk.utils.FastDFSClient;
import com.kk.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FileUploadController {

    //将图片服务器的"域名" 注入
    @Value("${IMAGE_SERVER_URL}")
    private String server_url;


    /*
            tips:
            1.由于浏览器对富文本兼容不同(不支持text/json) 设置响应头为text/plain
            2.@ResponseBody 该注解表示 返回数据不通过视图解析器 相当于直接write
              识别返回参数为对象会自动转换json数据
     */
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")

    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        try {
//            获取文件服务上传器
            FastDFSClient fastDFSClient = new FastDFSClient("D:\\workspace\\e3-manager-web\\src\\main\\resources\\spring\\fdfs_client.conf");
//            获取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String exFile = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//            上传文件到图片服务器
            String url =  fastDFSClient.uploadFile(uploadFile.getBytes(), exFile);

//            生成url路径 作为返回值

            url = server_url+url;
            HashMap<Object, Object> map = new HashMap<>();
            map.put("error",0);
            map.put("url",url);
            return JsonUtils.objectToJson(map);
        }catch (Exception exception){
            HashMap<Object, Object> map = new HashMap<>();
            map.put("error",1);
            map.put("message","上传图片错误 请联系管理员");
            return JsonUtils.objectToJson(map);
        }
    }


}

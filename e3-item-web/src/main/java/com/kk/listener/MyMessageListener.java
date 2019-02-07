package com.kk.listener;

import com.kk.ItemService;
import com.kk.pojo.ItemDesc;
import com.kk.pojo.ext.ItemView;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class MyMessageListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("D:\\Freemarker\\item\\")
    private String filePath;


    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            ItemDesc itemDesc = itemService.selectItemDescById(itemId);
            ItemView itemView = itemService.createItemView(itemId);

//            获得配置对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");

            Map map = new HashMap<String,Object>();

            map.put("item",itemView);
            map.put("itemDesc",itemDesc);


            FileWriter fileWriter = new FileWriter(filePath+itemId+".html");
            template.process(map,fileWriter);

            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.graduate.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.StringWriter;

public class VHelper {
    @Autowired
    ResourceLoader resourceLoader;
    private final VelocityEngine velocityEngine;
    private VelocityContext velocityContext;
    private final String templateFilePath;
    /**
     * @author sadbigpig@foxmail.com
     * VHelper的初始化工作
     * tfp 是文件的工作目录
     * @return VHelper的实体
     * @date  2022/12/14
     */
    public VHelper(String tfp){
        try{
            velocityEngine = new VelocityEngine();
            // 获取工作目录
            String baseDir = System.getProperty("user.dir");
            // 初始工作
            velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER,"file");
            velocityEngine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, baseDir);
            velocityEngine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_CACHE,true);
            velocityEngine.setProperty(VelocityEngine.INPUT_ENCODING, "utf-8");
            velocityEngine.setProperty(VelocityEngine.OUTPUT_ENCODING, "utf-8");
            velocityEngine.init();
            velocityContext = new VelocityContext();
            //加一个日期格式化工具
            templateFilePath = tfp;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * @author sadbigpig@foxmail.com
     * 设置Velocity的变量
     * key 是 键， Value 是 值
     * @return VHelper实体类
     * @date  2022/12/14
     */
    public VHelper setVar(String key,Object value){
        try {
            if(velocityContext == null){
                velocityContext = new VelocityContext();
            }
            velocityContext.put(key,value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return this;  // 链式编程
    }

    /**
     * 渲染页面
     * @return 字节流
     */
    public String render() {
        try {
            StringWriter writer = new StringWriter();
            // 从文件中读取模板
            Template template = velocityEngine.getTemplate(templateFilePath);
            // 合并模板
            template.merge(velocityContext, writer);
            // 输出字符
            String result = writer.toString();
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
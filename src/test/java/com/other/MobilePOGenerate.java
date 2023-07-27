package com.other;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
*@description 移动端PO模版生成
*@date 2023/6/15
*@author zhibindu1
*/
public class MobilePOGenerate {

    public static void main(String[] args) {
        // 创建数据模型
        TemplateDataModel dataModel = new TemplateDataModel();

        // 添加元素到数据模型
        List<Element> elements =new ArrayList<>();

        dataModel.setElements(elements);

        // 生成 PO 类
        try {
            TemplateProcessor templateProcessor = new TemplateProcessor();
            templateProcessor.generatePOClass(dataModel);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        // 生成测试类
        try {
            TemplateProcessor templateProcessor = new TemplateProcessor();
            templateProcessor.generateTestClass(dataModel);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}

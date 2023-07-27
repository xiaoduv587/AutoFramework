package com.other;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateProcessor {

    private static final String TEMPLATE_DIR = "templates";

    private static final String PO_TEMPLATE_FILE = "po_template.ftl";
    private static final String TEST_TEMPLATE_FILE = "test_template.ftl";
    private static final String OUTPUT_DIRECTORY = "generated_code/";

    private Configuration configuration;

    public TemplateProcessor() {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(TemplateProcessor.class, "/"+ TEMPLATE_DIR);

        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }

    public void generatePOClass(TemplateDataModel dataModel) throws IOException, TemplateException {
        Template template = configuration.getTemplate(PO_TEMPLATE_FILE);

//        for (Element element : dataModel.getElements()) {
            String outputFileName = OUTPUT_DIRECTORY + "TestPage" + ".java";
            Writer writer = new FileWriter(new File(outputFileName));
            template.process(dataModel, writer);
            writer.close();
//        }

    }


    public void generateTestClass(TemplateDataModel dataMode) throws IOException, TemplateException {
        Template template = configuration.getTemplate(TEST_TEMPLATE_FILE);

        String outputFileName = OUTPUT_DIRECTORY + "ShopTest.java";
        Writer writer = new FileWriter(new File(outputFileName));

        template.process(dataMode, writer);

        writer.close();
    }
}

package com.other;

import org.apache.commons.lang3.StringUtils;

public class Element {
    private String name;    //
    private String id;      //元素ID
    private String note;    //注释


    public Element(String name, String id) {
        this.name = name;
        this.id = id;
        this.note = "";
    }

    public Element(String name, String id, String note) {
        this.name = name;
        this.id = id;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getFieldName() {
        // 可以根据需要自定义生成字段名的逻辑
        String[] words = name.split(" ");
        StringBuilder fieldName = new StringBuilder();
        for (String word : words) {
            fieldName.append(StringUtils.capitalize(word));
        }
        return StringUtils.uncapitalize(fieldName.toString());
    }

    public String getMethodName() {
        // 可以根据需要自定义生成方法名的逻辑
        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);  // 首字母大写后的名称

        return capitalized;
    }

    public String getClassName() {
        // 可以根据需要自定义生成类名的逻辑
        String[] words = name.split(" ");
        StringBuilder className = new StringBuilder();
        for (String word : words) {
            className.append(StringUtils.capitalize(word));
        }
        return StringUtils.capitalize(className.toString().replaceAll("[^a-zA-Z0-9]", ""));
    }

    public boolean isClickElement() {
        return name.endsWith("Btn");
    }

    public boolean isTextElement() {
        return name.endsWith("Tv");
    }

    public boolean isInputFieldElement() {
        return name.endsWith("Ed");
    }

    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

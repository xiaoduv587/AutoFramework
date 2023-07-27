package com.other;


import java.util.ArrayList;
import java.util.List;

public class TemplateDataModel {

    private List<Element> elements;

    public TemplateDataModel() {
        elements = new ArrayList<>();
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }
}

package com.example.myapplication.Model;

public class template {
    private String templateId;
    private String templateName;

    public template(String day_id, String day_name) {
        this.templateId = day_id;
        this.templateName = day_name;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}

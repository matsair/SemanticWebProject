package com.matsschade.semanticquizapp.Objects;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QuestionTemplate {


    private String query;
    private String element;
    private String attribute;
    private String question;
    private String attributeType;
    private String questionType;



    private String URI;

    public String getAttributeUnit() {
        return attributeUnit;
    }

    private String attributeUnit;

    public QuestionTemplate(String query, String element, String attribute, String question, String type, String attributeUnit, String URI){
        this.query = query;
        this.element = element;
        this.attribute = attribute;
        this.question = question;
        this.attributeType = type;
        this.attributeUnit = attributeUnit;
        this.URI = URI;
    }

    public String getElement() {
        return element;
    }
    public String getAttribute() {
        return attribute;
    }

    public String getQuery() {
        return query;
    }

    public String getQuestion() {
        return question;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public String getURI() {
        return URI;
    }
}

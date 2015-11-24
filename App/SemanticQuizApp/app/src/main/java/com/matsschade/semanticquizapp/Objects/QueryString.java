package com.matsschade.semanticquizapp.Objects;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QueryString {


    private String query;
    private String element;
    private String attribute;

    public QueryString (String query, String element, String attribute){
        this.query = query;
        this.element = element;
        this.attribute = attribute;
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


}

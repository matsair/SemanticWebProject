package com.matsschade.semanticquizapp.models;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QueryString {


    private String query;
    private String element;

    public QueryString (String query, String element){
        this.query = query;
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public String getQuery() {
        return query;
    }


}

package com.matsschade.semanticquizapp.models;


import com.hp.hpl.jena.query.Query;

/**
 * Created by Mats on 11/11/15.
 */
public class Company {

    private String name;
    private Query q;
    private String revenue;

    public Company(String name) {
        this.name = name;
    }

    public Query getRevenue() {
        return this.q;
    }

}

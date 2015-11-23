package com.matsschade.semanticquizapp.models;


import com.hp.hpl.jena.query.Query;

/**
 * Created by Mats on 10/11/15.
 */
public class City {

    private Query q;

    public City(int limit) {
    }

    public Query getQuery() {
        return q;
    }
}

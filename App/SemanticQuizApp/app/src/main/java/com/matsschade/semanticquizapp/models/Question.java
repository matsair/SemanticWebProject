package com.matsschade.semanticquizapp.models;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class Question {

    private int categoryID;
    private ResultSet matrix;
    private String q;

    public Question (int categoryID) {
        this.categoryID = categoryID;

        //Initialize the matrix
        q = Queries.getQuery(categoryID);
        Query query = QueryFactory.create(q);
        String endpoint = "http://dbpedia.org/sparql";
        //String endpoint = "http://linkedmdb.org/sparql";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        try {
            this.matrix = qexec.execSelect();
            ResultSetFormatter.out(System.out, matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
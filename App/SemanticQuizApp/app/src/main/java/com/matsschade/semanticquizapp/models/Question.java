package com.matsschade.semanticquizapp.models;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class Question {

    private int categoryID;
    private ResultSet matrix;
    private QueryString q;
    private String question;
    private String candA;
    private String candB;
    private String candC;
    private String candD;
    private String correctAnswer;


    public String getCandD() {
        return candD;
    }

    public String getCandC() {
        return candC;
    }

    public String getCandB() {
        return candB;
    }

    public String getCandA() {
        return candA;
    }

    public Question (int categoryID) {
        this.categoryID = categoryID;
        setMatrix(categoryID);
        nameCandidates();
    }


    private void setMatrix (int categoryID) {

        //Initialize the matrix
        q = QueryStrings.getRandomQueryString(categoryID);
        Query query = QueryFactory.create(q.getQuery());
        String endpoint = "http://dbpedia.org/sparql";
        //String endpoint = "http://linkedmdb.org/sparql";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        try {
            this.matrix = qexec.execSelect();

            //This also moves the matrix to the last row --> commented out to avoid errors
            //ResultSetFormatter.out(System.out, this.matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nameCandidates(){
        int i = 1;
        while (this.matrix.hasNext() && (i <= 4)) {
            QuerySolution qs;
            qs = matrix.next();
            String element = String.valueOf(qs.getLiteral(q.getElement()));
            System.out.println(element);

            switch (i) {
                case 1:
                    this.candA = element;
                case 2:
                    this.candB = element;
                case 3:
                    this.candC = element;
                case 4:
                    this.candD = element;
            }
            i++;
        }
    }
}
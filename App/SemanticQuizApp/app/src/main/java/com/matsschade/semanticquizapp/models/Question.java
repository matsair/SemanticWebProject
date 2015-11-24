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
    private String candAName, candBName, candCName, candDName;
    private double candAAttribute, candBAttribute, candCAttribute, candDAttribute;
    private String correctAnswer;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCandDName() {
        return candDName;
    }

    public String getCandCName() {
        return candCName;
    }

    public String getCandBName() {
        return candBName;
    }

    public String getCandAName() {
        return candAName;
    }

    public Question(int categoryID) {
        this.categoryID = categoryID;
        setMatrix(categoryID);
        initializeCandidates();
        determineCorrectAnswer();
    }


    private void setMatrix(int categoryID) {

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

    private void initializeCandidates() {
        int i = 1;
        while (this.matrix.hasNext() && (i <= 4)) {
            QuerySolution qs;
            qs = matrix.next();
            String element = String.valueOf(qs.getLiteral(q.getElement()));
            double attribute = Double.valueOf(String.valueOf(qs.getLiteral(q.getAttribute())));
            System.out.println(element);

            switch (i) {
                case 1:
                    this.candAName = element;
                    this.candAAttribute = attribute;
                case 2:
                    this.candBName = element;
                    this.candBAttribute = attribute;
                case 3:
                    this.candCName = element;
                    this.candCAttribute = attribute;
                case 4:
                    this.candDName = element;
                    this.candDAttribute = attribute;
            }
            i++;
        }
    }

    private void determineCorrectAnswer() {

        double highestValue;
        highestValue = Math.max(Double.valueOf(candAAttribute), Math.max(Double.valueOf(candBAttribute),
                Math.max(Double.valueOf(candCAttribute), Double.valueOf(candDAttribute))));

        if (highestValue == candAAttribute){
                this.correctAnswer = candAName;}
        if (highestValue == candBAttribute){
            this.correctAnswer = candBName;}
        if (highestValue == candCAttribute){
            this.correctAnswer = candCName;}
        if (highestValue == candDAttribute){
            this.correctAnswer = candDName;}
    }
}
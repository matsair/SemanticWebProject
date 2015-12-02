package com.matsschade.semanticquizapp.Objects;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.matsschade.semanticquizapp.Processing.StringProcessing;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class Question {

    private int categoryID;
    private QuestionTemplate q;
    private String questionText;
    private String candAName, candBName, candCName, candDName;
    private double candAAttribute, candBAttribute, candCAttribute, candDAttribute;
    private String correctAnswer;
    private ResultSet resultsSet;
    private ArrayList<String> elementsArray;
    private ArrayList<String> attributesArray;


    public Question(int categoryID) {

        this.categoryID = categoryID;
        executeQuery(categoryID);
        initializeCandidates();
        determineCorrectAnswer();
    }


    private void executeQuery(int categoryID) {

        //Initialize the resultsSet
        q = QuestionTemplates.getRandomQuestionTemplate(categoryID);
        questionText = q.getQuestion();
        Query query = QueryFactory.create(q.getQuery());
        String endpoint = "http://dbpedia.org/sparql";
        //String endpoint = "http://linkedmdb.org/sparql";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        try {
            this.resultsSet = qexec.execSelect();

            //additional result set to print out results
            ResultSet showSet = ResultSetFactory.copyResults(qexec.execSelect());

            //This also moves the resultsSet to the last row --> commented out to avoid errors
            ResultSetFormatter.out(System.out, showSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        elementsArray = new ArrayList<String>();
        attributesArray = new ArrayList<String>();

        //Fill the arrays
        while (this.resultsSet.hasNext()) {
            QuerySolution qs;
            qs = resultsSet.next();

            String element = StringProcessing.clean(
                    String.valueOf(qs.getLiteral(q.getElement())), "string");

            String attribute = StringProcessing.clean(
                    String.valueOf(qs.getLiteral(q.getAttribute())), q.getAttributeType());

            if (!attribute.equals("0.0")) {
                elementsArray.add(element);
                attributesArray.add(attribute);
            }
        }
    }

    private void initializeCandidates() {
        int randomInt1, randomInt2, randomInt3, randomInt4;

        //Use shuffledArray in order to generate random numbers
        ArrayList <Integer> shuffledArray = getShuffledArray(elementsArray.size());

        randomInt1 = shuffledArray.get(0);
        randomInt2 = shuffledArray.get(1);
        randomInt3 = shuffledArray.get(2);
        randomInt4 = shuffledArray.get(3);

        this.candAName = elementsArray.get(randomInt1);
        this.candAAttribute = Double.valueOf(attributesArray.get(randomInt1));

        this.candBName = elementsArray.get(randomInt2);
        this.candBAttribute = Double.valueOf(attributesArray.get(randomInt2));

        this.candCName = elementsArray.get(randomInt3);
        this.candCAttribute = Double.valueOf(attributesArray.get(randomInt3));

        this.candDName = elementsArray.get(randomInt4);
        this.candDAttribute = Double.valueOf(attributesArray.get(randomInt4));
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


    private static  ArrayList<Integer> getShuffledArray (int range) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < range; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        return list;
    }




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

    public String getQuestionText(){return questionText;}


    public double getCandAAttribute() {
        return candAAttribute;
    }

    public double getCandBAttribute() {
        return candBAttribute;
    }

    public double getCandCAttribute() {
        return candCAttribute;
    }

    public double getCandDAttribute() {
        return candDAttribute;
    }



}
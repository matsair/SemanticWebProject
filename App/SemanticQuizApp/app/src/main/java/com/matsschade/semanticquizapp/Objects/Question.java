package com.matsschade.semanticquizapp.Objects;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.matsschade.semanticquizapp.GPS.GEODistance;
import com.matsschade.semanticquizapp.GPS.GPSTracker;
import com.matsschade.semanticquizapp.Processing.JSONReader;
import com.matsschade.semanticquizapp.Processing.StringProcessing;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class Question {

    private int categoryID;
    public QuestionTemplate questionTemplate;
    private String candAName, candBName, candCName, candDName;
    private String candAAttribute, candBAttribute, candCAttribute, candDAttribute;
    private String correctAnswer;
    private ResultSet resultsSet;
    private ArrayList<String> elementsArray;
    private ArrayList<String> attributesArray;
    private GPSTracker tracker;
    private Context mycontext;

    public Question(int categoryID, Context mycontext) {

        this.categoryID = categoryID;
        this.mycontext = mycontext;
        executeQuery(categoryID);
        initializeCandidates();
        determineCorrectAnswer();
    }


    private void executeQuery(int categoryID) {

        //Initialize the resultsSet
        questionTemplate = QuestionTemplates.getRandomQuestionTemplate(categoryID);
        Query query = QueryFactory.create(questionTemplate.getQuery());
        String endpoint = questionTemplate.getURI();
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

            Log.d("initial Value",String.valueOf(qs.getLiteral(questionTemplate.getElement())));

            String element = StringProcessing.clean(
                    String.valueOf(qs.getLiteral(questionTemplate.getElement())), "string");

            String attribute = StringProcessing.clean(
                    String.valueOf(qs.getLiteral(questionTemplate.getAttribute())), questionTemplate.getAttributeType());

            // get rid of useless values and redundant elements
            if (!attribute.equals("0.0") && !elementsArray.contains(element)&& !attributesArray.contains(attribute)
                    && !element.equals("Snoop Dogg")) {
                elementsArray.add(element);

                if (questionTemplate.getAttribute().equals("rating")) {
                    element = element.replace(" ", "+");
                    String requestURL = "http://www.omdbapi.com/?t=" + element + "&y=&plot=short&r=json";
                    Log.d("Movie Request URL", requestURL);
                    JSONReader reader = new JSONReader();
                    JSONObject obj = null;
                    try {
                        obj = reader.readJsonFromUrl(requestURL);
                        double rating = obj.getDouble("imdbRating");
                        //Log.d("Rating", String.valueOf(rating));

                        if(!attributesArray.contains(attribute)){

                            attributesArray.add(String.valueOf(rating));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    attributesArray.add(attribute);
                }
            }
        }
    }

    private void initializeCandidates() {
        int randomInt1, randomInt2, randomInt3, randomInt4;

        //Use shuffledArray in order to generate random numbers
        ArrayList<Integer> shuffledArray = getShuffledArray(elementsArray.size());

        randomInt1 = shuffledArray.get(0);
        randomInt2 = shuffledArray.get(1);
        randomInt3 = shuffledArray.get(2);
        randomInt4 = shuffledArray.get(3);

        this.candAName = elementsArray.get(randomInt1);
        this.candBName = elementsArray.get(randomInt2);
        this.candCName = elementsArray.get(randomInt3);
        this.candDName = elementsArray.get(randomInt4);

        if (questionTemplate.getAttributeType().equals("location")) {

            tracker = new GPSTracker(mycontext);
            Location location = tracker.getLocation();
            double lat1 = location.getLatitude();
            double lon1= location.getLongitude();

            double lat2, lon2;
            String lat2lon2;

            lat2lon2 = attributesArray.get(randomInt1);
            lat2 = Double.valueOf(lat2lon2.split(" ")[0]);
            lon2 = Double.valueOf(lat2lon2.split(" ")[1]);
            this.candAAttribute = Double.toString(GEODistance.getDistance(lat1, lon1, lat2, lon2));

            lat2lon2 = attributesArray.get(randomInt2);
            lat2 = Double.valueOf(lat2lon2.split(" ")[0]);
            lon2 = Double.valueOf(lat2lon2.split(" ")[1]);
            this.candBAttribute = Double.toString(GEODistance.getDistance(lat1, lon1, lat2, lon2));

            lat2lon2 = attributesArray.get(randomInt3);
            lat2 = Double.valueOf(lat2lon2.split(" ")[0]);
            lon2 = Double.valueOf(lat2lon2.split(" ")[1]);
            this.candCAttribute = Double.toString(GEODistance.getDistance(lat1, lon1, lat2, lon2));

            lat2lon2 = attributesArray.get(randomInt4);
            lat2 = Double.valueOf(lat2lon2.split(" ")[0]);
            lon2 = Double.valueOf(lat2lon2.split(" ")[1]);
            this.candDAttribute = Double.toString(GEODistance.getDistance(lat1, lon1, lat2, lon2));

        } else {
                this.candAAttribute = (attributesArray.get(randomInt1));
                this.candBAttribute = (attributesArray.get(randomInt2));
                this.candCAttribute = (attributesArray.get(randomInt3));
                this.candDAttribute = (attributesArray.get(randomInt4));
            }
    }

    private void determineCorrectAnswer() {

        String correctValue;

        if (questionTemplate.getAttributeType().equals("location")) {
            correctValue = String.valueOf(Math.min(Double.valueOf(candAAttribute), Math.min(Double.valueOf(candBAttribute),
                    Math.min(Double.valueOf(candCAttribute), Double.valueOf(candDAttribute)))));

        } else if (questionTemplate.getAttributeType().equals("string")) {

            ArrayList<String> list = new ArrayList<String>();
            list.add (candAAttribute);
            list.add (candBAttribute);
            list.add (candCAttribute);
            list.add (candDAttribute);

            //choose arbitrary string of the four strings
            correctValue = list.get((int) (Math.random()*4));

        } else {
            correctValue = String.valueOf(Math.max(Double.valueOf(candAAttribute), Math.max(Double.valueOf(candBAttribute),
                    Math.max(Double.valueOf(candCAttribute), Double.valueOf(candDAttribute)))));
        }

        if (correctValue.equals(candAAttribute)){
            this.correctAnswer = candAName;}
        if (correctValue.equals(candBAttribute)){
            this.correctAnswer = candBName;}
        if (correctValue.equals(candCAttribute)){
            this.correctAnswer = candCName;}
        if (correctValue.equals(candDAttribute)){
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


    public String getCandAAttribute() {
        return candAAttribute;
    }

    public String getCandBAttribute() {
        return candBAttribute;
    }

    public String getCandCAttribute() {
        return candCAttribute;
    }

    public String getCandDAttribute() {
        return candDAttribute;
    }

}
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


    public Question(int categoryID) {

        this.categoryID = categoryID;
        executeQuery(categoryID);
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
//            ResultSetFormatter.out(System.out, showSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        elementsArray = new ArrayList<String>();
        attributesArray = new ArrayList<String>();

        //Fill the arrays
        while (this.resultsSet.hasNext()) {
            QuerySolution qs;
            qs = resultsSet.next();

//            Log.d("initial Value", String.valueOf(qs.getLiteral(questionTemplate.getElement())));

            String element = StringProcessing.clean(
                    String.valueOf(qs.getLiteral(questionTemplate.getElement())), questionTemplate.getElementType());

            String attribute = "";
            if (questionTemplate.getAttribute().equals("rating")) {
                String placeholder = element.replace(" ", "+");
                String requestURL = "http://www.omdbapi.com/?t=" + placeholder + "&y=&plot=short&r=json";
//                Log.d("Movie Request URL", requestURL);
                JSONReader reader = new JSONReader();
                JSONObject obj;
                try {
                    obj = reader.readJsonFromUrl(requestURL);
                    attribute = Double.toString(obj.getDouble("imdbRating"));
                    //Log.d("Rating", String.valueOf(rating));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (questionTemplate.getAttribute().equals("director")) {
                String placeholder = element.replace(" ", "+");
                String requestURL = "http://www.omdbapi.com/?t=" + placeholder + "&y=&plot=short&r=json";
//                Log.d("Movie Request URL", requestURL);
                JSONReader reader = new JSONReader();
                JSONObject obj;
                try {
                    obj = reader.readJsonFromUrl(requestURL);
                    String director = obj.getString("Director");
                    //Log.d("Rating", String.valueOf(rating));
                    int spaceIndex = director.indexOf(",");
                    if (spaceIndex != -1) {
                        director = director.substring(0, spaceIndex);
                    }
                    attribute = String.valueOf(director);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                attribute = StringProcessing.clean(
                        String.valueOf(qs.getLiteral(questionTemplate.getAttribute())), questionTemplate.getAttributeType());
            }

            // get rid of useless values and redundant elements
            if (!attribute.equals("0.0") && !elementsArray.contains(element) && !attributesArray.contains(attribute)) {
                elementsArray.add(element);
                attributesArray.add(attribute);
            }
        }
    }

    public void initializeCandidates(Context context) {
        int randomInt1, randomInt2, randomInt3, randomInt4;

        //Use shuffledArray in order to generate random numbers
        ArrayList<Integer> shuffledArray = getShuffledArray(elementsArray.size());

        randomInt1 = shuffledArray.get(0);
        randomInt2 = shuffledArray.get(1);
        randomInt3 = shuffledArray.get(2);
        randomInt4 = shuffledArray.get(3);

        while (attributesArray.get(randomInt1).equals("N/A") || attributesArray.get(randomInt2).equals("N/A") ||
                attributesArray.get(randomInt3).equals("N/A") || attributesArray.get(randomInt4).equals("N/A")) {

            shuffledArray = getShuffledArray(elementsArray.size());

            randomInt1 = shuffledArray.get(0);
            randomInt2 = shuffledArray.get(1);
            randomInt3 = shuffledArray.get(2);
            randomInt4 = shuffledArray.get(3);
        }

        if (questionTemplate.getElementType().equals("year")){

            this.candAName = Integer.toString(Double.valueOf(elementsArray.get(randomInt1)).intValue());
            this.candBName = Integer.toString(Double.valueOf(elementsArray.get(randomInt2)).intValue());
            this.candCName = Integer.toString(Double.valueOf(elementsArray.get(randomInt3)).intValue());
            this.candDName = Integer.toString(Double.valueOf(elementsArray.get(randomInt4)).intValue());

        }else {

            this.candAName = elementsArray.get(randomInt1);
            this.candBName = elementsArray.get(randomInt2);
            this.candCName = elementsArray.get(randomInt3);
            this.candDName = elementsArray.get(randomInt4);

        }

        if (questionTemplate.getAttributeType().equals("location")) {

            tracker = new GPSTracker(context);
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

    public void determineCorrectAnswer() {

        String correctValue;
        //boolean answerIsANumber = true;

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
            //this.correctAnswer = correctValue;

        } else {
            correctValue = String.valueOf(Math.max(Double.valueOf(candAAttribute), Math.max(Double.valueOf(candBAttribute),
                    Math.max(Double.valueOf(candCAttribute), Double.valueOf(candDAttribute)))));
        }

        //if (answerIsANumber) {
            if (correctValue.equals(candAAttribute)){
                this.correctAnswer = candAName;}
            if (correctValue.equals(candBAttribute)){
                this.correctAnswer = candBName;}
            if (correctValue.equals(candCAttribute)){
                this.correctAnswer = candCName;}
            if (correctValue.equals(candDAttribute)){
                this.correctAnswer = candDName;}
       // }
        Log.d("CorrectAnswer", "" + this.correctAnswer);
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
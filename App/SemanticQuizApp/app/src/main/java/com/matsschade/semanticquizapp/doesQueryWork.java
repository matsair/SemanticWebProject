package com.matsschade.semanticquizapp;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

/**
 * Created by rober_000 on 01.12.2015.
 */
public class doesQueryWork {

    public static void test ()

    {

        ResultSet resultsSet;
        String text = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                "\n" +
                "SELECT ?movie_name  ?date \n" +
                "\n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "FROM <http://data.linkedmdb.org/all>\n" +
                "\n" +
                "WHERE { \n" +
                "?movie movie:actor ?actor.\n" +
                "?actor movie:actor_name \"Brad Pitt\".\n" +
                "?movie rdfs:label ?movie_name.\n" +
                "?movie dc:date ?date. \n" +
                "?movie_name vrank:hasRank ?value.\n" +
                "}\n" +
                "\n" +
                "ORDER BY DESC(?value)";


        Query query = QueryFactory.create(text);
        String endpoint = "http://linkedmdb.org/sparql";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        try

        {
            resultsSet = qexec.execSelect();

            //This also moves the resultsSet to the last row --> commented out to avoid errors
            ResultSetFormatter.out(System.out, resultsSet);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

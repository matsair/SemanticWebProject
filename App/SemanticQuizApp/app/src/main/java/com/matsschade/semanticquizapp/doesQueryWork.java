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
        String text = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT DISTINCT ?name ?revenue ?intl\n" +
                "WHERE\n" +
                "{\n" +
                "  ?company rdf:type dbo:Company .\n" +
                "  ?company rdfs:label ?name .\n" +
                "  ?company dbp:revenue ?revenue.\n" +
                "  ?company dbp:numEmployees ?employees .\n" +
                "  ?company dbp:intl ?intl .\n" +
                "\n" +
                "   FILTER langMatches(lang(?name), \"DE\")\n" +
                "   FILTER (?employees>100000).\n" +
                "   FILTER regex(?intl, \"yes\", \"i\") \n" +
                "}";


        Query query = QueryFactory.create(text);
        String endpoint = "http://dbpedia.org/sparql";

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

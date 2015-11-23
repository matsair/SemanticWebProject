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
        String query = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "\n" +
                "SELECT DISTINCT ?company\n" +
                "WHERE\n" +
                "{\n" +
                "  ?city rdf:type dbo:Company .\n" +
                "  ?city rdfs:label ?company .\n" +
                "  ?city dbo:revenue ?revenue .\n" +
                "\n" +
                "  FILTER (?revenue > 1000000000).\n" +
                "  FILTER (langMatches(lang(?company), \"EN\")) .\n" +
                "}\n" +
                "LIMIT 10";
        return this.q;
    }
}
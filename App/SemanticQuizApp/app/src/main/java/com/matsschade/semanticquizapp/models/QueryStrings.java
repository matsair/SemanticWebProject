package com.matsschade.semanticquizapp.models;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QueryStrings {

    static QueryString[][] queries;

    public static void initializeQueries() {

        queries = new QueryString [3][5];

        String abc = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT DISTINCT ?city_name MAX(?sun)\n" +
                "WHERE\n" +
                "{\n" +
                "  ?city rdf:type yago:City108524735 .\n" +
                "  ?city rdfs:label ?city_name .\n" +
                "  ?city dbo:country ?country .\n" +
                "\n" +
                "  ?city dbo:populationTotal ?population_total .\n" +
                "  ?city dbp:yearSun ?sun .\n" +
                "\n" +
                "  FILTER (?population_total > 1000000) .\n" +
                "  FILTER (langMatches(lang(?city_name), \"EN\")) .\n" +
                "}\n" +
                "ORDER BY DESC(?sun)\n";

        queries[0][0] = new QueryString(abc,"city_name", "callret-2");

    }

    // Has to be randomized
    public static QueryString getRandomQueryString(int categoryID) {
        return queries[categoryID][0];
    }


}
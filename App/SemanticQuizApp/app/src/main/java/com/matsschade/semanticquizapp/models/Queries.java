package com.matsschade.semanticquizapp.models;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class Queries {

    static String[][] queries;

    public static void initializeQueries() {

        queries = new String[3][5];

        queries[0][0] = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT DISTINCT ?city_name\n" +
                "WHERE\n" +
                "{\n" +
                "  ?city dct:subject dbc:Capitals_in_Europe .\n" +
                "  ?city rdfs:label ?city_name .\n" +
                "  OPTIONAL { ?city dbo:populationTotal ?population_total . }\n" +
                "  OPTIONAL { ?city dbp:populationBlank ?population_blank . }\n" +
                "\n" +
                "  FILTER (?population_total > 2000000 || ?population_blank > 2000000) .\n" +
                "  FILTER (langMatches(lang(?city_name), \"EN\")) .\n" +
                "}\n" +
                "LIMIT 10";

    }

     /*   queries[0][0] =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        //  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
                        "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> "+
                        "SELECT ?rN ?mN WHERE { "+
                        "?a movie:actor_name \""+"Jennifer Lawrence"+"\"." +
                        "{?m movie:actor ?a; rdfs:label ?mN.} UNION { " +
                        "OPTIONAL {?a movie:performance ?p. ?p movie:film_character ?r. ?r movie:film_character_name ?rN.}} "+
                        "}";
    }*/

    public static String getQuery(int categoryID) {
        return queries[categoryID][0];
    }
}
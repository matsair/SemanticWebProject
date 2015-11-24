package com.matsschade.semanticquizapp.models;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QueryStrings {

    static QueryString[][] queries;

    public static void initializeQueries() {

        queries = new QueryString [3][5];

        String abc = "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                "PREFIX  dct:  <http://purl.org/dc/terms/>\n" +
                "PREFIX  dbc:  <http://dbpedia.org/resource/Category:>\n" +
                "\n" +
                "SELECT DISTINCT  ?city_name ?sun\n" +
                "WHERE\n" +
                "  { ?city  dct:subject  dbc:Capitals_in_Europe ;\n" +
                "           rdfs:label   ?city_name ;\n" +
                "           dbp:yearSun  ?sun\n" +
                "    OPTIONAL\n" +
                "      { ?city  dbo:populationTotal  ?population_total }\n" +
                "    OPTIONAL\n" +
                "      { ?city  dbp:populationBlank  ?population_blank }\n" +
                "    FILTER ( ( ?population_total > 2000000 ) || ( ?population_blank > 2000000 ) )\n" +
                "    FILTER langMatches(lang(?city_name), \"EN\")\n" +
                "  }\n" +
                "LIMIT   10";

        queries[0][0] = new QueryString(abc,"city_name", "callret-2");

    }

    // Has to be randomized
    public static QueryString getRandomQueryString(int categoryID) {
        return queries[categoryID][0];
    }


}
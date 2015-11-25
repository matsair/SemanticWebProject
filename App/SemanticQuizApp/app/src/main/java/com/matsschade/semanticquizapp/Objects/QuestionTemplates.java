package com.matsschade.semanticquizapp.Objects;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QuestionTemplates {

    static QuestionTemplate[][] queries;

    public static void initializeQueries() {

        queries = new QuestionTemplate[3][2];

        String queryCity1 = "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
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

        String questionCity1 = "Which of the following is the sunniest city?";

        String queryCompany1 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?employees\n" +
                "WHERE\n" +
                "  { ?company rdf:type dbo:Company .\n" +
                "    ?company rdfs:label ?name .\n" +
                "    ?company dbo:numberOfEmployees ?employees\n" +
                "    FILTER ( ?employees > 100000 )\n" +
                "    FILTER langMatches(lang(?name), \"DE\")\n" +
                "  }\n" +
                "LIMIT   100";

        String questionCompany1 = "Which company has the highest revenue?";

        queries[0][0] = new QuestionTemplate(queryCity1,"city_name", "sun", questionCity1);
        queries[0][1] = new QuestionTemplate(queryCompany1, "name", "revenue", questionCompany1);
    }

    // Has to be randomized
    public static QuestionTemplate getRandomQuestionTemplate(int categoryID) {
        int arraySize = queries[categoryID].length;
        int randomNumber = (int) (Math.random()*arraySize);
        return queries[categoryID][randomNumber];
    }
}
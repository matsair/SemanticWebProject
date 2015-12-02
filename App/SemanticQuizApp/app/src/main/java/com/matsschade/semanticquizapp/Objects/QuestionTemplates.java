package com.matsschade.semanticquizapp.Objects;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QuestionTemplates {

    static QuestionTemplate[][] queries;

    public static void initializeQueries() {

        queries = new QuestionTemplate[3][7];

        String queryCity1 = "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbp:  <http://dbpedia.org/property/>\n" +
                "PREFIX  dct:  <http://purl.org/dc/terms/>\n" +
                "PREFIX  dbc:  <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT DISTINCT  ?city_name ?sun\n" +
                "WHERE {\n" +
                "  ?city  dct:subject  dbc:Capitals_in_Europe ;\n" +
                "                      rdfs:label  ?city_name ;\n" +
                "                      dbp:yearSun  ?sun\n" +
                "  FILTER langMatches(lang(?city_name), \"EN\")\n" +
                "}";

        String questionCity1 = "Which of the following is the sunniest city?";

        String queryCity2 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX yago: <http://yago-knowledge.org/resource/>\n" +
                "PREFIX dct:  <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc:  <http://dbpedia.org/resource/Category:>\n" +
                "\n" +
                "SELECT DISTINCT ?city_name ?precip\n" +
                "WHERE\n" +
                "{\n" +
                "  ?city dct:subject  dbc:Capitals_in_Europe .\n" +
                "  ?city rdfs:label ?city_name .\n" +
                "  ?city dbo:country ?country .\n" +
                "\n" +
                "  ?city dbo:populationTotal ?population_total .\n" +
                "  ?city dbp:yearPrecipitationMm ?precip .\n" +
                "\n" +
                "  FILTER (langMatches(lang(?city_name), \"EN\")) .\n" +
                "}\n" +
                "ORDER BY DESC(?precip)\n";

        String questionCity2 = "Which city experiences the most annual rainfall?";

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

        String questionCompany1 = "Which company has the most employees?";

        String queryCompany2 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
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

        String questionCompany2 = "Which company has the highest revenue?";

        String queryCountry1 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT DISTINCT ?country_name ?area\n" +
                "WHERE\n" +
                "{\n" +
                "  ?country rdf:type dbo:Country .\n" +
                "  ?country rdfs:label ?country_name .\n" +
                "  ?country dbp:areaKm ?area .\n" +
                "  ?country dbp:gini ?gini .\n" +
                "\n" +
                "  FILTER (langMatches(lang(?country_name), \"EN\")) .\n" +
                "}\n";

        String questionCountry1 = "Which is the largest country by area?";

        String queryCountry2 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT DISTINCT ?country_name ?gini\n" +
                "WHERE\n" +
                "{\n" +
                "  ?country rdf:type dbo:Country .\n" +
                "  ?country rdfs:label ?country_name .\n" +
                "  ?country dbp:gini ?gini .\n" +
                "\n" +
                "  FILTER (langMatches(lang(?country_name), \"EN\")) .\n" +
                "}\n";

        String questionCountry2 = "Which country is the most corrupt?";

        String queryCountry3 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "SELECT DISTINCT ?country_name ?hdi\n" +
                "WHERE\n" +
                "{\n" +
                "  ?country rdf:type dbo:Country .\n" +
                "  ?country rdfs:label ?country_name .\n" +
                "  ?country dbp:hdi ?hdi .\n" +
                "  ?country dbo:populationTotal ?population_total .\n" +
                "\n" +
                "  FILTER (langMatches(lang(?country_name), \"EN\")) .\n" +
                "}\n";

        String questionCountry3 = "Which country has the highest standard of living?";

        queries[0][0] = new QuestionTemplate(queryCity1,"city_name", "sun", questionCity1);
        queries[1][1] = new QuestionTemplate(queryCompany1, "name", "employees", questionCompany1);
        queries[1][2] = new QuestionTemplate(queryCompany2, "name", "revenue", questionCompany2);

        queries[2][3] = new QuestionTemplate(queryCountry1, "country_name", "area", questionCountry1);
        queries[2][4] = new QuestionTemplate(queryCountry2, "country_name", "gini", questionCountry2);
        queries[2][5] = new QuestionTemplate(queryCountry3, "country_name", "hdi", questionCountry3);
        queries[0][6] = new QuestionTemplate(queryCity2, "city_name", "precip", questionCity2);

    }

    // Has to be randomized
    public static QuestionTemplate getRandomQuestionTemplate(int categoryID) {
        int arraySize = queries[categoryID].length;
        int randomNumber = (int) (Math.random()*arraySize);
        return queries[categoryID][0];
    }
}
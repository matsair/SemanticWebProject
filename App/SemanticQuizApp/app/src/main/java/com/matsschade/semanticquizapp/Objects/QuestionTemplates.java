package com.matsschade.semanticquizapp.Objects;

/**
 * Created by rober_000 on 23.11.2015.
 */
public class QuestionTemplates {

    static QuestionTemplate[][] queries;

    public static void initializeQueries() {

        queries = new QuestionTemplate[4][4];

        String queryCity1 = "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX  dbp:  <http://dbpedia.org/property/> \n" +
                "PREFIX  dct:  <http://purl.org/dc/terms/> \n" +
                "PREFIX  dbc:  <http://dbpedia.org/resource/Category:> \n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX yago: <http://dbpedia.org/class/yago/>\n" +
                "PREFIX vrank: <http://purl.org/voc/vrank#>\n" +
                " \n" +
                "SELECT DISTINCT  ?city_name ?sun\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "\n" +
                "WHERE { \n" +
                "?city rdf:type yago:Capital108518505 .\n" +
                "?city rdfs:label   ?city_name. \n" +
                "?city dbp:yearSun  ?sun.\n" +
                "?city vrank:hasRank ?value.\n" +
                "\n" +
                "FILTER langMatches(lang(?city_name), \"EN\") \n" +
                "} \n" +
                "ORDER BY DESC (?value)\n" +
                "LIMIT 50";

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

        String queryCity3 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX yago: <http://dbpedia.org/class/yago/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?population\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE{ \n" +
                "    ?city rdf:type yago:Capital108518505 .\n" +
                "    ?city rdfs:label ?name .\n" +
                "    ?city dbo:populationTotal ?population.\n" +
                "\t?city vrank:hasRank ?value.\n" +
                "\n" +
                "    FILTER langMatches(lang(?name), \"EN\")\n" +
                "\t}\n" +
                "\t\n" +
                "\tORDER BY DESC (?value)\n" +
                "   LIMIT 60\n";

        String questionCity3 = "Which city has the highest population?";

        String queryCity4 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX yago: <http://dbpedia.org/class/yago/>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?geo\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "  { ?city rdf:type yago:Capital108518505 .\n" +
                "    ?city rdfs:label ?name .\n" +
                "    ?city dbo:populationTotal ?population.\n" +
                "    ?city georss:point ?geo.\n" +
                "    \n" +
                "    ?city vrank:hasRank ?value.\n" +
                "\n" +
                "    FILTER langMatches(lang(?name), \"EN\")\n" +
                "    FILTER (?population > 2000000)\n" +
                "  }\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 40";

        String questionCity4 = "Which city is closest to your current location?";

        String queryCompany1 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?employees\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "  { ?company rdf:type dbo:Company .\n" +
                "    ?company rdfs:label ?name .\n" +
                "    ?company dbo:numberOfEmployees ?employees.\n" +
                "    ?company dbp:intl ?intl.\n" +
                "\n" +
                "?company vrank:hasRank ?value.\n" +
                "\n" +
                "    FILTER ( ?employees > 100000 )\n" +
                "    FILTER langMatches(lang(?name), \"DE\")\n" +
                "    FILTER regex(?intl, \"yes\", \"i\")\n" +
                "  }\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 50";

        String questionCompany1 = "Which company has the most employees?";

        String queryCompany2 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT ?name ?revenue ?intl ?employees\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "{\n" +
                "  ?company rdf:type dbo:Company .\n" +
                "  ?company rdfs:label ?name .\n" +
                "  ?company dbp:revenue ?revenue.\n" +
                "  ?company dbp:numEmployees ?employees .\n" +
                "  ?company dbp:intl ?intl .\n" +
                "?company vrank:hasRank ?value.\n" +
                "\n" +
                "   FILTER langMatches(lang(?name), \"EN\")\n" +
                "   FILTER (?employees>100000 && datatype(?employees) = xsd:integer).\n" +
                "   FILTER regex(?intl, \"yes\", \"i\") \n" +
                "}\n" +
                "\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 40";

        String questionCompany2 = "Which company has the highest revenue?";

        String queryCompany3 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?year\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "  { ?company rdf:type dbo:Company .\n" +
                "    ?company rdfs:label ?name .\n" +
                "    ?company dbo:numberOfEmployees ?employees.\n" +
                "    ?company dbp:intl ?intl.\n" +
                "\n" +
                "?company dbo:foundingYear ?year.\n" +
                "?company vrank:hasRank ?value.\n" +
                "\n" +
                "    FILTER ( ?employees > 100000 )\n" +
                "    FILTER langMatches(lang(?name), \"DE\")\n" +
                "    FILTER regex(?intl, \"yes\", \"i\")\n" +
                "  }\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 50";

        String questionCompany3 = "Which company has been founded most recently?";

        String queryCompany4 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?geo\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "  { ?company rdf:type dbo:Company .\n" +
                "    ?company rdfs:label ?name .\n" +
                "    ?company dbo:numberOfEmployees ?employees.\n" +
                "    ?company dbp:intl ?intl.\n" +
                "\n" +
                "    ?company dbo:location ?location.\n" +
                "    ?location georss:point ?geo.\n" +
                "?company vrank:hasRank ?value.\n" +
                "\n" +
                "    FILTER ( ?employees > 100000 )\n" +
                "    FILTER langMatches(lang(?name), \"DE\")\n" +
                "    FILTER regex(?intl, \"yes\", \"i\")\n" +
                "  }\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 50";

        String questionCompany4 = "Which company's headquarters is closest to your current location?";

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

        String questionCountry2 = "Which country has the most unequal distribution of income?";

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

            String queryCountry4 = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "PREFIX dbc: <http://dbpedia.org/resource/Category:>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "\n" +
                "SELECT DISTINCT ?country_name ?geo\t\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "{\n" +
                "  ?country rdf:type dbo:Country.\n" +
                "  ?country rdfs:label ?country_name.\n" +
                "  ?country dbo:capital ?capital. \n" +
                "  ?capital georss:point ?geo.\n" +
                "  ?country dbp:gini ?gini .\n" +
                "\n" +
                "  ?country vrank:hasRank ?value.\n" +
                "\n" +
                "  FILTER (langMatches(lang(?country_name), \"EN\")) .\n" +
                "}\n" +
                "\n" +
                "ORDER BY DESC(?value)\n" +
                "LIMIT 100";

        String questionCountry4 = "Considering the capital, which country is closest to your current location?";

        String queryMovie1 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " \n" +
                "                PREFIX  dbo:  <http://dbpedia.org/ontology/> \n" +
                "                PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX vrank:<http://purl.org/voc/vrank#> \n" +
                "                 \n" +
                "                SELECT DISTINCT  ?name ?rating\n" +
                "                 \n" +
                "                FROM <http://dbpedia.org>  \n" +
                "                FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank>  \n" +
                "                 \n" +
                "                WHERE \n" +
                "                  { ?film rdf:type dbo:Film . \n" +
                "                    ?film rdfs:label ?name . \n" +
                "                    ?film dbp:country \"United States\"@en. \n" +
                "                    ?film vrank:hasRank ?value. \n" +
                "                    ?film rdfs:label ?rating.                  \n" +
                "                    ?film dbp:gross ?budget. \n" +
                "                 \n" +
                "                FILTER langMatches(lang(?name), \"EN\")\n" +
                "                FILTER langMatches(lang(?rating), \"EN\")  \n" +
                "                FILTER (?budget>1000000) \n" +
                "                 \n" +
                "                  } \n" +
                "                ORDER BY DESC (?value)  \n" +
                "                LIMIT 50";

        String questionMovie1 = "Which of these movies has the highest IMDB rating?";


        String queryMovie2 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  dbo:  <http://dbpedia.org/ontology/>\n" +
                "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX dbp: <http://dbpedia.org/property/>\n" +
                "PREFIX vrank:<http://purl.org/voc/vrank#>\n" +
                "PREFIX yago: <http://dbpedia.org/class/yago/>\n" +
                "PREFIX dct: <http://purl.org/dc/terms/>\n" +
                "\n" +
                "SELECT DISTINCT  ?name ?date\n" +
                "\n" +
                "FROM <http://dbpedia.org> \n" +
                "FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n" +
                "\n" +
                "WHERE\n" +
                "  { ?actor rdf:type yago:AmericanFilmActors.\n" +
                "    ?actor dct:subject ?filter.\n" +
                "    ?actor rdfs:label ?name.\n" +
                "    ?actor vrank:hasRank ?value.\n" +
                "    ?actor dbp:birthDate ?date\n" +
                "\n" +
                "    FILTER langMatches(lang(?name), \"EN\")\n" +
                "    FILTER regex(?filter, \"st-century_American_\", \"i\")\n" +
                "    FILTER regex(?filter, \"act\", \"i\")\n" +
                "    FILTER (?name != \"Snoop Dogg\"@en)\n" +
                "  }\n" +
                "\n" +
                "ORDER BY DESC (?value) \n" +
                "LIMIT 50";

        String questionMovie2 = "Which of the following actors is the youngest?";

        String queryMovie3= "PREFIX dc: <http://purl.org/dc/terms/>\n" +
                "PREFIX movie: <http://data.linkedmdb.org/resource/movie/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "\n" +
                "\n" +
                "SELECT ?name  ?date\n" +
                "\n" +
                "WHERE{\n" +
                "\n" +
                "?film dc:date ?date.\n" +
                "?a movie:film_of_distributor ?film.\n" +
                "?a movie:film_film_distributor_relationship_distributor \"20th Century Fox\".\n" +
                "?film rdfs:label ?name.\n" +
                "?film movie:initial_release_date ?date\n" +
                "}\n" +
                "ORDER BY DESC (?date)\n" +
                "LIMIT 300";

        String questionMovie3 = "When did {movie} first play in theaters?";

        String queryMovie4 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                " \n" +
                "                PREFIX  dbo:  <http://dbpedia.org/ontology/> \n" +
                "                PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "                PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "                PREFIX vrank:<http://purl.org/voc/vrank#> \n" +
                "                 \n" +
                "                SELECT DISTINCT  ?name ?director\n" +
                "                 \n" +
                "                FROM <http://dbpedia.org>  \n" +
                "                FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank>  \n" +
                "                 \n" +
                "                WHERE \n" +
                "                  { ?film rdf:type dbo:Film . \n" +
                "                    ?film rdfs:label ?name . \n" +
                "                    ?film dbp:country \"United States\"@en. \n" +
                "                    ?film vrank:hasRank ?value. \n" +
                "                    ?film rdfs:label ?director.                  \n" +
                "                    ?film dbp:gross ?budget. \n" +
                "                 \n" +
                "                FILTER langMatches(lang(?name), \"EN\")\n" +
                "                FILTER langMatches(lang(?director), \"EN\")  \n" +
                "                FILTER (?budget>1000000) \n" +
                "                 \n" +
                "                  } \n" +
                "                ORDER BY DESC (?value)  \n" +
                "                LIMIT 50";

        String questionMovie4 = " was the director of which movie?";

        queries[0][0] = new QuestionTemplate(queryCity1,"city_name", "sun", questionCity1,"string", "number", "hours", "http://dbpedia.org/sparql");
        queries[0][1] = new QuestionTemplate(queryCity2, "city_name", "precip", questionCity2,"string","number", "mm", "http://dbpedia.org/sparql");
        queries[0][2] = new QuestionTemplate(queryCity3, "name", "population", questionCity3,"string","number", "inhabitants", "http://dbpedia.org/sparql");
        queries[0][3] = new QuestionTemplate(queryCity4, "name", "geo", questionCity4,"string","location", "km", "http://dbpedia.org/sparql");
        queries[1][0] = new QuestionTemplate(queryCompany1, "name", "employees", questionCompany1,"string","number", "employees", "http://dbpedia.org/sparql");
        queries[1][1] = new QuestionTemplate(queryCompany2, "name", "revenue", questionCompany2,"string","currency", "US Dollars", "http://dbpedia.org/sparql");
        queries[1][2] = new QuestionTemplate(queryCompany3, "name", "year", questionCompany3,"string","year", "", "http://dbpedia.org/sparql");
        queries[1][3] = new QuestionTemplate(queryCompany4, "name", "geo", questionCompany4,"string","location", "km", "http://dbpedia.org/sparql");
        queries[2][0] = new QuestionTemplate(queryCountry1, "country_name", "area", questionCountry1,"string","number", "km2", "http://dbpedia.org/sparql");
        queries[2][1] = new QuestionTemplate(queryCountry2, "country_name", "gini", questionCountry2,"string","number", "Gini Index", "http://dbpedia.org/sparql");
        queries[2][2] = new QuestionTemplate(queryCountry3, "country_name", "hdi", questionCountry3,"string","number", "HDI Index", "http://dbpedia.org/sparql");
        queries[2][3] = new QuestionTemplate(queryCountry4, "country_name", "geo", questionCountry4,"string","location", "km", "http://dbpedia.org/sparql");
        queries[3][0] = new QuestionTemplate(queryMovie1, "name", "rating", questionMovie1,"string","", "","http://dbpedia.org/sparql");
        queries[3][1] = new QuestionTemplate(queryMovie2, "name", "date", questionMovie2,"string","year", "", "http://dbpedia.org/sparql");
        queries[3][2] = new QuestionTemplate(queryMovie3, "date", "name", questionMovie3,"year","string", "", "http://data.linkedmdb.org/sparql");
        queries[3][3] = new QuestionTemplate(queryMovie4, "name", "director", questionMovie4,"string","string", "", "http://dbpedia.org/sparql");
    }

    // Has to be randomized
    public static QuestionTemplate getRandomQuestionTemplate(int categoryID) {
        int arraySize = queries[categoryID].length;
        int randomNumber = (int) (Math.random()*arraySize);
        return queries[categoryID][randomNumber];
    }
}

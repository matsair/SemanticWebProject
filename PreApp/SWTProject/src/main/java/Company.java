import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;

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
        ParameterizedSparqlString strQuery = new ParameterizedSparqlString();

        strQuery.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        strQuery.setNsPrefix("dbo", "http://dbpedia.org/ontology/");
        strQuery.setNsPrefix("dct", "http://purl.org/dc/terms/");
        strQuery.setNsPrefix("dbc", "http://dbpedia.org/resource/Category:");
        strQuery.setNsPrefix("dbp", "http://dbpedia.org/property/");
        strQuery.append("SELECT DISTINCT ?city_name");
        strQuery.append("{");
        strQuery.append("?city dct:subject dbc:Capitals_in_Europe .");
        strQuery.append("?city rdfs:label ?city_name .");
        strQuery.append("OPTIONAL { ?city dbo:populationTotal ?population_total . }");
        strQuery.append("OPTIONAL { ?city dbp:populationBlank ?population_blank . }");
        strQuery.append("FILTER (?population_total > 2000000 || ?population_blank > 2000000) .");
        strQuery.append("FILTER (langMatches(lang(?city_name), \"EN\")) .");
        strQuery.append("}");
        this.q = strQuery.asQuery();
        return this.q;
    }

}

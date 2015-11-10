import org.apache.jena.query.*;

/**
 * Created by Mats on 10/11/15.
 */
public class Main {

    public static void main(String[] args) {

        CityPopulation cp = new CityPopulation(4);

        String endpoint = "http://dbpedia.org/sparql";
        QueryExecution qexec =
                QueryExecutionFactory.sparqlService(endpoint, cp.getQuery());
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                System.out.println(ResultSetFormatter.asText(results));
            }
        }
        finally {
            qexec.close();
        }
    }
}

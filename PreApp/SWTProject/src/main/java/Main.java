import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import java.io.IOException;

/**
 * Created by Mats on 10/11/15.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        CityPopulation cp = new CityPopulation(4);
        Artist artist = new Artist("Beatles");

        System.out.println(artist.getFamiliarity());

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

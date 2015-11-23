package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.matsschade.semanticquizapp.intro.Intro;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    BootstrapButton answerOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            Intent intent = new Intent(this, Intro.class);
            startActivity(intent);
        }

        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText(R.string.question);

        answerOne = (BootstrapButton) findViewById(R.id.answer_button_one);

        String q = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
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

        Query query = QueryFactory.create(q);
        String endpoint = "http://dbpedia.org/sparql";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results;
        try {
            results  = qexec.execSelect();
            for (; results.hasNext(); ) {
                Log.d("SPARQL Results ", results.toString());
            }
        }
        catch (Exception e){
            Log.e("DBPEDIADetail", "Failed DBPEDIA DOWN "+ e.toString());
        }


        String requestURL = "http://developer.echonest.com/api/v4/artist/familiarity?api_key=MN9EYDKKLH6QBGHBH&name=Rihanna&format=json";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, requestURL, createMyReqSuccessListener(), createMyReqErrorListener());

        Volley.newRequestQueue(this).add(jsonObjReq);

    }

    private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Double familiarity = response.getJSONObject("response").getJSONObject("artist").getDouble("familiarity");
                    answerOne.setText(familiarity.toString());
                } catch (JSONException e) {
                    answerOne.setText("Parse error");
                }
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                answerOne.setText(error.getMessage());
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

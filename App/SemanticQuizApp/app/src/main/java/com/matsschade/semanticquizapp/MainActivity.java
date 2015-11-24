package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.matsschade.semanticquizapp.intro.Intro;
import com.matsschade.semanticquizapp.Objects.QueryStrings;
import com.matsschade.semanticquizapp.Objects.Question;

public class MainActivity extends AppCompatActivity {

    BootstrapButton buttonOne, buttonTwo, buttonThree, buttonFour;

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

        QueryStrings.initializeQueries();

        buttonOne = (BootstrapButton) findViewById(R.id.answer_button_one);
        buttonTwo = (BootstrapButton) findViewById(R.id.answer_button_two);
        buttonThree = (BootstrapButton) findViewById(R.id.answer_button_three);
        buttonFour = (BootstrapButton) findViewById(R.id.answer_button_four);

        generateNewQuestion();

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonOne.getText())) {
                    Toast.makeText(getApplicationContext(), "Well Done!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bullshit!",
                            Toast.LENGTH_SHORT).show();
                }
                generateNewQuestion();
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonTwo.getText())) {
                    Toast.makeText(getApplicationContext(), "Well Done!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bullshit!",
                            Toast.LENGTH_SHORT).show();
                }
                generateNewQuestion();
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonThree.getText())) {
                    Toast.makeText(getApplicationContext(), "Well Done!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bullshit!",
                            Toast.LENGTH_SHORT).show();
                }
                generateNewQuestion();
            }
        });

        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonFour.getText())) {
                    Toast.makeText(getApplicationContext(), "Well Done!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bullshit!",
                            Toast.LENGTH_SHORT).show();
                }
                generateNewQuestion();
            }
        });

    }

    private void generateNewQuestion(){

        question = new Question(0);

        buttonOne.setText(question.getCandAName());
        buttonTwo.setText(question.getCandBName());
        buttonThree.setText(question.getCandCName());
        buttonFour.setText(question.getCandDName());

    }


       /*
        String requestURL = "http://developer.echonest.com/api/v4/artist/familiarity?api_key=MN9EYDKKLH6QBGHBH&name=Rihanna&format=json";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, requestURL, createMyReqSuccessListener(), createMyReqErrorListener());

        Volley.newRequestQueue(this).add(jsonObjReq);

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
    */
}
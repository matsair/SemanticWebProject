package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.matsschade.semanticquizapp.Objects.Question;
import com.matsschade.semanticquizapp.Objects.QuestionTemplates;

public class QuestionActivity extends AppCompatActivity {

    BootstrapButton buttonOne, buttonTwo, buttonThree, buttonFour;
    Button back;
    TextView questionText;
    int categoryID;

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryID = extras.getInt("category");
        }

        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionText = (TextView) findViewById(R.id.question);
        buttonOne = (BootstrapButton) findViewById(R.id.answer_button_one);
        buttonTwo = (BootstrapButton) findViewById(R.id.answer_button_two);
        buttonThree = (BootstrapButton) findViewById(R.id.answer_button_three);
        buttonFour = (BootstrapButton) findViewById(R.id.answer_button_four);

        back = (Button) findViewById(R.id.button_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        QuestionTemplates.initializeQueries();
        generateNewQuestion();

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonOne.getText())) {
                    incrementCorrect();
                    buttonOne.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);
                } else {
                    incrementWrong();
                    buttonOne.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        generateNewQuestion();
                        resetButtonColors();
                    }
                }, 2000);
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonTwo.getText())) {
                    incrementCorrect();
                    buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);
                } else {
                    incrementWrong();
                    buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        generateNewQuestion();
                        resetButtonColors();
                    }
                }, 2000);
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonThree.getText())) {
                    incrementCorrect();
                    buttonThree.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);
                } else {
                    incrementWrong();
                    buttonThree.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        generateNewQuestion();
                        resetButtonColors();
                    }
                }, 2000);
            }
        });

        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(buttonFour.getText())) {
                    incrementCorrect();
                    buttonFour.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    buttonFour.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        generateNewQuestion();
                        resetButtonColors();
                    }
                }, 2000);
            }
        });

    }

    private void generateNewQuestion(){

        question = new Question(categoryID, this);

        questionText.setText(question.getQuestionText());
        buttonOne.setText(question.getCandAName());
        buttonTwo.setText(question.getCandBName());
        buttonThree.setText(question.getCandCName());
        buttonFour.setText(question.getCandDName());
    }

    private void setRightAnswer() {
        if (question.getCorrectAnswer().equals(buttonFour.getText())) {
            buttonFour.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else if (question.getCorrectAnswer().equals(buttonThree.getText())) {
            buttonThree.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else if (question.getCorrectAnswer().equals(buttonTwo.getText())) {
            buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else {
            buttonOne.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
    }

    private void resetButtonColors() {
        buttonOne.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonThree.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonFour.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        makeButtonsClickable(true);
    }

    private void makeButtonsClickable(boolean bool) {
        buttonOne.setClickable(bool);
        buttonTwo.setClickable(bool);
        buttonThree.setClickable(bool);
        buttonFour.setClickable(bool);
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

    private void incrementCorrect() {
        SharedPreferences prefs = getSharedPreferences("score", 0);
        int correct = prefs.getInt("correct", 0);
        correct++;
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("correct", correct);
        edit.commit();
    }

    private void incrementWrong() {
        SharedPreferences prefs = getSharedPreferences("score", 0);
        int wrong = prefs.getInt("wrong", 0);
        wrong++;
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("wrong", wrong);
        edit.commit();
    }

    private void goBack(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
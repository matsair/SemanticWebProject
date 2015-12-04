package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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

    String snackBarText;

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

//        back = (Button) findViewById(R.id.button_back);

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goBack();
//            }
//        });

        QuestionTemplates.initializeQueries();
        generateNewQuestion();

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(question.getCandAName())) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonOne.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);
                } else {
                    incrementWrong();
                    snackBarText = "Schlecht!";
                    buttonOne.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                finishQuestion();
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(question.getCandBName())) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Schlecht!";
                    buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                finishQuestion();
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(question.getCandCName())) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonThree.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Schlecht!";
                    buttonThree.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();

                }
                finishQuestion();
            }
        });

        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getCorrectAnswer().equals(question.getCandDName())) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonFour.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Schlecht!";
                    buttonFour.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    makeButtonsClickable(false);
                    setRightAnswer();
                }
                finishQuestion();
            }
        });

    }

    private void generateNewQuestion(){

        question = new Question(categoryID, this);

        questionText.setText(question.q.getQuestion());
        buttonOne.setText(question.getCandAName());
        buttonTwo.setText(question.getCandBName());
        buttonThree.setText(question.getCandCName());
        buttonFour.setText(question.getCandDName());
    }

    private void setRightAnswer() {
        if (question.getCorrectAnswer().equals(question.getCandDName())) {
            buttonFour.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else if (question.getCorrectAnswer().equals(question.getCandCName())) {
            buttonThree.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else if (question.getCorrectAnswer().equals(question.getCandBName())) {
            buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
        else if (question.getCorrectAnswer().equals(question.getCandAName())){
            buttonOne.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }
    }

    private void resetButtonColors() {
        buttonOne.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonThree.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonFour.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        makeButtonsClickable(true);
        changeButtonTextSize(18);
    }

    private void finishQuestion() {
        makeButtonsClickable(false);
        changeButtonTextSize(14);
        if (question.q.getAttributeUnit().equals("km2")) {
            buttonOne.append(Html.fromHtml("<br>" + (int) question.getCandAAttribute() + " " + "Xkm<sup>2</sup>"));
            buttonTwo.append(Html.fromHtml("<br>" + (int) question.getCandBAttribute() + " " + "km<sup>2</sup>"));
            buttonThree.append(Html.fromHtml("<br>" + (int) question.getCandCAttribute() + " " + "km<sup>2</sup>"));
            buttonFour.append(Html.fromHtml("<br>" + (int) question.getCandDAttribute() + " " + "km<sup>2</sup>"));
        } else {
            buttonOne.append("\n" + (int) question.getCandAAttribute() + " " + question.q.getAttributeUnit());
            buttonTwo.append("\n" + (int) question.getCandBAttribute() + " " + question.q.getAttributeUnit());
            buttonThree.append("\n" + (int) question.getCandCAttribute() + " " + question.q.getAttributeUnit());
            buttonFour.append("\n" + (int) question.getCandDAttribute() + " " + question.q.getAttributeUnit());
        }
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackBarText, Snackbar.LENGTH_INDEFINITE)
                .setAction("Next Question", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generateNewQuestion();
                        resetButtonColors();
                    }
                })
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void makeButtonsClickable(boolean bool) {
        buttonOne.setClickable(bool);
        buttonTwo.setClickable(bool);
        buttonThree.setClickable(bool);
        buttonFour.setClickable(bool);
    }

    private void changeButtonTextSize(int size) {
        buttonOne.setTextSize(size);
        buttonTwo.setTextSize(size);
        buttonThree.setTextSize(size);
        buttonFour.setTextSize(size);
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
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            goBack();
        }

        return super.onOptionsItemSelected(item);
    }

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
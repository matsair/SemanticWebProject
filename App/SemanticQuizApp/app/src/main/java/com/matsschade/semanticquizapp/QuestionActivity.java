package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.matsschade.semanticquizapp.Email.GMailSender;
import com.matsschade.semanticquizapp.Objects.Question;
import com.matsschade.semanticquizapp.Objects.QuestionTemplates;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class QuestionActivity extends AppCompatActivity {

    BootstrapButton buttonOne, buttonTwo, buttonThree, buttonFour;
    Button back;
    TextView questionText;
    int categoryID;

    String snackBarText;

    private Question question;

    private boolean testForDirector;

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

        if (question.questionTemplate.getAttribute().equals("director")) {
            testForDirector = true;
        }
        else {
            testForDirector = false;
        }

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candA;
                if (testForDirector) {
                    candA = question.getCandAAttribute();
                    Log.d("TestForDirector TRUE", candA);
                }
                else {
                    candA = question.getCandAName();
                    Log.d("TestForDirector FALSE", candA);
                }
                if (question.getCorrectAnswer().equals(candA)) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonOne.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);
                } else {
                    incrementWrong();
                    snackBarText = "Too bad...";
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
                String candB;
                if (testForDirector) {
                    candB = question.getCandBAttribute();
                }
                else {
                    candB = question.getCandBName();
                }
                if (question.getCorrectAnswer().equals(candB)) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Too bad...";
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
                String candC;
                if (testForDirector) {
                    candC = question.getCandCAttribute();
                }
                else {
                    candC = question.getCandCName();
                }
                if (question.getCorrectAnswer().equals(candC)) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonThree.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Too bad...";
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
                String candD;
                if (testForDirector) {
                    candD = question.getCandDAttribute();
                }
                else {
                    candD = question.getCandDName();
                }
                if (question.getCorrectAnswer().equals(candD)) {
                    incrementCorrect();
                    snackBarText = "Yeah!";
                    buttonFour.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    makeButtonsClickable(false);

                } else {
                    incrementWrong();
                    snackBarText = "Too bad...";
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

        if (question.questionTemplate.getAttribute().equals("director")) {

            String questionPlaceholder;

            if (question.getCorrectAnswer().equals(question.getCandAName())){
                questionPlaceholder = question.getCandAAttribute();
            } else if (question.getCorrectAnswer().equals(question.getCandBName())) {
                questionPlaceholder = question.getCandBAttribute();
            }else if (question.getCorrectAnswer().equals(question.getCandCName())) {
                questionPlaceholder = question.getCandCAttribute();
            }else {
                questionPlaceholder = question.getCandDAttribute();
            }

            questionText.setText(questionPlaceholder + question.questionTemplate.getQuestion());
        }
        else if(question.questionTemplate.getAttribute().equals("name")) {

            String questionPlaceholder;

            if (question.getCorrectAnswer().equals(question.getCandAName())){
                questionPlaceholder = question.getCandAAttribute();
            } else if (question.getCorrectAnswer().equals(question.getCandBName())) {
                questionPlaceholder = question.getCandBAttribute();
            }else if (question.getCorrectAnswer().equals(question.getCandCName())) {
                questionPlaceholder = question.getCandCAttribute();
            }else {
                questionPlaceholder = question.getCandDAttribute();
            }

            questionText.setText("When did \"" + questionPlaceholder + "\" first play in theaters?");

        }else {
            questionText.setText(question.questionTemplate.getQuestion());
        }
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

        NumberFormat formatter = DecimalFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(0);

        makeButtonsClickable(false);
        changeButtonTextSize(14);
        if (question.questionTemplate.getAttributeUnit().equals("km2")) {
            buttonOne.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandAAttribute())) + " " + "km<sup>2</sup>"));
            buttonTwo.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandBAttribute())) + " " + "km<sup>2</sup>"));
            buttonThree.append(Html.fromHtml("<br>" +formatter.format(Double.valueOf(question.getCandCAttribute())) + " " + "km<sup>2</sup>"));
            buttonFour.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandDAttribute())) + " " + "km<sup>2</sup>"));

        } else if (question.questionTemplate.getAttributeType().equals("string")) {
            buttonOne.append("\n" + question.getCandAAttribute());
            buttonTwo.append("\n" + question.getCandBAttribute());
            buttonThree.append("\n" + question.getCandCAttribute());
            buttonFour.append("\n" + question.getCandDAttribute());

    } else if (question.questionTemplate.getAttributeType().equals("year")) {
        buttonOne.append("\n" + Double.valueOf(question.getCandAAttribute()).intValue());
        buttonTwo.append("\n" + Double.valueOf(question.getCandBAttribute()).intValue());
        buttonThree.append("\n" + Double.valueOf(question.getCandCAttribute()).intValue());
        buttonFour.append("\n" + Double.valueOf(question.getCandDAttribute()).intValue());

    }
        else if (question.questionTemplate.getAttribute().equals("director")) {
            // Do nothing
        }
        else {
            buttonOne.append("\n" + formatter.format(Double.valueOf(question.getCandAAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonTwo.append("\n" + formatter.format(Double.valueOf(question.getCandBAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonThree.append("\n" + formatter.format(Double.valueOf(question.getCandCAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonFour.append("\n" + formatter.format(Double.valueOf(question.getCandDAttribute())) + " " + question.questionTemplate.getAttributeUnit());
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

        if (id == R.id.action_report) {
            new SendEmailAsyncTask().execute();
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

    private class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                GMailSender sender = new GMailSender("semanticquizappfeedback@gmail.com", "124nv9Av53");
                sender.sendMail("[Semantic Quiz App] Error Report",
                        "Correct Answer: " + question.getCorrectAnswer() +
                                "\nCandidate A: " + question.getCandAName() + " " + question.getCandAAttribute() +
                                "\nCandidate B: " + question.getCandBName() + " " + question.getCandBAttribute() +
                                "\nCandidate C: " + question.getCandCName() + " " + question.getCandCAttribute() +
                                "\nCandidate D: " + question.getCandDName() + " " + question.getCandDAttribute(),
                        "semanticquizappfeedback@gmail.com",
                        "matsschade@gmail.com");
                return true;
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
                return false;
            }
        }
    }
}
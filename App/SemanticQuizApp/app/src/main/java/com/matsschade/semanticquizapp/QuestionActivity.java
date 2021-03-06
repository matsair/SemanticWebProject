package com.matsschade.semanticquizapp;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.matsschade.semanticquizapp.Email.GMailSender;
import com.matsschade.semanticquizapp.Questions.Question;
import com.matsschade.semanticquizapp.Questions.QuestionTemplates;
import com.matsschade.semanticquizapp.Utils.GetQuestionOnlineTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class QuestionActivity extends AppCompatActivity {

    BootstrapButton buttonOne;
    BootstrapButton buttonTwo;
    BootstrapButton buttonThree;
    BootstrapButton buttonFour;
    TextView questionText;
    int categoryID;

    String snackBarText;

    private boolean questionFinished;

    public Question question;

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
        questionText.setVisibility(View.INVISIBLE);
        buttonOne = (BootstrapButton) findViewById(R.id.answer_button_one);
        buttonOne.setVisibility(View.INVISIBLE);
        buttonTwo = (BootstrapButton) findViewById(R.id.answer_button_two);
        buttonTwo.setVisibility(View.INVISIBLE);
        buttonThree = (BootstrapButton) findViewById(R.id.answer_button_three);
        buttonThree.setVisibility(View.INVISIBLE);
        buttonFour = (BootstrapButton) findViewById(R.id.answer_button_four);
        buttonFour.setVisibility(View.INVISIBLE);

        QuestionTemplates.initializeQueries();
        newQuestionTask();

    }

    public void newQuestionTask() {
        questionFinished = false;
        new GetQuestionOnlineTask(this).execute(categoryID);
    }

    public void setupButtons() {

        questionText.setVisibility(View.VISIBLE);
        buttonOne.setVisibility(View.VISIBLE);
        buttonTwo.setVisibility(View.VISIBLE);
        buttonThree.setVisibility(View.VISIBLE);
        buttonFour.setVisibility(View.VISIBLE);

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
                if (question.getCorrectAnswer().equals(question.getCandBName())) {
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
                if (question.getCorrectAnswer().equals(question.getCandCName())) {
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
                if (question.getCorrectAnswer().equals(question.getCandDName())) {
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

    public void generateNewQuestion(){

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

            questionText.setText("\"" + questionPlaceholder + "\"" + question.questionTemplate.getQuestion());
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

    public void resetButtonColors() {
        buttonOne.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonTwo.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonThree.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        buttonFour.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        makeButtonsClickable(true);
        changeButtonTextSize(18);
    }

    private void finishQuestion() {

        NumberFormat formatter = DecimalFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(0);

        makeButtonsClickable(false);
        if (question.questionTemplate.getAttributeUnit().equals("km2")) {
            changeButtonTextSize(14);
            buttonOne.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandAAttribute())) + " " + "km<sup>2</sup>"));
            buttonTwo.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandBAttribute())) + " " + "km<sup>2</sup>"));
            buttonThree.append(Html.fromHtml("<br>" +formatter.format(Double.valueOf(question.getCandCAttribute())) + " " + "km<sup>2</sup>"));
            buttonFour.append(Html.fromHtml("<br>" + formatter.format(Double.valueOf(question.getCandDAttribute())) + " " + "km<sup>2</sup>"));

        } else if (question.questionTemplate.getAttributeType().equals("string") && !question.questionTemplate.getAttribute().equals("name")) {
            changeButtonTextSize(14);
            buttonOne.append("\n" + question.getCandAAttribute());
            buttonTwo.append("\n" + question.getCandBAttribute());
            buttonThree.append("\n" + question.getCandCAttribute());
            buttonFour.append("\n" + question.getCandDAttribute());

        } else if (question.questionTemplate.getAttributeType().equals("year")) {
            changeButtonTextSize(14);
            buttonOne.append("\n" + Double.valueOf(question.getCandAAttribute()).intValue());
            buttonTwo.append("\n" + Double.valueOf(question.getCandBAttribute()).intValue());
            buttonThree.append("\n" + Double.valueOf(question.getCandCAttribute()).intValue());
            buttonFour.append("\n" + Double.valueOf(question.getCandDAttribute()).intValue());

        }
        else if (question.questionTemplate.getAttribute().equals("director")) {
            // Do nothing
        }
        else if (question.questionTemplate.getAttribute().equals("name")) {
            // Do nothing
        }
        else {
            changeButtonTextSize(14);
            buttonOne.append("\n" + formatter.format(Double.valueOf(question.getCandAAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonTwo.append("\n" + formatter.format(Double.valueOf(question.getCandBAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonThree.append("\n" + formatter.format(Double.valueOf(question.getCandCAttribute())) + " " + question.questionTemplate.getAttributeUnit());
            buttonFour.append("\n" + formatter.format(Double.valueOf(question.getCandDAttribute())) + " " + question.questionTemplate.getAttributeUnit());
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackBarText, Snackbar.LENGTH_INDEFINITE)
                .setAction("Next Question", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newQuestionTask();
                    }
                })
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
        questionFinished = true;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_report);
        item.setVisible(questionFinished);
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
            SendEmailAsyncTask sendEmailAsyncTask = new SendEmailAsyncTask();
            sendEmailAsyncTask.setmActivity(this);
            sendEmailAsyncTask.execute();
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

    private String readEmailAddress() {

        String ret = "";

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.email);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("Email Address", ret);
        return ret;
    }

    private String readEmailAddressPassword() {

        String ret = "";

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.password);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("Password", ret);
        return ret;
    }

    private class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {

        Activity mActivity;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String email = readEmailAddress();
                String password = readEmailAddressPassword();
                GMailSender sender = new GMailSender(email, password);
                sender.sendMail("[Semantic Quiz App] Error Report",
                        "Question: " + question.questionTemplate.getQuestion() +
                                "\nCorrect Answer: " + question.getCorrectAnswer() +
                                "\nCandidate A: " + question.getCandAName() + " " + question.getCandAAttribute() + question.questionTemplate.getAttributeUnit() +
                                "\nCandidate B: " + question.getCandBName() + " " + question.getCandBAttribute() + question.questionTemplate.getAttributeUnit() +
                                "\nCandidate C: " + question.getCandCName() + " " + question.getCandCAttribute() + question.questionTemplate.getAttributeUnit() +
                                "\nCandidate D: " + question.getCandDName() + " " + question.getCandDAttribute() + question.questionTemplate.getAttributeUnit(),
                        "semanticquizappfeedback@gmail.com",
                        "matsschade@gmail.com");
                return true;
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(mActivity, "Report sent, thanks!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mActivity, "Report not sent, something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }

        public void setmActivity(Activity mActivity) {
            this.mActivity = mActivity;
        }
    }
}
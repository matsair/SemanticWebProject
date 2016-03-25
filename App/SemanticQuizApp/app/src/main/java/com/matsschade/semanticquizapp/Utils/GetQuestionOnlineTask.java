package com.matsschade.semanticquizapp.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.matsschade.semanticquizapp.QuestionActivity;
import com.matsschade.semanticquizapp.Questions.Question;

/**
 * Created by Mats on 23/03/16.
 */
public class GetQuestionOnlineTask extends AsyncTask<Integer, String, Question>
{
    public QuestionActivity activity;
    private ProgressDialog dialog;

    public GetQuestionOnlineTask(QuestionActivity a)
    {
        dialog = new ProgressDialog(a);
        this.activity = a;
    }

    @Override
    protected Question doInBackground(Integer... params) {
        try {
            Question q = new Question(params[0]);
            Log.d("ATTRIBUTE TYPE", q.questionTemplate.getAttributeType());
            return q;
        } catch (Exception e) {
            Log.e("GetSparqlResult", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Question result)
    {
        if (result != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            result.initializeCandidates(activity);
            result.determineCorrectAnswer();
            activity.question = result;
            activity.resetButtonColors();
            activity.setupButtons();
            activity.generateNewQuestion();
        }
        else {
            Log.d("ERROR", "Question from AsyncTask is null");
        }

    }
}

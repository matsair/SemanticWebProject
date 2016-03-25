package com.matsschade.semanticquizapp.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.matsschade.semanticquizapp.StartActivity;

import java.io.IOException;

/**
 * Created by Mats on 24/03/16.
 */
public class CheckInternetConnTask extends AsyncTask<Void, Void, Boolean> {

        public StartActivity activity;
        private ProgressDialog dialog;

        public CheckInternetConnTask(StartActivity a) {
            dialog = new ProgressDialog(a);
            dialog.setMessage("Connecting...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            this.activity = a;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            while (!isCancelled()) {
                Runtime runtime = Runtime.getRuntime();
                try {

                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    return (exitValue == 0);

                } catch (IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }

                return false;
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (result) {
                activity.startQuiz();
            }
        }

        @Override
        protected void onCancelled() {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

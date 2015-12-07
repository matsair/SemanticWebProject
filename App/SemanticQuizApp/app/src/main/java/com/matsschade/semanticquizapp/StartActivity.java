package com.matsschade.semanticquizapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.matsschade.semanticquizapp.intro.Intro;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class StartActivity extends AppCompatActivity {

    BootstrapButton startQuiz;

    private Intent intent;

    private PieChartView chart;
    private PieChartView emptyChart;
    private PieChartData data;

    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = true;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private boolean isRotationEnabled = false;
    private boolean isClickable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (Build.VERSION.SDK_INT >= 23) {
            int requestId = 1;
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestId);
            }
        }

        //doesQueryWork.test();

        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            Intent intent = new Intent(this, Intro.class);
            startActivity(intent);
        }

        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = new Intent(getBaseContext(), QuestionActivity.class);

        emptyChart = (PieChartView) findViewById(R.id.empty_chart);
        chart = (PieChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new PieTouchListener());

        startQuiz = (BootstrapButton) findViewById(R.id.start_quiz_button);

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        getPieChartData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPieChartData();
    }

    private void getPieChartData() {
        SharedPreferences prefs = getSharedPreferences("score", 0);
        int correct = prefs.getInt("correct", 0);
        int wrong = prefs.getInt("wrong", 0);

        List<SliceValue> values = new ArrayList<SliceValue>();

        SliceValue sliceValueCorrect = new SliceValue(correct, ContextCompat.getColor(this, R.color.correct));
        sliceValueCorrect.setLabel("Correct");
        values.add(sliceValueCorrect);
        SliceValue sliceValueWrong = new SliceValue(wrong, ContextCompat.getColor(this, R.color.wrong));
        sliceValueWrong.setLabel("Wrong");
        values.add(sliceValueWrong);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            data.setCenterText1FontSize(19);
            data.setCenterText1("Stats");
            data.setCenterText1Typeface((Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)));
        }

        chart.setPieChartData(data);
        chart.setChartRotationEnabled(isRotationEnabled);
        chart.setClickable(isClickable);

        if (wrong > 0 || correct > 0) {
            chart.setVisibility(View.VISIBLE);
            emptyChart.setVisibility(View.INVISIBLE);
        }
        else {
            emptyChart.setVisibility(View.VISIBLE);
            chart.setVisibility(View.INVISIBLE);
        }
    }

    private void startQuiz() {
        if (isNetworkAvailable()) {
            startActivity(intent);
        }
        else {
            Toast.makeText(getBaseContext(), "Please connect to the internet to start the quiz.", Toast.LENGTH_SHORT).show();
        }
    }

    private class PieTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getBaseContext(), String.valueOf(value.getValue()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // do nothing

        }
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

        if (id == R.id.action_reset) {
            new MaterialDialog.Builder(this)
                    .title("Confirmation")
                    .content("Are you sure you want to reset your stats?")
                    .positiveText("Yes, I'm sure")
                    .negativeText("No, don't reset")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferences prefs = getSharedPreferences("score", 0);
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putInt("wrong", 0);
                            edit.putInt("correct", 0);
                            edit.commit();
                            getPieChartData();
                        }
                    })
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_cities:
                if (checked)
                    intent.putExtra("category", 0);
                    break;
            case R.id.radio_companies:
                if (checked)
                    intent.putExtra("category", 1);
                    break;
            case R.id.radio_countries:
                if (checked)
                    intent.putExtra("category", 2);
                break;
            case R.id.radio_movies:
                if (checked)
                    intent.putExtra("category", 3);
                break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

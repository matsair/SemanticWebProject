package com.matsschade.semanticquizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

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
    private PieChartData emptyData;

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

        chart = (PieChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new PieTouchListener());
        emptyChart = (PieChartView) findViewById(R.id.empty_chart);

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

        SliceValue sliceValueCorrect = new SliceValue(correct, getColor(this, R.color.correct));
        sliceValueCorrect.setLabel("Correct");
        values.add(sliceValueCorrect);
        SliceValue sliceValueWrong = new SliceValue(wrong, getColor(this, R.color.wrong));
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

        if (values.size() > 0) {
            emptyChart.setVisibility(View.INVISIBLE);
            chart.setVisibility(View.VISIBLE);
        }
        else {
            SliceValue sliceValue = new SliceValue(1, getColor(this, R.color.colorPrimary));
            values.add(sliceValue);
            emptyData = new PieChartData(values);
            emptyData.setHasCenterCircle(hasCenterCircle);
            emptyData.setCenterText1("No data");
            emptyData.setCenterText1FontSize(15);
            emptyData.setCenterText2("yet");
            emptyData.setCenterText2FontSize(15);
            emptyData.setHasLabels(false);
            emptyChart.setPieChartData(emptyData);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
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
                    // TODO
                    // change passed integer values
                    intent.putExtra("category", 0);
                    break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }
}

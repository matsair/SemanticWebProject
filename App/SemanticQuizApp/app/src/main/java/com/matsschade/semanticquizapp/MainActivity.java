package com.matsschade.semanticquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.matsschade.semanticquizapp.database.DatabaseManager;
import com.matsschade.semanticquizapp.database.QuestionDbHelper;
import com.matsschade.semanticquizapp.intro.Intro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            Intent intent = new Intent(this, Intro.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseManager.initializeInstance(new QuestionDbHelper(this));

        QuestionDbHelper.addCategory("Companies");
        QuestionDbHelper.addQuestion("Which of the following has the most employees?", QuestionDbHelper.getCategoryId("Companies"));

        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText(R.string.question);
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

package com.matsschade.semanticquizapp.Intro;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.matsschade.semanticquizapp.R;
import com.matsschade.semanticquizapp.StartActivity;

/**
 * Created by Mats on 10/11/15.
 */
public class Intro extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(Slide.newInstance(R.layout.intro_welcome));
        addSlide(Slide.newInstance(R.layout.intro_permissions));

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);

    }

    @Override
    public void onDonePressed() {
        saveFirstRun();
        loadMainActivity();
    }
    @Override
    public void onNextPressed() {
    }

    @Override
    public void onSlideChanged() {
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void saveFirstRun() {
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();
    }
}

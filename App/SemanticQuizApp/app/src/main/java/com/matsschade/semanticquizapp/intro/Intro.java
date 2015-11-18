package com.matsschade.semanticquizapp.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.matsschade.semanticquizapp.MainActivity;
import com.matsschade.semanticquizapp.R;

/**
 * Created by Mats on 10/11/15.
 */
public class Intro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance("Welcome", "This quiz app uses semantic web technologies.", R.drawable.news, R.color.colorPrimary));

        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(false);
        showDoneButton(true);
        setFadeAnimation();
    }

    @Override
    public void onSkipPressed() {
        saveFirstRun();
        loadMainActivity();
    }

    @Override
    public void onDonePressed() {
        saveFirstRun();
        loadMainActivity();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveFirstRun() {
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();
    }
}

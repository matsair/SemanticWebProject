package semanticweb.hws14.movapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import semanticweb.hws14.activities.R;

public class License extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        TextView lmdbLicenseTV = (TextView) findViewById(R.id.LinkedMDBLicense);
        TextView omdbLicenseTV = (TextView) findViewById(R.id.OMDbApiLicense);
        TextView dbpediaLicenseTV = (TextView) findViewById(R.id.DBpediaLicense);
        lmdbLicenseTV.setMovementMethod(LinkMovementMethod.getInstance());
        omdbLicenseTV.setMovementMethod(LinkMovementMethod.getInstance());
        dbpediaLicenseTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

}

package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.fragments.AboutFragment;
import es.upv.dadm.myquotesapp.fragments.FavouriteFragment;
import es.upv.dadm.myquotesapp.fragments.QuotationFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // All buttons share the same OnClickListener
        final View.OnClickListener listener = v -> testLayout(v.getId());

        findViewById(R.id.b_get_quotations).setOnClickListener(listener);
        findViewById(R.id.b_favourite_quotations).setOnClickListener(listener);
        findViewById(R.id.b_settings).setOnClickListener(listener);
        findViewById(R.id.b_about).setOnClickListener(listener);
    }

    // This method is activated whenever any of the buttons is clicked.
    // It launches a new activity that displays the type of layout selected.
    private void testLayout(int buttonClicked) {
        Intent intent;
        if (buttonClicked == R.id.b_get_quotations) {
            intent = new Intent(DashboardActivity.this, QuotationFragment.class);
            //intent.putExtra(Util.LAYOUT, Util.VERTICAL);
        } else if (buttonClicked == R.id.b_favourite_quotations) {
            intent = new Intent(DashboardActivity.this, FavouriteFragment.class);
            //intent.putExtra(Util.LAYOUT, Util.HORIZONTAL);
        } else if (buttonClicked == R.id.b_settings) {
            intent = new Intent(DashboardActivity.this, SettingsActivity.class);
            //intent.putExtra(Util.LAYOUT, Util.NESTED);
        } else { // (buttonClicked == R.id.b_about)
            intent = new Intent(DashboardActivity.this, AboutFragment.class);
            //intent.putExtra(Util.LAYOUT, Util.RELATIVE);
        }
        startActivity(intent);
    }

}
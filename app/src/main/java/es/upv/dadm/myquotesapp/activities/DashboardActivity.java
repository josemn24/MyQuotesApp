package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.fragments.AboutFragment;
import es.upv.dadm.myquotesapp.fragments.FavouriteFragment;
import es.upv.dadm.myquotesapp.fragments.QuotationFragment;

public class DashboardActivity extends AppCompatActivity {

    Class<? extends Fragment> fragment_class;

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
//        Intent intent;
        if (buttonClicked == R.id.b_get_quotations) {
            fragment_class = QuotationFragment.class;
        } else if (buttonClicked == R.id.b_favourite_quotations) {
        } else if (buttonClicked == R.id.b_settings) {
        } else { // (buttonClicked == R.id.b_about)
            fragment_class = QuotationFragment.class;
        }
//        startActivity(intent);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.add(R.id.fragment_dashboard_container, fragment_class, null);
        transaction.commit();
    }

}
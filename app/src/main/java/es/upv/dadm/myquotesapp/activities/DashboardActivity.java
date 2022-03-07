package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.fragments.AboutFragment;
import es.upv.dadm.myquotesapp.fragments.FavouriteFragment;
import es.upv.dadm.myquotesapp.fragments.QuotationFragment;
import es.upv.dadm.myquotesapp.fragments.SettingsFragment;

public class DashboardActivity extends AppCompatActivity {

    Class<? extends Fragment> fragment_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ((BottomNavigationView) findViewById(R.id.bottomView)).setOnItemSelectedListener(
                item -> testLayout(item.getItemId())
        );

        // If the activity is being created for the first time, then display the QuotationFragment,
        // otherwise let the system recreate whatever was being displayed previously
        if (savedInstanceState == null) {
            final Bundle bundle = new Bundle();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_dashboard_container, QuotationFragment.class, bundle)
                    .commit();
        }

        // All buttons share the same OnClickListener
        /* final View.OnClickListener listener = v -> testLayout(v.getId());

        findViewById(R.id.b_get_quotations).setOnClickListener(listener);
        findViewById(R.id.b_favourite_quotations).setOnClickListener(listener);
        findViewById(R.id.b_settings).setOnClickListener(listener);
        findViewById(R.id.b_about).setOnClickListener(listener); */
    }

    // This method is activated whenever any of the buttons is clicked.
    // It launches a new activity that displays the type of layout selected.
    private boolean testLayout(int buttonClicked) {
//        Intent intent;
        int actionBarTitle = R.string.app_name;
        if (buttonClicked == R.id.btn_get_quotations) {
            fragment_class = QuotationFragment.class;
            actionBarTitle = R.string.get_quotations;
        } else if (buttonClicked == R.id.btn_favourite_quotations) {
            fragment_class = FavouriteFragment.class;
            actionBarTitle = R.string.favourite_quotations;
        } else if (buttonClicked == R.id.btn_settings) {
            fragment_class = FavouriteFragment.class;
            actionBarTitle = R.string.favourite_quotations;
        } else { // (buttonClicked == R.id.b_about)
            fragment_class = AboutFragment.class;
            actionBarTitle = R.string.about;

        }
//        startActivity(intent);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setReorderingAllowed(true);
        if(fragment_class != null) {
            transaction.replace(R.id.fragment_dashboard_container, fragment_class, null);
        }
        transaction.commit();
        this.setTitle(actionBarTitle);

        return true;
    }

}
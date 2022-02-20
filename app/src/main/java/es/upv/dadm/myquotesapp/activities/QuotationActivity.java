package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import es.upv.dadm.myquotesapp.R;

public class QuotationActivity extends AppCompatActivity {

    private TextView textViewSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final View.OnClickListener listener = v -> onClick();

        textViewSample = findViewById(R.id.tw_greeting);

        String text = textViewSample.getText().toString();
        String username = prefs.getString("settings_username", "Nameless One");

        textViewSample.setText(text.replace("%1s!", username));

        findViewById(R.id.ib_new_quotation).setOnClickListener(listener);



    }

    public void onClick() {
        // do something when the button is clicked
        textViewSample = findViewById(R.id.tw_greeting);
        final TextView textViewAuthor = findViewById(R.id.tw_author);

        textViewSample.setText(R.string.quotation_text_view_2);
        textViewAuthor.setText(R.string.quotation_text_view_3);

    }
}
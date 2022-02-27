package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

import java.util.List;
import java.io.*;

import es.upv.dadm.myquotesapp.R;

public class QuotationActivity extends AppCompatActivity {

    private int quotationCounter = 0;
    private Menu menu;
    private boolean addVisible;
    private TextView textViewAuthor;
    private TextView textViewSample;
    private String oldTextViewSample;
    private String oldTextViewAuthor;
    private boolean refreshVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        textViewSample = findViewById(R.id.tw_greeting);
        textViewAuthor = findViewById(R.id.tw_author);
        oldTextViewSample = getString(R.string.quotation_text_view_2);
        oldTextViewAuthor = getString(R.string.quotation_text_view_3);
        addVisible = false;
        refreshVisible = true;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

//        final View.OnClickListener listener = v -> onClick();

        textViewSample = findViewById(R.id.tw_greeting);

        if(savedInstanceState == null) {
            String text = textViewSample.getText().toString();
            String username = prefs.getString("settings_username", "Nameless One");

            textViewSample.setText(text.replace("%1s!", username));
        }
        else{
            quotationCounter = savedInstanceState.getInt("quotationCounter");
            textViewSample.setText(savedInstanceState.getString("quotation_text_view_2"));
            textViewAuthor.setText(savedInstanceState.getString("quotation_text_view_3"));
            addVisible = savedInstanceState.getBoolean("addVisible");
            supportInvalidateOptionsMenu();
        }

//        findViewById(R.id.ib_new_quotation).setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.quotation, menu);
        menu.findItem(R.id.item_add_quotation).setVisible(addVisible);
        menu.findItem(R.id.item_new_quotation).setVisible(refreshVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_new_quotation) {
            textViewSample.setText(oldTextViewSample.replace("%1$d",
                    Integer.toString(quotationCounter)));
//            textViewSample.setText(R.string.quotation_text_view_2);
            textViewAuthor.setText(oldTextViewAuthor.replace("%1$d",
                    Integer.toString(quotationCounter)));
//            textViewAuthor.setText(R.string.quotation_text_view_3);
            quotationCounter++;
            addVisible = true;
            menu.findItem(R.id.item_add_quotation).setVisible(addVisible);
            return true;
        } else if (item.getItemId() == R.id.item_add_quotation) {
            addVisible = false;
            menu.findItem(R.id.item_add_quotation).setVisible(addVisible);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("quotation_text_view_2", textViewSample.getText().toString());
        outState.putString("quotation_text_view_3", textViewAuthor.getText().toString());
        outState.putInt("quotationCounter", quotationCounter);
        outState.putBoolean("addVisible", addVisible);
        super.onSaveInstanceState(outState);
    }

    public void setTextViewSample(String textViewSampleString) {
        this.textViewSample.setText(textViewSampleString);
    }

    public void setTextViewAuthor(String textViewAuthorString) {
        this.textViewAuthor.setText(textViewAuthorString);
    }

    //    public void onClick() {
//        // do something when the button is clicked
//        textViewSample = findViewById(R.id.tw_greeting);
//        final TextView textViewAuthor = findViewById(R.id.tw_author);
//
//        textViewSample.setText(R.string.quotation_text_view_2);
//        textViewAuthor.setText(R.string.quotation_text_view_3);
//
//    }
}
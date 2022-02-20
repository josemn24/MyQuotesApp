package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

import java.util.List;

import es.upv.dadm.myquotesapp.R;

public class QuotationActivity extends AppCompatActivity {

    private TextView textViewSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

//        final View.OnClickListener listener = v -> onClick();

        textViewSample = findViewById(R.id.tw_greeting);

        String text = textViewSample.getText().toString();

        textViewSample.setText(text.replace("%1s!", "Nameless one"));

//        findViewById(R.id.ib_new_quotation).setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_new_quotation) {
            textViewSample = findViewById(R.id.tw_greeting);
            final TextView textViewAuthor = findViewById(R.id.tw_author);

            textViewSample.setText(R.string.quotation_text_view_2);
            textViewAuthor.setText(R.string.quotation_text_view_3);
            return true;
        } else if (item.getItemId() == R.id.item_add_quotation) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
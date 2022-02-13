package es.upv.dadm.myquotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        final View.OnClickListener listener = v -> onClick();

        //findViewById(R.id.tw_greeting).setText;
        findViewById(R.id.ib_new_quotation).setOnClickListener(listener);



    }

    public void onClick() {
        // do something when the button is clicked
    }
}
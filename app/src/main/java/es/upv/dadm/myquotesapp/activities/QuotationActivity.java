package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.io.*;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.databases.QuotationsDatabase;
import es.upv.dadm.myquotesapp.pojo.Quotation;
import es.upv.dadm.myquotesapp.requests.QuotationRequest;

public class QuotationActivity extends AppCompatActivity {

    private final String myUrl = "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=";
    private final String myPostUrl = "https://api.forismatic.com/api/1.0/";
    private final String body = "method=getQuote&format=json&lang=";
    private String prefLanguage;
    private String prefRequest;
    private Menu menu;
    private boolean addVisible;
    private TextView textViewAuthor;
    private TextView textViewSample;
    private String oldTextViewSample;
    private String oldTextViewAuthor;
    private boolean refreshVisible;
    private RequestQueue queue;

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

        textViewSample = findViewById(R.id.tw_greeting);

        if (savedInstanceState == null) {
            String text = textViewSample.getText().toString();
            String username = prefs.getString("settings_username", "Nameless One");
            prefLanguage = prefs.getString("list_languages", "English");
            prefRequest = prefs.getString("list_requests", "GET");

            textViewSample.setText(text.replace("%1s!", username));
        } else {
            textViewSample.setText(savedInstanceState.getString("quotation_text_view_2"));
            textViewAuthor.setText(savedInstanceState.getString("quotation_text_view_3"));
            addVisible = savedInstanceState.getBoolean("addVisible");
            supportInvalidateOptionsMenu();
        }

        // Restful
        this.queue = Volley.newRequestQueue(getApplicationContext());

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
            if (isConnected()) {
                this.beforeRequest();
                this.newQuotationRequest();
            } else {
                Toast.makeText(QuotationActivity.this, R.string.quotation_activity_toast_no_connection, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.item_add_quotation) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Include here the code to access the database
                    QuotationActivity.this.addQuotation();
                }
            }).start();

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
        outState.putBoolean("addVisible", addVisible);
        super.onSaveInstanceState(outState);
    }

    public void setActionBarOptionsVisibility(boolean refreshVisibility, boolean addVisibility) {
        addVisible = addVisibility;
        refreshVisible = refreshVisibility;
        menu.findItem(R.id.item_add_quotation).setVisible(addVisible);
        menu.findItem(R.id.item_new_quotation).setVisible(refreshVisible);
    }

    public void setProgressbarVisibility(boolean visibility) {
        int visible = visibility ? View.VISIBLE : View.INVISIBLE;
        findViewById(R.id.progressBar2).setVisibility(visible);
    }

    public void addQuotation() {
        Quotation newQuotation = new Quotation(textViewSample.getText().toString(), textViewAuthor.getText().toString());
        QuotationsDatabase.getInstance(this).quotationDao().addQuotation(newQuotation);
    }

    public boolean isConnected() {
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 22) {
            final Network activeNetwork = manager.getActiveNetwork();
            if (activeNetwork != null) {
                final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
                result = networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            result = ((info != null) && (info.isConnected()));
        }
        return result;
    }

    public void displayQuotation(Quotation quotation) {

        if(quotation == null) {
            Toast.makeText(QuotationActivity.this, R.string.quotation_activity_toast_no_quote, Toast.LENGTH_SHORT).show();
        } else {
            textViewSample.setText(quotation.getQuoteText());
            textViewAuthor.setText(quotation.getQuoteAuthor());
            this.setProgressbarVisibility(false);

            // Thread
            Thread thread = new ThreadClass(QuotationActivity.this);
            thread.start();

        }

    }

    public void beforeRequest() {
        this.setActionBarOptionsVisibility(false, false);
        this.setProgressbarVisibility(true);
    }

    public void newQuotationRequest() {

        int requestMethod = this.prefRequest.equals("GET") ? Request.Method.GET : Request.Method.POST;
        String requestUrl = this.prefRequest.equals("GET") ? this.myUrl + this.getPrefLanguage() : this.myPostUrl;
        String bodyRequest = this.prefRequest.equals("GET") ? "" : this.body + this.getPrefLanguage();

        QuotationRequest request = new QuotationRequest(requestMethod, requestUrl, bodyRequest,
                new Response.Listener<Quotation>() {
                    @Override
                    public void onResponse(Quotation quotationResponse) {
                        displayQuotation(quotationResponse);
                        //Toast.makeText(QuotationActivity.this, quotationResponse.getQuoteText(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayQuotation(null);
                    }
                });
        this.queue.add(request);
    }

    public String getPrefLanguage() {
        return this.prefLanguage.equals("English") ? "en" : "ru";
    }

    private class ThreadClass extends Thread {
        private final WeakReference<QuotationActivity> reference;
        ThreadClass(QuotationActivity activity) {
            super();
            this.reference = new WeakReference<QuotationActivity>(activity);
        }
        @Override
        public void run() {
//            Handler handler = new Handler(Looper.getMainLooper());
            Quotation dbQuotation = QuotationsDatabase.getInstance(QuotationActivity.this).quotationDao().getQuotation(textViewSample.getText().toString());
            try {
                reference.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(reference.get() != null){
                            boolean addVisibility = (dbQuotation == null) ? true : false;
                            reference.get().setActionBarOptionsVisibility(true, addVisibility);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
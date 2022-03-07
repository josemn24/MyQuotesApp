package es.upv.dadm.myquotesapp.fragments;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.databases.QuotationsDatabase;
import es.upv.dadm.myquotesapp.pojo.Quotation;
import es.upv.dadm.myquotesapp.requests.QuotationRequest;

public class QuotationFragment extends Fragment {

    private final String myUrl = "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=";
    private final String myPostUrl = "https://api.forismatic.com/api/1.0/";
    private final String body = "method=getQuote&format=json&lang=";
    private String prefLanguage;
    private String prefRequest;
    private Menu menu;
    private boolean addVisible;
    private TextView textViewAuthor;
    private TextView textViewSample;
    FloatingActionButton floatingActionButton;
    private String oldTextViewSample;
    private String oldTextViewAuthor;
    private boolean refreshVisible;
    private RequestQueue queue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    public QuotationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_quotation, container, false);;

        swipeRefreshLayout = view.findViewById(R.id.swipelayout);
        floatingActionButton = view.findViewById(R.id.fabMessage);
        textViewSample = view.findViewById(R.id.tw_greeting);
        textViewAuthor = view.findViewById(R.id.tw_author);
        oldTextViewSample = getString(R.string.quotation_text_view_2);
        oldTextViewAuthor = getString(R.string.quotation_text_view_3);
        addVisible = false;
        refreshVisible = true;

        floatingActionButton.setVisibility(addVisible ? View.VISIBLE : View.INVISIBLE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        textViewSample = view.findViewById(R.id.tw_greeting);

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
            getActivity().invalidateOptionsMenu();
        }

        // Restful
        this.queue = Volley.newRequestQueue(getContext());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Snackbar snackbar;
                CoordinatorLayout coordinatorLayout;

                if (isConnected()) {
                    QuotationFragment.this.beforeRequest();
                    QuotationFragment.this.newQuotationRequest();
                } else {
//                    Toast.makeText(getContext(), R.string.quotation_activity_toast_no_connection, Toast.LENGTH_SHORT).show();
                    coordinatorLayout = view.findViewById(R.id.coordinatorLayoutQuotation);
                    snackbar = Snackbar.make(coordinatorLayout, R.string.quotation_activity_toast_no_connection, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Include here the code to access the database
                        QuotationFragment.this.addQuotation();
                    }
                }).start();

                addVisible = false;
                floatingActionButton.setVisibility(addVisible ? View.VISIBLE : View.INVISIBLE);
            }
        });


        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.menu = menu;
        menuInflater.inflate(R.menu.quotation, menu);
        menu.findItem(R.id.item_new_quotation).setVisible(refreshVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar snackbar;
        CoordinatorLayout coordinatorLayout;
        if (item.getItemId() == R.id.item_new_quotation) {
            if (isConnected()) {
                this.beforeRequest();
                this.newQuotationRequest();
                swipeRefreshLayout.setRefreshing(true);
            } else {
//                Toast.makeText(getContext(), R.string.quotation_activity_toast_no_connection, Toast.LENGTH_SHORT).show();
                coordinatorLayout = view.findViewById(R.id.coordinatorLayoutQuotation);
                snackbar = Snackbar.make(coordinatorLayout, R.string.quotation_activity_toast_no_connection, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
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
        floatingActionButton.setVisibility(addVisible ? View.VISIBLE : View.INVISIBLE);
        menu.findItem(R.id.item_new_quotation).setVisible(refreshVisible);
    }

    public void addQuotation() {
        Quotation newQuotation = new Quotation(textViewSample.getText().toString(), textViewAuthor.getText().toString());
        QuotationsDatabase.getInstance(getContext()).quotationDao().addQuotation(newQuotation);
    }

    public boolean isConnected() {
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
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
        Snackbar snackbar;
        CoordinatorLayout coordinatorLayout;

        if (quotation == null) {
//            Toast.makeText(getContext(), R.string.quotation_activity_toast_no_quote, Toast.LENGTH_SHORT).show();
            coordinatorLayout = view.findViewById(R.id.coordinatorLayoutQuotation);
            snackbar = Snackbar.make(coordinatorLayout, R.string.quotation_activity_toast_no_quote, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            textViewSample.setText(quotation.getQuoteText());
            textViewAuthor.setText(quotation.getQuoteAuthor());

            // Thread
            Thread thread = new ThreadClass(QuotationFragment.this);
            thread.start();

        }

    }

    public void beforeRequest() {
        this.setActionBarOptionsVisibility(false, false);
    }

    public void newQuotationRequest() {

        int requestMethod = this.prefRequest.equals("GET") ? Request.Method.GET : Request.Method.POST;
        String requestUrl = this.prefRequest.equals("GET") ? this.myUrl + this.getPrefLanguage() : this.myPostUrl;
        String bodyRequest = this.prefRequest.equals("GET") ? "" : this.body + this.getPrefLanguage();

        QuotationRequest request = new QuotationRequest(requestMethod, requestUrl, bodyRequest,
                new Response.Listener<Quotation>() {
                    @Override
                    public void onResponse(Quotation quotationResponse) {
                        swipeRefreshLayout.setRefreshing(false);
                        displayQuotation(quotationResponse);
                        //Toast.makeText(getContext(), quotationResponse.getQuoteText(), Toast.LENGTH_SHORT).show();
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
        private final WeakReference<QuotationFragment> reference;

        ThreadClass(QuotationFragment fragment) {
            super();
            this.reference = new WeakReference<QuotationFragment>(fragment);
        }

        @Override
        public void run() {
//            Handler handler = new Handler(Looper.getMainLooper());
            String x = textViewSample.getText().toString();
            Quotation dbQuotation = QuotationsDatabase.getInstance(getContext()).quotationDao().getQuotation(textViewSample.getText().toString());
            try {
//                Context context = reference.get().getActivity();
                reference.get().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (reference.get() != null) {
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
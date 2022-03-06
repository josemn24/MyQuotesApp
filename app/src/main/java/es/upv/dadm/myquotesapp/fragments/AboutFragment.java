package es.upv.dadm.myquotesapp.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.upv.dadm.myquotesapp.R;

public class AboutFragment extends  androidx.fragment.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, null);
    }

    public AboutFragment() {
    }
}
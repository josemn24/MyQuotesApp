package es.upv.dadm.myquotesapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.adapters.FavouriteQuotesAdapter;
import es.upv.dadm.myquotesapp.pojo.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        //final View.OnClickListener listener = v -> onClick();

        RecyclerView recycler = findViewById(R.id.rv_favourite);

        LinearLayoutManager manager = new LinearLayoutManager(
                FavouriteActivity.this, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(this, manager.getOrientation());
        recycler.addItemDecoration(divider);

        List<Quotation> data = getMockQuotations();

        FavouriteQuotesAdapter adapter = new FavouriteQuotesAdapter(data, new FavouriteQuotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quotation quotation) {
                redirectToWikipedia(quotation);
            }
        });


        recycler.setAdapter(adapter);

        //findViewById(R.id.b_author_information).setOnClickListener(listener);
    }

    public void redirectToWikipedia(Quotation quotation) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String uri = "https://en.wikipedia.org/wiki/Special:Search?search=" + quotation.getQuoteAuthor();
        intent.setData(Uri.parse(uri));

        List<ResolveInfo> activities =
                getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    public void onClick() {
        // do something when the button is clicked
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=Albert Einstein"));

        List<ResolveInfo> activities =
                getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            startActivity(intent);
        }

    }

    public List<Quotation> getMockQuotations() {
      List<Quotation> res = new ArrayList<Quotation>();

      res.add(new Quotation("Moral excellence comes about as a result of habit. We become just by doing just acts, temperate by doing temperate acts, brave by doing brave acts.", "Aristotle"));
      res.add(new Quotation("There is nothing like returning to a place that remains unchanged to find the ways in which you yourself have altered.", "Nelson Mandela"));
      res.add(new Quotation("My advice to you is not to inquire why or whither, but just enjoy your ice cream while its on your plate — that's my philosophy.", "Wilder"));
      res.add(new Quotation("Be a yardstick of quality. Some people aren't used to an environment where excellence is expected.", "Stephen Jobs"));
      res.add(new Quotation("The world is round and the place which may seem like the end may also be the beginning. ", "Ivy Baker Priest"));
      res.add(new Quotation("Every problem has a gift for you in its hands. ", "Richard Bach"));
      res.add(new Quotation("Happiness is the reward we get for living to the highest right we know. ", "Richard Bach"));
      res.add(new Quotation("It isn't what happens to us that causes us to suffer; it's what we say to ourselves about what happens.", "Pema Chodron"));
      res.add(new Quotation("Strength does not come from physical capacity. It comes from an indomitable will.", "Gandhi"));
      res.add(new Quotation("You learn to speak by speaking, to study by studying, to run by running, to work by working; in just the same way, you learn to love by loving. ", "Anatole France"));

      return res;
    }

}
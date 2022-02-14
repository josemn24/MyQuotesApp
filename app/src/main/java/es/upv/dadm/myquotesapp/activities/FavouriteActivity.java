package es.upv.dadm.myquotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import es.upv.dadm.myquotesapp.R;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        final View.OnClickListener listener = v -> onClick();

        findViewById(R.id.b_author_information).setOnClickListener(listener);
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
}
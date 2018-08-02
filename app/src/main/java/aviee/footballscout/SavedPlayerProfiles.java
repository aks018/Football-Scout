package aviee.footballscout;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aviee.footballscout.adapter.SavedReviewsAdapter;
import aviee.footballscout.database.DBHelper;
import aviee.footballscout.pojo.PlayerReview;

public class SavedPlayerProfiles extends AppCompatActivity {

    private DBHelper mydb;

    ListView listView;

    SavedReviewsAdapter savedReviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_player_profiles);

        mydb = new DBHelper(this);

        final List<PlayerReview> playerReviews = mydb.getAllPlayerReviews();

        listView = (ListView) findViewById(R.id.listViewSavedPlayers);

        savedReviewsAdapter = new SavedReviewsAdapter(getApplicationContext(), playerReviews);
        listView.setAdapter(savedReviewsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File myFile = new File(playerReviews.get(position).getFile());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri pdfURI = FileProvider.getUriForFile(SavedPlayerProfiles.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        myFile);
                intent.setDataAndType(pdfURI, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        });


    }
}

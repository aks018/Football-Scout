package aviee.footballscout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import aviee.footballscout.adapter.MainMenuAdapter;
import aviee.footballscout.pojo.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences();
    }

    public void goToSettings(View view) {
        Intent i = new Intent(this, MyPreferencesActivity.class);
        startActivity(i);
    }

    public void myPreferences(){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strUserName = SP.getString("username", "NA");
        boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
        String downloadType = SP.getString("downloadType","1");
    }

    public void goToNewPlayerProfileSetup(View view) {
        Intent intent = new Intent(this, NewPlayerProfileName.class);
        startActivity(intent);
    }
}

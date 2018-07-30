package aviee.footballscout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import aviee.footballscout.adapter.ExpandableListAdapter;


public class NewPlayerProfilePosition extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_position);

        expListView = (ExpandableListView) findViewById(R.id.expandedListView);

        prepareData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

    }

    public void prepareData() {

        listDataChild = new HashMap<String, List<String>>();

        String[] formations = getIntent().getStringArrayExtra("selectedFormations");
        listDataHeader = Arrays.asList(formations);

        for (int i = 0; i < listDataHeader.size(); i++) {
            listDataChild.put(listDataHeader.get(i), Arrays.asList(getResources().getStringArray(R.array.positionperformation)));
        }


    }
}

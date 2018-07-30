package aviee.footballscout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NewPlayerProfileFormation extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    SharedPreferences SP;
    String currentNewPlayerName;
    Set<String> selectedSetFormations;
    String selectedFormations;
    String playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_formation);

        listView = (ListView) findViewById(R.id.listViewPlayerFormation);
        String[] formations = getResources().getStringArray(R.array.formations);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, formations);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        currentNewPlayerName = getResources().getString(R.string.currentNewPlayerName);
        playerName = SP.getString(currentNewPlayerName, "");
        selectedFormations = getResources().getString(R.string.currentNewPlayerName);
        selectedSetFormations = SP.getStringSet(selectedFormations, new HashSet<String>());

    }



    public void cancelForm(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Cancel New Player Profile?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NewPlayerProfileFormation.this, MainActivity.class);
                        startActivity(intent);
                        SP.edit().putString("currentNewPlayerName", "");
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void nextPage(View view) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();

        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add formation if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        if (selectedItems.size() <= 0) {
            Toast.makeText(getApplicationContext(), "Please select at least one formation.", Toast.LENGTH_LONG).show();
            return;
        }

        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        Intent intent = new Intent(getApplicationContext(),
                NewPlayerProfilePosition.class);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedFormations", outputStrArr);
        b.putString("playerName", b.getString("playerName"));
        SP.edit().putStringSet(selectedFormations, new HashSet<String>(Arrays.asList(outputStrArr)));

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        startActivity(intent);

    }

}

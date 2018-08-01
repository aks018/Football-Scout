package aviee.footballscout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aviee.footballscout.adapter.ConstantManager;
import aviee.footballscout.adapter.MyCategoriesExpandableListAdapter;
import aviee.footballscout.pojo.DataItem;
import aviee.footballscout.pojo.Pair;

public class NewPlayerProfilePosition extends AppCompatActivity {

    private ExpandableListView lvCategory;

    private ArrayList<DataItem> arCategory;
    private ArrayList<SubCategoryItem> arSubCategory;
    private ArrayList<ArrayList<SubCategoryItem>> arSubCategoryFinal;

    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    SharedPreferences SP;


    String playerName;
    String[] selectedFormations;

    String TAG = "NewPlayerProfilePosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_position);

        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        playerName = getIntent().getStringExtra("playerName");
        selectedFormations = getIntent().getStringArrayExtra("selectedFormations");

        setupReferences();
    }

    private void setupReferences() {

        lvCategory = findViewById(R.id.expandedListView);
        arCategory = new ArrayList<>();
        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();

        int i = 0;
        String[] formations = selectedFormations;
        int k = formations.length;

        for (; i < formations.length; i++) {
            k++;
            DataItem dataItem = new DataItem();
            dataItem.setCategoryId(Integer.toString(k));
            dataItem.setCategoryName(formations[i]);

            arSubCategory = new ArrayList<>();
            List<String> playerSpecificFormations = Arrays.asList(getResources().getStringArray(R.array.positionperformation));
            for (int j = 0; j < playerSpecificFormations.size(); j++) {
                k++;
                SubCategoryItem subCategoryItem = new SubCategoryItem();
                subCategoryItem.setCategoryId(String.valueOf(k));
                subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                subCategoryItem.setSubCategoryName(playerSpecificFormations.get(j));
                arSubCategory.add(subCategoryItem);
            }
            dataItem.setSubCategory(arSubCategory);
            arCategory.add(dataItem);
        }

        for (DataItem data : arCategory) {
            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> mapParent = new HashMap<String, String>();

            mapParent.put(ConstantManager.Parameter.CATEGORY_ID, data.getCategoryId());
            mapParent.put(ConstantManager.Parameter.CATEGORY_NAME, data.getCategoryName());

            int countIsChecked = 0;
            for (SubCategoryItem subCategoryItem : data.getSubCategory()) {

                HashMap<String, String> mapChild = new HashMap<String, String>();
                mapChild.put(ConstantManager.Parameter.SUB_ID, subCategoryItem.getSubId());
                mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subCategoryItem.getSubCategoryName());
                mapChild.put(ConstantManager.Parameter.CATEGORY_ID, subCategoryItem.getCategoryId());
                mapChild.put(ConstantManager.Parameter.IS_CHECKED, subCategoryItem.getIsChecked());

                if (subCategoryItem.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                    countIsChecked++;
                }
                childArrayList.add(mapChild);
            }

            if (countIsChecked == data.getSubCategory().size()) {
                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
            } else {
                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            }

            mapParent.put(ConstantManager.Parameter.IS_CHECKED, data.getIsChecked());
            childItems.add(childArrayList);
            parentItems.add(mapParent);
        }

        ConstantManager.parentItems = parentItems;
        ConstantManager.childItems = childItems;

        myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapter(this, parentItems, childItems, false);
        lvCategory.setAdapter(myCategoriesExpandableListAdapter);
    }

    public void cancelForm(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Cancel New Player Profile?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NewPlayerProfilePosition.this, MainActivity.class);
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
        HashMap<Integer, String> formationsMap = new HashMap<>();
        for (int i = 0; i < parentItems.size(); i++) {
            HashMap<String, String> map = parentItems.get(i);
            String formationName = "";
            for (String key : map.keySet()) {
                if (key.equals("category_name")) {
                    formationName = map.get(key);
                    formationsMap.put(i, formationName);
                }
            }
        }
        ArrayList<Pair> selectedValues = new ArrayList<>();
        for (int i = 0; i < childItems.size(); i++) {
            ArrayList<HashMap<String, String>> list = childItems.get(i);
            for (Map<String, String> entry : list) {
                String subCatName = "";
                for (String key : entry.keySet()) {
                    if (key.equals("sub_category_name")) {
                        subCatName = entry.get(key);
                    }
                    if (key.equals("is_checked")) {
                        if (entry.get(key).equals(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                            Pair newPair = new Pair(subCatName, formationsMap.get(i));
                            selectedValues.add(newPair);
                        }
                    }
                }
            }
        }

        if (selectedValues.size() == 0) {
            Toast.makeText(getApplicationContext(), "Please select at least one position", Toast.LENGTH_LONG).show();
            return;
        }

        Bundle b = new Bundle();
        b.putStringArray("selectedFormations", selectedFormations);
        b.putString("playerName", playerName);
        b.putSerializable("selectedPositions", selectedValues);

        Intent intent = new Intent(this, NewPlayerProfileReview.class);
        intent.putExtras(b);
        startActivity(intent);

    }


}
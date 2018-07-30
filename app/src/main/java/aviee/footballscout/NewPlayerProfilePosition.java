package aviee.footballscout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import aviee.footballscout.adapter.ConstantManager;
import aviee.footballscout.adapter.MyCategoriesExpandableListAdapter;
import aviee.footballscout.pojo.DataItem;

public class NewPlayerProfilePosition extends AppCompatActivity {

    private ExpandableListView lvCategory;

    private ArrayList<DataItem> arCategory;
    private ArrayList<SubCategoryItem> arSubCategory;
    private ArrayList<ArrayList<SubCategoryItem>> arSubCategoryFinal;

    private ArrayList<HashMap<String, String>> parentItems;
    private ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_position);

        setupReferences();
    }

    private void setupReferences() {

        lvCategory = findViewById(R.id.expandedListView);
        arCategory = new ArrayList<>();
        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();

        int i = 0;
        String[] formations = getIntent().getStringArrayExtra("selectedFormations");
        int k = formations.length;

        for (; i < formations.length; i++) {
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


        Log.d("TAG", "setupReferences: " + arCategory.size());

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
}
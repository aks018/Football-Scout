package aviee.footballscout.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aviee.footballscout.R;
import aviee.footballscout.pojo.MenuItem;

public class MainMenuAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<MenuItem> menuItems;
    String TAG = "MainMenuAdapter";

    //Constructor for MusicBaseAdapter passing in the context of the activity using the adapter and the arraylist holding the music objects
    public MainMenuAdapter(Context mainActivity, List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        TextView dummyTextView = new TextView(context);
        dummyTextView.setText(menuItems.get(position).getName());
        return dummyTextView;
    }

}

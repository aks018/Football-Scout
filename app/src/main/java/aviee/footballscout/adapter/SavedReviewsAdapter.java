package aviee.footballscout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aviee.footballscout.R;
import aviee.footballscout.pojo.PlayerReview;

public class SavedReviewsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<PlayerReview> playerReviews;

    String TAG = "SavedReviewsAdapter";

    public SavedReviewsAdapter(Context mainActivity, List<PlayerReview> playerReviews) {
        this.playerReviews = playerReviews;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return playerReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return playerReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        //Inflate the row view with layout created to store results
        rowView = inflater.inflate(R.layout.saved_reviews_adapter_layout, parent, false);
        holder.playerName = (TextView) rowView.findViewById(R.id.savedReviewPlayerName);

        holder.playerName.setText(playerReviews.get(position).getName());
        return rowView;
    }

    public class Holder {
        TextView playerName;

    }


}

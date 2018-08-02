package aviee.footballscout.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import aviee.footballscout.pojo.PlayerReview;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FootballScout.db";
    public static final String CONTACTS_TABLE_NAME = "playerreview";
    public static final String PLAYER_COLUMN_ID = "id";
    public static final String PLAYER_COLUMN_NAME = "name";
    public static final String PLAYER_COLUMN_CLUB = "club";
    public static final String PLAYER__COLUMN_FILE = "file";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table playerreview " +
                        "(id integer primary key, name text,club text,file text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


    /*public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from playerreview", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }*/

    public long insertReview(PlayerReview playerReview) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        contentValues.put("name", playerReview.getName());
        contentValues.put("club", playerReview.getClub());
        contentValues.put("file", playerReview.getFile());

        // insert row
        long id = db.insert("playerreview", null, contentValues);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<PlayerReview> getAllPlayerReviews() {
        List<PlayerReview> playerReviews = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + "playerreview";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlayerReview playerReview = new PlayerReview();
                playerReview.setName(cursor.getString(cursor.getColumnIndex(PLAYER_COLUMN_NAME)));
                playerReview.setClub(cursor.getString(cursor.getColumnIndex(PLAYER_COLUMN_CLUB)));
                playerReview.setFile(cursor.getString(cursor.getColumnIndex(PLAYER__COLUMN_FILE)));

                playerReviews.add(playerReview);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return playerReviews;
    }
}

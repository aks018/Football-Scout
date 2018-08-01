package aviee.footballscout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aviee.footballscout.pojo.Pair;

public class NewPlayerProfileReview extends AppCompatActivity {
    SharedPreferences SP;

    String playerName;
    String[] selectedFormations;
    ArrayList<Pair> selectedPositions;

    EditText playerDescription;

    String TAG = "NewPlayerProfileReview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_review);

        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String currentNewPlayerName = getResources().getString(R.string.currentNewPlayerName);
        playerName = getIntent().getStringExtra("playerName");
        selectedFormations = getIntent().getStringArrayExtra("selectedFormations");
        selectedPositions = (ArrayList<Pair>) getIntent().getSerializableExtra("selectedPositions");

        playerDescription = (EditText) findViewById(R.id.playerReviewEditText);

    }

    public void cancelForm(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Cancel New Player Profile?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NewPlayerProfileReview.this, MainActivity.class);
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
        String playerDescriptionStr = playerDescription.getText().toString();
        boolean error = false;
        if (playerDescriptionStr.length() < 3) {
            Toast.makeText(getApplicationContext(), "Please enter a valid description", Toast.LENGTH_LONG).show();
            return;
        }
        Random r = new Random();
        int fileNumber = r.nextInt();
        Document document = new Document();
        String outPath = Environment.getExternalStorageDirectory() + "/FootballScout/PlayerReviews/" + playerName + "_" + Integer.toString(fileNumber) + ".pdf";
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                PdfWriter.getInstance(document, new FileOutputStream(outPath));
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            PdfWriter.getInstance(document, new FileOutputStream(outPath));
            document.open();
            document.add(new Paragraph(playerName));
            for (int i = 0; i < selectedPositions.size(); i++) {
                Pair p = selectedPositions.get(i);
                document.add(new Paragraph(p.getFormation().toString() + "- " + p.getPosition()));
            }
            document.add(new Paragraph(playerDescriptionStr));
            document.close();
        } catch (DocumentException e) {
            Log.e(TAG, e.toString());
            error = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            error = true;
        }
        if (error)
            Toast.makeText(getApplicationContext(), "Player Review Not Successfully Created.", Toast.LENGTH_LONG).show();

        /*Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(outPath));
        intent.setType("application/pdf");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Unable to open Player Review", Toast.LENGTH_LONG).show();
        }*/
        Toast.makeText(getApplicationContext(), "Player Review Successfully created", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        SP.edit().putString("currentNewPlayerName", "");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                return;
        }
    }
}

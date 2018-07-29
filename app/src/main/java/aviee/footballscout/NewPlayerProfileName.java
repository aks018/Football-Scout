package aviee.footballscout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class NewPlayerProfileName extends AppCompatActivity {

    EditText playerNameEditText;
    TextInputLayout textInputLayoutPlayerName;
    SharedPreferences SP;
    String currentNewPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_profile_name);

        playerNameEditText = (EditText) findViewById(R.id.playerNameEditText);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        textInputLayoutPlayerName = (TextInputLayout) findViewById(R.id.textInputLayoutPlayerName);
        currentNewPlayerName = SP.getString(getResources().getString(R.string.currentNewPlayerName), "");
        if (!currentNewPlayerName.equals("") || currentNewPlayerName != null) {
            playerNameEditText.setText(currentNewPlayerName);
        }

        playerNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 45) {
                    textInputLayoutPlayerName.setError(null);
                } else {
                    textInputLayoutPlayerName.setError("Player name too long.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void cancelForm(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Cancel New Player Profile?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NewPlayerProfileName.this, MainActivity.class);
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
        String playerName = playerNameEditText.getText().toString();

        if (playerName.equals("")) {
            textInputLayoutPlayerName.setError(null);
            textInputLayoutPlayerName.setError("Please enter a player name.");
            return;
        } else if (playerName.length() > 45) {
            textInputLayoutPlayerName.setError(null);
            textInputLayoutPlayerName.setError("Player name too long.");
            return;
        }

        Intent intent = new Intent(this, NewPlayerProfileFormation.class);
        intent.putExtra("playerName", playerName);
        startActivity(intent);

        SP.edit().putString(currentNewPlayerName, playerName);
    }
}

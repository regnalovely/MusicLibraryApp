package fr.dev.shyn;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int CHECK_CODE = 0x1;
    private Button launchPrompt, displayMusic;

    private static final int REQUEST_CODE = 1;
    private EditText editTextOutput;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchPrompt = (Button) findViewById(R.id.launchPrompt);
        launchPrompt.setOnClickListener(promptListener);

        checkTTS();


        displayMusic = (Button) findViewById(R.id.displayMusic);
        displayMusic.setOnClickListener(displayListener);
        editTextOutput = (EditText) findViewById(R.id.textOutput);
        textView = (TextView) findViewById(R.id.textApp);
    }
    public void GoToListActivity(){
        startActivity(new Intent(getApplicationContext(), ListActivity.class));
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    View.OnClickListener displayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GoToListActivity();
        }
    };

    View.OnClickListener promptListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            promptSpeechInput();
        }
    };

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Vous pouvez parler");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e){
            Toast.makeText(this, "Désolé, votre appareil ne supporte pas d'entrée vocale...", Toast.LENGTH_SHORT).show();
            Intent intentInstall = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.voicesearch"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String text = "";
                for(String  result : results){
                    text += result + "\n";
                }

                doCommande(results.get(0));
            } else {
                Toast.makeText(this, "Opération echouée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void doCommande(String commande){
        switch (commande){
            case "suivant":
                textView.setText("Musique suivante");
                break;
            case "précédent":
                textView.setText("Musique précédente");
                break;
            case "arrêter":
                textView.setText("Musique arrêter");
                break;
            case "stop":
                textView.setText("Musique arrêter");
                break;
            case "continuer":
                textView.setText("Musique écouter");
                break;
            case "lire":
                textView.setText("Musique écouter");
                break;
            default:
                textView.setText("Commande inconnu: "+commande);
                break;
        }
    }
}

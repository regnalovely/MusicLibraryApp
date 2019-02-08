package fr.dev.apptwo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.IBinder;
import android.provider.MediaStore;
=======
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private final int CHECK_CODE = 0x1;
    private CardView btnRecord, displayMusic;

    private static final int REQUEST_CODE = 1;
    private EditText editTextOutput;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = (CardView) findViewById(R.id.btnRecord);
        btnRecord.setOnClickListener(promptListener);

        displayMusic = (CardView) findViewById(R.id.displayMusic);
        displayMusic.setOnClickListener(displayListener);
        editTextOutput = (EditText) findViewById(R.id.textOutput);
        //textView = (TextView) findViewById(R.id.textApp);
    }

    public void GoToListActivity(){
        startActivity(new Intent(getApplicationContext(), ListActivity.class));
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
<<<<<<< HEAD
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicService = null;
        super.onDestroy();
    }

    public void getMusicList(){
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int columnIndex = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

            //add songs to list
            do {
                String imagePath = musicCursor.getString(columnIndex);
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                musicList.add(new Music(thisId, thisTitle, thisArtist, imagePath));
            }
            while (musicCursor.moveToNext());
        }
    }

    @Override
    public void start() {
        musicService.startPlayer();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicService != null && musicBound && musicService.isPlaying()){
            return musicService.getDuration();
        } else {
            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {
        if(musicService != null && musicBound && musicService.isPlaying()){
            return musicService.getPosition();
        } else {
            return 0;
=======
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17
        }
    }


}

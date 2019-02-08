package fr.dev.apptwo;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    Button btnNext, btnPrevious, btnPause;
    TextView musicTextLabel;
    SeekBar seekBar;
    String mName;
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> musics;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnNext = (Button) findViewById(R.id.next);
        btnPrevious = (Button) findViewById(R.id.previous);
        btnPause = (Button) findViewById(R.id.pause);
        musicTextLabel = (TextView) findViewById(R.id.musicLabel);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        updateSeekBar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while(currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        musics = (ArrayList) bundle.getParcelableArrayList("musics");
        mName = musics.get(position).getName().toString();

        String musicName = i.getStringExtra("musicName");
        musicTextLabel.setText(musicName);
        musicTextLabel.setSelected(true);

        position = bundle.getInt("pos", 0);

        Uri uri = Uri.parse(musics.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        getSupportActionBar().setTitle("Jouer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                } else {
                    btnPause.setBackgroundResource(R.drawable.icon_pause);
                    mediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%musics.size());
                Uri uri = Uri.parse(musics.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mName = musics.get(position).getName().toString();
                musicTextLabel.setText(mName);
                mediaPlayer.start();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0) ? (musics.size()-1) : (position-1);
                Uri uri = Uri.parse(musics.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mName = musics.get(position).getName().toString();
                musicTextLabel.setText(mName);
                mediaPlayer.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

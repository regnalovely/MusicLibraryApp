package fr.dev.apptwo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {

    private MediaPlayer mediaPlayer;
    private ArrayList<Music> musics;
    private int position;
    private final IBinder musicBinder = new MusicBinder();
    private String musicTitre = "";
    private static final int NOTIFY_ID = 1;
    private boolean shuffle = false;
    private Random random;

    public void onCreate(){
        super.onCreate();
        position = 0;
        mediaPlayer = new MediaPlayer();
        initMusicPlayer();
        random = new Random();
    }

    public void initMusicPlayer(){
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }
    public void setList(ArrayList<Music> musics){
        this.musics = musics;
    }

    public void jouer(){
        mediaPlayer.reset();
        Music music = musics.get(position);
        musicTitre = music.getTitre();
        long currentMusic = music.getId();
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentMusic);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (Exception e){
            Log.e("MUSIC SERVICE", "Erreur de configuration du data source", e);
        }

        mediaPlayer.prepareAsync();
    }

    public void playPrevious(){
        position--;
        if(position < 0) {
            position = musics.size() - 1;
            jouer();
        }
    }

    public void playNext(){
        if(shuffle){
            int newMusic = position;
            while (newMusic == position){
                newMusic = random.nextInt(musics.size());
            }
            position = newMusic;
        } else {
            position++;
            if (position > musics.size()) {
                position = 0;
            }
        }
        jouer();
    }

    public void setShuffle(){
        shuffle = !shuffle;
    }

    public void setMusic(int musicIndex){
        position = musicIndex;
    }

    public int getPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer(){
        mediaPlayer.pause();
    }

    public void seek(int position){
        mediaPlayer.seekTo(position);
    }

    public void startPlayer(){
        mediaPlayer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mediaPlayer.getCurrentPosition() > 0){
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.android_music_player_play)
                .setTicker(musicTitre)
                .setOngoing(true)
                .setContentTitle("Entrain de jouer")
                .setContentText(musicTitre);
        Notification notification = builder.build();

        startForeground(NOTIFY_ID, notification);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        //
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}

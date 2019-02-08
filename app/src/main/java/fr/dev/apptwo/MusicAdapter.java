package fr.dev.apptwo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
<<<<<<< HEAD
import android.graphics.Color;
=======
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    private ArrayList<Music> musics;
    private LayoutInflater musicInf;

    public MusicAdapter(Context context, ArrayList<Music> musicList) {
        musicInf = LayoutInflater.from(context);
        musics = musicList;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        LinearLayout musicLayout = (LinearLayout)musicInf.inflate(R.layout.music, parent, false);

        //get title and artist views
        TextView songView = (TextView)musicLayout.findViewById(R.id.music_titre);
        TextView artistView = (TextView)musicLayout.findViewById(R.id.music_artiste);
<<<<<<< HEAD
        ImageView imageAlbum = (ImageView)musicLayout.findViewById(R.id.music_image);
=======
        ImageView pochetteView = (ImageView)musicLayout.findViewById(R.id.music_pochette) ;
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17

        //get song using position
        Music currentMusic = musics.get(position);


        //get title and artist strings
        songView.setText(currentMusic.getTitre());
        artistView.setText(currentMusic.getArtiste());

<<<<<<< HEAD
        //set image's song
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(currentMusic.getImage());
=======

        //set image's song
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(currentMusic.getPochette());
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17
        try{
            byte[] art = mediaMetadataRetriever.getEmbeddedPicture();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap image = BitmapFactory.decodeByteArray(art, 0, art.length, options);
<<<<<<< HEAD
            imageAlbum.setImageBitmap(image);

        } catch (Exception e){
            imageAlbum.setImageResource(R.drawable.music_cover);
=======
            pochetteView.setImageBitmap(image);

        } catch (Exception e){
            pochetteView.setImageResource(R.drawable.music_cover);
>>>>>>> bcdc3b659e68b815ab14c83e076098e50100dd17
        }

        //set position as tag
        musicLayout.setTag(position);
        return musicLayout;
    }
}

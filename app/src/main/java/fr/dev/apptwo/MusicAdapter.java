package fr.dev.apptwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        LinearLayout musicLayout = (LinearLayout)musicInf.inflate
                (R.layout.music, parent, false);

        //get title and artist views
        TextView songView = (TextView)musicLayout.findViewById(R.id.music_titre);
        TextView artistView = (TextView)musicLayout.findViewById(R.id.music_artiste);

        //get song using position
        Music currentMusic = musics.get(position);

        //get title and artist strings
        songView.setText(currentMusic.getTitre());
        artistView.setText(currentMusic.getArtiste());

        //set position as tag
        musicLayout.setTag(position);
        return musicLayout;
    }
}

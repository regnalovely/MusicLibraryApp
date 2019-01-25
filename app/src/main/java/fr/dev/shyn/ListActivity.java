package fr.dev.shyn;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView musicListView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        musicListView = (ListView) findViewById(R.id.musicListView);
        runtimePermission();
    }

    public void runtimePermission(){
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findMusic(File file){
        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findMusic(singleFile));
            } else {
                if(singleFile.getName().endsWith(".mp3")){
                    arrayList.add(singleFile);
                }
            }
        }

        return arrayList;
    }

    public void display(){
        final ArrayList<File> musics = findMusic(Environment.getExternalStorageDirectory());
        items = new String[musics.size()];

        for (int  i=0; i< musics.size(); i++){
            items[i] = musics.get(i).getName().toString().replace(".mp3", "");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        musicListView.setAdapter(adapter);

        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String musicName = musicListView.getItemAtPosition(position).toString();

                startActivity(new Intent(getApplicationContext(), PlayActivity.class)
                .putExtra("musics", musics).putExtra("musicName",musicName).putExtra("pos", position));
            }
        });
    }
}

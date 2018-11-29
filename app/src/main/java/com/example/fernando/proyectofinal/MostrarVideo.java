package com.example.fernando.proyectofinal;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class MostrarVideo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_video);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        String valor = getIntent().getExtras().getString("Data");
        Uri path = Uri.parse(valor);

        videoView.setVideoURI(path);
        videoView.start();
    }
}

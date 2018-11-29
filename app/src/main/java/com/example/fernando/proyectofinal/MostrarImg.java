package com.example.fernando.proyectofinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

public class MostrarImg extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_img);
        String valor = getIntent().getExtras().getString("Data");
        Toast.makeText(this, valor, Toast.LENGTH_LONG).show();
        ImageView entry = (ImageView) findViewById(R.id.imageView);
        Bitmap bMap = BitmapFactory.decodeFile(valor);
        entry.setImageBitmap(bMap);
    }

}

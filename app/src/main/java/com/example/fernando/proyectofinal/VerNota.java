package com.example.fernando.proyectofinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.proyectofinal.R;
import com.example.fernando.proyectofinal.bd.DAOnota;
import com.example.fernando.proyectofinal.bd.Nota;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VerNota extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_nota);
        int valor = Integer.parseInt(getIntent().getExtras().getString("data"));
        DAOnota dn = new DAOnota(this);
        Nota n = dn.getOneNote(valor);

        TextView txt = findViewById(R.id.txtTitulo);
        txt.setText(n.getTitulo());

        EditText mEdit = (EditText) findViewById(R.id.txtMensaje);
        mEdit.setText(n.getMensaje());
        mEdit.setEnabled(false);

        lista = findViewById(R.id.contenedorMultimediaMuestra);
        listaFotos = n.getImagenes();
        listaVideos = n.getVideos();
        llenarMultimedia();

        //Toast.makeText(this, valor + "", Toast.LENGTH_SHORT).show();
    }

    ArrayList<Nodo> listaFotos = new ArrayList();
    ArrayList<Nodo> listaVideos = new ArrayList();
    LinearLayout lista;
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void llenarMultimedia(){
        lista.removeAllViews();
        for (Nodo n:listaFotos){
            Button b = new Button(getApplicationContext());
            b.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.fotos));
            b.setText(n.getNombre());
            b.setMaxWidth(20);
            b.setOnClickListener(new VerNota.oyente());
            lista.addView(b);
        }
        for (Nodo n:listaVideos){
            Button b = new Button(getApplicationContext());
            Drawable d = getApplicationContext().getResources().getDrawable(R.drawable.videos);
            d.setBounds(20,20,20,20);
            b.setBackground(d);
            b.setText(n.getNombre());
            b.setOnClickListener(new VerNota.OyenteVideo());
            lista.addView(b);
        }
    }

    public class oyente implements View.OnClickListener{

        @Override
        public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MostrarImg.class);
                        intent.putExtra("Data", (Environment.getExternalStorageDirectory() + File.separator +
                                DIRECTORIO_IMAGEN + File.separator+((Button)view).getText()));
                        startActivity(intent);
        }
    }

    public class OyenteVideo implements View.OnClickListener{
        @Override
        public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), MostrarVideo.class);
                        intent.putExtra("Data", (Environment.getExternalStorageDirectory() + File.separator +
                                DIRECTORIO_IMAGEN + File.separator + ((Button) view).getText()));
                        startActivity(intent);
        }
    }

}

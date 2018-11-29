package com.example.fernando.proyectofinal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fernando.proyectofinal.bd.DAOnota;
import com.example.fernando.proyectofinal.bd.Nota;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditarNota extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_lista);
        int valor = Integer.parseInt(getIntent().getExtras().getString("data"));
        DAOnota dn = new DAOnota(this);
        n = dn.getOneNote(valor);

        en = findViewById(R.id.edtTituloEdit);
        en.setText(n.getTitulo());

        enM = findViewById(R.id.edtMensajeEdit);
        enM.setText(n.getMensaje());

        lista = findViewById(R.id.contenedorMultimediaEdit);
        listaFotos = n.getImagenes();
        listaVideos = n.getVideos();
        llenarMultimedia();

        chbRecordatorio = findViewById(R.id.chbRecordatorioEdit);

    }
    private Nota n;
    private EditText en;
    private EditText enM;
    private CheckBox chbRecordatorio;

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
            b.setOnClickListener(new EditarNota.oyente());
            lista.addView(b);
        }
        for (Nodo n:listaVideos){
            Button b = new Button(getApplicationContext());
            Drawable d = getApplicationContext().getResources().getDrawable(R.drawable.videos);
            d.setBounds(20,20,20,20);
            b.setBackground(d);
            b.setText(n.getNombre());
            b.setOnClickListener(new EditarNota.OyenteVideo());
            lista.addView(b);
        }
    }

    public class oyente implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            final View v = view;
            final CharSequence[] items = {"Ver", "Eliminar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(EditarNota.this);
            builder.setTitle("Opciones");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item].equals("Ver")){
                        Intent intent = new Intent(getApplicationContext(), MostrarImg.class);
                        intent.putExtra("Data", (Environment.getExternalStorageDirectory() + File.separator +
                                DIRECTORIO_IMAGEN + File.separator+((Button)v).getText()));
                        startActivity(intent);
                    }else{
                        eliminar(((Button)v).getText().toString());
                        llenarMultimedia();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            /*Intent intent = new Intent(view.getContext(), MostrarImg.class);
            intent.putExtra("Data", (Environment.getExternalStorageDirectory() + File.separator +
                    DIRECTORIO_IMAGEN + File.separator+((Button)view).getText()));
            startActivity(intent);*/

        }
    }

    public void eliminar(String dir){
        for(int i= 0; i < listaFotos.size(); i++){
            if(listaFotos.get(i).getNombre().equals(dir)){
                listaFotos.remove(i);
                break;
            }
        }

        for(int i= 0; i < listaVideos.size(); i++){
            if(listaVideos.get(i).getNombre().equals(dir)){
                listaVideos.remove(i);
                break;
            }
        }

    }

    public class OyenteVideo implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            final View v = view;
            final CharSequence[] items = {"Ver", "Eliminar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(EditarNota.this);
            builder.setTitle("Opciones");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item].equals("Ver")){
                        Intent intent = new Intent(v.getContext(), MostrarVideo.class);
                        intent.putExtra("Data", (Environment.getExternalStorageDirectory() + File.separator +
                                DIRECTORIO_IMAGEN + File.separator + ((Button) v).getText()));
                        startActivity(intent);
                    }else{
                        eliminar(((Button)v).getText().toString());
                        llenarMultimedia();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void tomarVideosEdit(View view){
        if (
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(EditarNota.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        videos();

    }

    public void tomarFotosEdit(View view){
        if (
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(EditarNota.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }


        fotos();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode ==20 && resultCode == RESULT_OK)
        {
            listaFotos.add(new Nodo(nombre,pathImg));
            fotos();
        }else if(requestCode == 10 && resultCode == RESULT_OK)
        {
            listaVideos.add(new Nodo(nombre,pathVideos));
            videos();
        }else{
            llenarMultimedia();
        }
    }

    private String pathImg;
    private String nombre;
    public void fotos(){
        String archivo;
        File file;
        File fileImagen;
        try
        {
            file = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
            boolean estaCreada = file.exists();
            if (estaCreada == false){
                estaCreada = file.mkdir();
            }
            if (estaCreada == true){
                Long consecutivo = System.currentTimeMillis() / 1000;
                nombre = consecutivo.toString() + ".jpg";
                pathImg = Environment.getExternalStorageDirectory() + File.separator +
                        DIRECTORIO_IMAGEN + File.separator + nombre;
                fileImagen = new File(pathImg);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
                startActivityForResult(intent, 20);
            }
        } catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage() + " $$$$$", Toast.LENGTH_LONG).show();
        }
    }

    private String pathVideos;

    public void videos(){
        String archivo;
        File file;
        File fileImagen;
        try
        {
            file = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
            boolean estaCreada = file.exists();
            if (estaCreada == false){
                estaCreada = file.mkdir();
            }
            if (estaCreada == true){
                Long consecutivo = System.currentTimeMillis() / 1000;
                nombre = consecutivo.toString() + ".mp4";
                pathVideos = Environment.getExternalStorageDirectory() + File.separator +
                        DIRECTORIO_IMAGEN + File.separator + nombre;
                fileImagen = new File(pathVideos);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
                startActivityForResult(intent, 10);
            }
        } catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void actualizarNota(View view){

        /*for (String s:listaFotos){
            Log.i("DatosIMG", s);
        }
        Log.i("r////","***************************");
        for (String s:listaVideos){
            Log.i("DatosVID", s);
        }
        Log.i("r////","***************************");*/
        /*
        DAOnota dn = new DAOnota(view.getContext());
        dn.add(new Nota(0,"Holas",new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),
                new Date(), false, "hbak.jb,j Dqbudq.kBHJQBDLQBDAKBJ,BABJHADVEVJH,W"));*/
        DAOnota dn = new DAOnota(view.getContext());
        long n = dn.update(new Nota(this.n.getId(),en.getText().toString(),listaVideos,listaFotos,new ArrayList<Nodo>(),
                new Date(), chbRecordatorio.isChecked(), enM.getText().toString()));
        AdaptadorListView miAdapter = new AdaptadorListView(getApplicationContext(),dn.getAll());
        Class_list_note.lv.setAdapter(miAdapter);
        this.onBackPressed();
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}

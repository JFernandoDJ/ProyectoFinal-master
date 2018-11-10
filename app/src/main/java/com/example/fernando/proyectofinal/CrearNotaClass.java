package com.example.fernando.proyectofinal;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fernando.proyectofinal.bd.DAOnota;
import com.example.fernando.proyectofinal.bd.Nota;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearNotaClass extends AppCompatActivity {

    LinearLayout lista;
    EditText titulo,mensaje;
    Button btnHora, btnFecha;
    CheckBox chbRecordatorio;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_nota);
        lista = findViewById(R.id.contenedorMultimedia);
        titulo = findViewById(R.id.edtTitulo);
        titulo.setFocusable(false);
        titulo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                titulo.setFocusableInTouchMode(true);
                return false;
            }
        });

        mensaje = findViewById(R.id.edtMensaje);
        mensaje.setFocusable(false);
        mensaje.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mensaje.setFocusableInTouchMode(true);
                return false;
            }
        });

        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());

        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnTiempo);
        btnFecha.setVisibility(View.INVISIBLE);
        btnHora.setVisibility(View.INVISIBLE);

        chbRecordatorio = findViewById(R.id.chbRecordatorio);

        resetarValores();

    }

    public void mostrar(View view){
        if(chbRecordatorio.isChecked()) {
            btnFecha.setVisibility(View.VISIBLE);
            btnHora.setVisibility(View.VISIBLE);
            Animation ani = AnimationUtils.loadAnimation(view.getContext(), R.anim.animar_recordatorio_botones);
            btnFecha.startAnimation(ani);
            btnHora.startAnimation(ani);

        }else {
            Animation ani = AnimationUtils.loadAnimation(view.getContext(), R.anim.animar_recordatorio_salida);
            btnFecha.startAnimation(ani);
            btnHora.startAnimation(ani);
            btnFecha.setVisibility(View.INVISIBLE);
            btnHora.setVisibility(View.INVISIBLE);
        }
    }


    private int ano,dia,mes, hora, minuto;
    private int diaO, mesO, anoO, horaO, minutosO;

    public void establecerFecha(View view){
        Calendar c = Calendar.getInstance();
        dia = Calendar.DAY_OF_MONTH;
        mes = Calendar.MONDAY;
        ano = Calendar.YEAR;
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                diaO = i;
                mesO = i1;
                anoO = i2;
            }
        },dia,mes,ano);
        dpd.show();
    }

    public void establecerHora(View view){
        Calendar c = Calendar.getInstance();
        hora = Calendar.HOUR_OF_DAY;
        minuto = Calendar.MINUTE;
        TimePickerDialog dpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                horaO = i;
                mesO = i1;
            }
        }, hora, minuto, false);
        dpd.show();
    }

    public void crearNota(View view){

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
        dn.add(new Nota(0,titulo.getText().toString(),new ArrayList<Nodo>(),listaVideos,listaFotos,
                new Date(), chbRecordatorio.isChecked(), mensaje.getText().toString()));
        List<Nota> list = dn.getAll();
        /*for (int i = 0; i < list.size(); i++){

            Log.i("Nota" + i, list.get(i).toString());
        }*/
        resetarValores();
        this.onBackPressed();
    }

    public void resetarValores(){
        diaO = mesO = anoO = horaO = minutosO = -1;
    }

    public void tomarVideos(View view){
        if (
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(CrearNotaClass.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        videos();

    }

    public void tomarFotos(View view){
        if (
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(CrearNotaClass.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }


        fotos();

    }

    ArrayList<Nodo> listaFotos = new ArrayList();
    ArrayList<Nodo> listaVideos = new ArrayList();

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void llenarMultimedia(){
        lista.removeAllViews();
        for (Nodo n:listaFotos){
            Button b = new Button(getApplicationContext());
            b.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.fotos));
            b.setText(n.getNombre());
            b.setMaxWidth(20);
            lista.addView(b);
        }
        for (Nodo n:listaVideos){
            Button b = new Button(getApplicationContext());
            Drawable d = getApplicationContext().getResources().getDrawable(R.drawable.videos);
            d.setBounds(20,20,20,20);
            b.setBackground(d);
            b.setText(n.getNombre());
            lista.addView(b);
        }
    }

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
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
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}

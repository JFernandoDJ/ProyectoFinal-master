package com.example.fernando.proyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.proyectofinal.bd.DAOnota;
import com.example.fernando.proyectofinal.bd.Nota;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Class_list_note extends AppCompatActivity {

    static boolean presionado = false;
    EditText edT;
    static ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_note);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Chalk-hand-lettering-shaded_demo.ttf");
        TextView txtAux = findViewById(R.id.txtTitulo);
        txtAux.setTypeface(face);
        txtAux.setTextSize(32);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);

        final FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        Animation ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab3_rev_i);
        fab3.startAnimation(ani);
        fab3.setEnabled(false);

        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab1);
        ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_rev_i);
        fab2.startAnimation(ani);
        fab2.setEnabled(false);

        final FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab4_rev_i);
        fab4.startAnimation(ani);
        fab4.setEnabled(false);

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Camara",Toast.LENGTH_SHORT).show();
                meterBotones(fab2,fab3,fab4,fab);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Notas",Toast.LENGTH_SHORT).show();
                meterBotones(fab2,fab3,fab4,fab);
                Intent intent = new Intent(view.getContext(),CrearNotaClass.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Grabar Audio",Toast.LENGTH_SHORT).show();
                meterBotones(fab2,fab3,fab4,fab);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!presionado) {
                    sacarBotones(fab2,fab3,fab4,fab);
                }else{
                    meterBotones(fab2,fab3,fab4,fab);
                }
            }
        });
        DAOnota dn = new DAOnota(getApplicationContext());
        List<Nota> list = dn.getAll();
        for (int i = 0; i < list.size(); i++){
            Log.i("Nota" + i, list.get(i).getMensaje());
        }
        /*List<Nota> listN = new ArrayList<>();
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),false,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),true,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),false,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),true,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),false,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),true,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),false,""));
        listN.add(new Nota(0,"",new ArrayList<String>(),new ArrayList<String>(), new ArrayList<String>(), new Date(),true,""));
*/
        lv = findViewById(R.id.listViewDatos);
        AdaptadorListView miAdapter = new AdaptadorListView(getApplicationContext(),list);
        lv.setAdapter(miAdapter);

        /*lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Opciones");
                CharSequence[] opciones = {"Eliminar"};
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });*/

        edT = findViewById(R.id.edTxtBuscar);
        edT.setFocusable(false);
        edT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edT.setFocusableInTouchMode(true);
                return false;
            }
        });

        edT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DAOnota dn = new DAOnota(getApplicationContext());
                List<Nota> list = dn.getAll(charSequence+"");
                AdaptadorListView miAdapter = new AdaptadorListView(getApplicationContext(),list);
                lv.setAdapter(miAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DAOnota dn = new DAOnota(getApplicationContext());
        List<Nota> list = dn.getAll();
        AdaptadorListView miAdapter = new AdaptadorListView(getApplicationContext(),list);
        lv.setAdapter(miAdapter);
    }

        public void onClickFab1(View view) {
        //Toast.makeText(getApplicationContext(),"Notas",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,CrearNotaClass.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

public void sacarBotones(FloatingActionButton fab2, FloatingActionButton fab3, FloatingActionButton fab4,
                         FloatingActionButton fab){
    Animation ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov);
    fab2.startAnimation(ani);

    ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab3);
    fab3.startAnimation(ani);

    ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab4);
    fab4.startAnimation(ani);

    presionado = true;
    fab2.setEnabled(true);
    fab3.setEnabled(true);
    fab4.setEnabled(true);
    fab.setImageDrawable(getResources().getDrawable(R.drawable.cerrar));
}

    public void meterBotones(FloatingActionButton fab2, FloatingActionButton fab3, FloatingActionButton fab4,
                             FloatingActionButton fab){
        Animation ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_rev);
        fab2.startAnimation(ani);

        ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab3_rev);
        fab3.startAnimation(ani);

        ani = AnimationUtils.loadAnimation(fab.getContext(), R.anim.floating_button_mov_fab4_rev);
        fab4.startAnimation(ani);

        presionado = false;
        fab2.setEnabled(false);
        fab3.setEnabled(false);
        fab4.setEnabled(false);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.agregar));
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}

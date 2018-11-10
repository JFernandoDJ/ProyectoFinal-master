package com.example.fernando.proyectofinal;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Build;
        import android.support.annotation.RequiresApi;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.BaseAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.fernando.proyectofinal.bd.DAOnota;
        import com.example.fernando.proyectofinal.bd.Nota;

        import java.util.ArrayList;
        import java.util.List;

public class AdaptadorListView extends BaseAdapter {

    Context context;
    List<Nota> listaDatos;

    public AdaptadorListView(Context context, List<Nota> listaDatos) {
        this.context = context;
        this.listaDatos = listaDatos;
    }

    @Override
    public int getCount() {
        return listaDatos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaDatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaDatos.get(i).getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;
        final int j = i;
        LayoutInflater inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.item_list_view,null);
        ImageView iv = vista.findViewById(R.id.img_icon);
        TextView txt = vista.findViewById(R.id.txtT);
        ImageButton imgBtnEliminar = vista.findViewById(R.id.imgbtneliminar);
        ImageButton imgBtnEditar = vista.findViewById(R.id.imgbtneditar);
        txt.setText(listaDatos.get(i).getTitulo());
        try {
            if (listaDatos.get(i).isRecordatorio()) {
                iv.setBackground(viewGroup.getContext().getResources().getDrawable(R.drawable.notificacion));
            } else {
                iv.setBackground(viewGroup.getContext().getResources().getDrawable(R.drawable.notas_icon));
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VerNota.class);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), (listaDatos.get(j).getTitulo()), Toast.LENGTH_SHORT).show();
            }
        });
        imgBtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAOnota dnota = new DAOnota(view.getContext());
                dnota.delete(listaDatos.get(j).getId());
                List<Nota> list = dnota.getAll();
                AdaptadorListView miAdapter = new AdaptadorListView(view.getContext(), list);
                Class_list_note.lv.setAdapter(miAdapter);
                Toast.makeText(view.getContext(), "Eliminar", Toast.LENGTH_SHORT).show();
            }
        });
        imgBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditarNota.class);
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "Editar", Toast.LENGTH_SHORT).show();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,
               R.anim.animar_recordatorio_botones);
        vista.startAnimation(animation);
        posicionAnterior = i;

        return vista;
    }
    private int posicionAnterior = -1;

}

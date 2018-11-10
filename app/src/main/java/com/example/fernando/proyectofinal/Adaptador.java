package com.example.fernando.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Adaptador extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;
    AssetManager assetManager;
    String [] saludos={"Hola 1", "Hola 2", "Hola 3"};

    public Adaptador(Context cont) {
        context = cont;
    }

    @Override
    public int getCount() {
        return saludos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view;
        Typeface face=Typeface.createFromAsset(assetManager,"fonts/Chalk-hand-lettering-shaded_demo.ttf");

        if (position == 0){
            view = layoutInflater.inflate(R.layout.principal_slide_item,container,false);
            TextView textView = view.findViewById(R.id.textView2);
            textView.setTypeface(face);
            textView.setTextSize(70);
            textView.setText("Notedroid");
            Animation ani = AnimationUtils.loadAnimation(textView.getContext(),R.anim.anim_titulo);
            textView.startAnimation(ani);
            //----------------------------------------------
            Button btn = view.findViewById(R.id.btnIngresar);
            face=Typeface.createFromAsset(assetManager,"fonts/KGTenThousandReasons.ttf");
            btn.setTypeface(face);
            btn.setTextSize(12);
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(40);
            gd.setStroke(5, R.color.White);
            btn.setBackground(gd);
            ani = AnimationUtils.loadAnimation(btn.getContext(),R.anim.anim_ingresar);
            btn.startAnimation(ani);
        }else {
            view = layoutInflater.inflate(R.layout.slider_item,container,false);
        }
        //TextView textView = view.findViewById(R.id.textView);
        //textView.setText(saludos[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((RelativeLayout) object);
    }
}

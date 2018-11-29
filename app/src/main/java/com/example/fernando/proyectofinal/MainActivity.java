package com.example.fernando.proyectofinal;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlider;
    private LinearLayout mDot;
    private Adaptador adp;
    private TextView indicador[];
    AlarmManager am;
    private String CHANNEL_ID="CANALID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlider = findViewById(R.id.sliderViewPager);
        mDot = findViewById(R.id.nextLayout);

        adp = new Adaptador(this);
        adp.assetManager=getAssets();
        mSlider.setAdapter(adp);
        addIndicator(0);
        mSlider.addOnPageChangeListener(pageChangeListener);

        createNotificationChannel();

    }

    public void addIndicator(int pos){
        indicador = new TextView[3];
        mDot.removeAllViews();
        for (int i = 0; i<indicador.length; i++){
            indicador[i] = new TextView(this);
            indicador[i].setText(Html.fromHtml("&#8226"));
            indicador[i].setTextSize(35);
            indicador[i].setTextColor(getResources().getColor(R.color.Transparente));
            mDot.addView(indicador[i]);
            if (pos == i){
                indicador[pos].setTextSize(40);
            }
        }
        if (indicador.length > 0){

            indicador[pos].setTextColor(getResources().getColor(R.color.White));
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void click(View view){
        am = (AlarmManager)   getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),  PlanaficarAlarma.class);
        PendingIntent pi =
                PendingIntent.getBroadcast(getApplicationContext(),
                        0, intent, 0);
        am.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                10000,
                2*1000, pi);
        Toast.makeText(this, "kkkkkkkkk", Toast.LENGTH_SHORT).show();


        Intent intent2 = new Intent(this, Class_list_note.class);
        startActivity(intent2);
        //this.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //Toast.makeText(view.getContext(),"MAL",Toast.LENGTH_LONG).show();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

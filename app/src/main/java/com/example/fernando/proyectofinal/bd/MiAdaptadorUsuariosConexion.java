package com.example.fernando.proyectofinal.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiAdaptadorUsuariosConexion extends SQLiteOpenHelper {

    public static final String [] COLUMNS_USUARIOS = {"_id", "titulo","mensaje", "audio", "video", "fotos", "fecha", "recordatorio" };
    public static final String [] TABLES_DB = {"notas"};

    private String SCRIPT_DB  = "create table Notas (_id integer primary key autoincrement, " +
            "titulo text not null, mensaje text not null,audio text, video text, fotos text," +
            "fecha text, recordatorio bool);";


    public MiAdaptadorUsuariosConexion (Context ctx){
        super(ctx, "bdEjemplos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SCRIPT_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

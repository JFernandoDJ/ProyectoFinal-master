package com.example.fernando.proyectofinal.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fernando.proyectofinal.Nodo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOnota {

    private SQLiteDatabase _ad ;

    public DAOnota(Context ctx) {
        _ad = new MiAdaptadorUsuariosConexion(ctx).getWritableDatabase();
    }


    public long add(Nota u)
    {
        Log.i("&&&&&&&&&&",u.getImagenesCadena() + " #####");
        ContentValues cv = new ContentValues();
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[1], u.getTitulo() );
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[2], u.getMensaje() );
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[3], u.getAudioCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[4], u.getVideosCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[5], u.getImagenesCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[6], u.getFecha() + "");
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[7], u.isRecordatorio());
        return _ad.insert(
                MiAdaptadorUsuariosConexion.TABLES_DB[0],
                null,
                cv);
    }

    public List<Nota> getAll(){
        List <Nota> lst = new ArrayList<Nota>();

        Cursor c = _ad.rawQuery("SELECT * FROM notas", null);
        while(c.moveToNext()){
            lst.add(
                    new Nota(c.getInt(0), c.getString(1),getExtraerVideos(c.getString(4)),
                            getExtraerImagenes(c.getString(5)),getExtraerAudios(c.getString(6)),
                            new Date(), c.getInt(7) == 0? false:true,c.getString(2))
            );

        }
        return lst;
    }

    public ArrayList<Nodo> getExtraerVideos(String s){
        ArrayList listA = new ArrayList();
        if (s != null) {
            String[] list = s.split("♥");
            for (String sE : list) {
                if (!sE.equals("")) {
                    listA.add(new Nodo(sE.split("[/]")[6],sE));
                }
            }
        }
        return listA;
    }

    public ArrayList<Nodo> getExtraerImagenes(String s){
        ArrayList listA = new ArrayList();
        Log.i("############", s + " --------------------------------");
        if(s != null) {
            String[] list = s.split("♥");
            for (String sE : list) {
                if (!sE.equals("")) {
                    listA.add(new Nodo(sE.split("[/]")[6],sE));
                }
            }
        }
        return listA;
    }

    public ArrayList<Nodo> getExtraerAudios(String s){
        ArrayList listA = new ArrayList();
        if(s != null){
            String [] list = s.split("♥");
            for (String sE: list){
                if (!sE.equals("")) {
                    listA.add(new Nodo("",sE));
                }
            }
        }
        return listA;
    }

    public List<Nota> getAll(String nombre){
        List <Nota> lst = new ArrayList<Nota>();

        Cursor c = _ad.rawQuery("SELECT * FROM notas Where titulo like \"%" + nombre + "%\" or mensaje like \"%" + nombre + "%\"", null);

        while(c.moveToNext()){

            lst.add(
                    new Nota(c.getInt(0), c.getString(1),getExtraerVideos(c.getString(4)),
                            getExtraerImagenes(c.getString(5)),getExtraerAudios(c.getString(6)),
                            new Date(), false,c.getString(2))
            );

        }
        return lst;
    }

    public long delete(int position)
    {
        String[] args = new String[]{(position+"")};
        return _ad.delete(MiAdaptadorUsuariosConexion.TABLES_DB[0], "_id=?", args);

    }

    public long   update (Nota u){
        ContentValues cv = new ContentValues();
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[1], u.getTitulo() );
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[2], u.getMensaje() );
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[3], u.getAudioCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[4], u.getVideosCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[5], u.getImagenesCadena());
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[6], u.getFecha() + "");
        cv.put(MiAdaptadorUsuariosConexion.COLUMNS_USUARIOS[7], u.isRecordatorio());

        return _ad.update(MiAdaptadorUsuariosConexion.TABLES_DB[0],
                cv,
                "_id=?",
                new String[]{String.valueOf( u.getId())}
        );
    }

    public Nota getOneNote(int id){
        Nota note = null;
        Cursor c = _ad.rawQuery("SELECT * FROM notas Where  _id = "+ id, null);
        while(c.moveToNext()){
           note = new Nota(c.getInt(0), c.getString(1),getExtraerVideos(c.getString(4)),
                            getExtraerImagenes(c.getString(5)),getExtraerAudios(c.getString(6)),
                            new Date(), false,c.getString(2));
        }
        return note;
    }

}



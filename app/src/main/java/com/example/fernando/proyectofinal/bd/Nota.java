package com.example.fernando.proyectofinal.bd;

import com.example.fernando.proyectofinal.Nodo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nota {

    private int id;
    private String titulo;
    private ArrayList<Nodo> videos;
    private ArrayList<Nodo> imagenes;
    private ArrayList<Nodo> audios;
    private Date fecha;
    private boolean recordatorio;
    private String mensaje;

    public Nota(int id, String titulo, ArrayList<Nodo> videos, ArrayList<Nodo> imagenes, ArrayList<Nodo> audios, Date fecha, boolean recordatorio, String mensaje) {
        this.id = id;
        this.titulo = titulo;
        this.videos = videos;
        this.imagenes = imagenes;
        this.audios = audios;
        this.fecha = fecha;
        this.recordatorio = recordatorio;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Nodo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Nodo> videos) {
        this.videos = videos;
    }

    public String getVideosCadena(){
        String enlaces = "";
        for (Nodo v: videos){
            enlaces+= v.getUrl() + "♥";
        }
        return enlaces;
    }

    public ArrayList<Nodo> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<Nodo> imagenes) {
        this.imagenes = imagenes;
    }

    public String getImagenesCadena(){
        String enlaces = "";
        for (Nodo i: imagenes){
            enlaces+= i.getUrl() + "♥";
        }
        return enlaces;
    }

    public List<Nodo> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<Nodo> audios) {
        this.audios = audios;
    }

    public String getAudioCadena(){
        String enlaces = "";
        for (Nodo a: audios){
            enlaces+= a.getUrl() + "♥";
        }
        return enlaces;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(boolean recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", videos=" + videos +
                ", imagenes=" + imagenes +
                ", audios=" + audios +
                ", fecha=" + fecha +
                ", recordatorio=" + recordatorio +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}

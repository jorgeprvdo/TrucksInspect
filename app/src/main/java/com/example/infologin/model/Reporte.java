package com.example.infologin.model;

public class Reporte {
    private String uid;
    private String Unidad;
    private String Operador;
    private String Viaje;
    private String Fecha;
    private String Descripcion;
    private String Estatus;
    private String Imagen;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getOperador() {
        return Operador;
    }

    public void setOperador(String operador) {
        Operador = operador;
    }

    public String getViaje() {
        return Viaje;
    }

    public void setViaje(String viaje) {
        Viaje = viaje;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Override
    public String toString() {
        return "UNIDAD: " + Unidad +"          "+ Fecha;
    }
}

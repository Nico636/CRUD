package com.example.myapplication.Tipos;

public class Persona {
    private String idPersona;
    private String rut;
    private String nombre;
    private String apellido;

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String toString(){
        return "RUT: "+rut+" // NOMBRE: "+nombre+" // APELLIDO: "+apellido;
    }
}


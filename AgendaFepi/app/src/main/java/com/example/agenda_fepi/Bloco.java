package com.example.agenda_fepi;

import android.widget.Button;

import java.io.Serializable;

public class Bloco implements Serializable {
    private String titulo;
    private String data;
    private String hora;
    private String previa;
    private String tag;
    private String descricaoTarefa;

    public Bloco(String titulo, String data, String hora, String previa, String tag, String descricaoTarefa) {
        this.titulo = titulo;
        this.data = data;
        this.hora = hora;
        this.previa = previa;
        this.tag = tag;
        this.descricaoTarefa = descricaoTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrevia() {
        return previa;
    }

    public void setPrevia(String previa) {
        this.previa = previa;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

}











package com.example.agenda_fepi;

import android.health.connect.datatypes.units.Length;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlocoViewHolder extends RecyclerView.ViewHolder {
    private TextView titulo;
    private TextView previa;
    private TextView data;
    private TextView hora;
    private TextView tag;
    Button pronto;
    Button cancelar;

    public BlocoViewHolder(View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.titulo);
        previa = itemView.findViewById(R.id.previa);
        data = itemView.findViewById(R.id.data);
        hora = itemView.findViewById(R.id.hora);
        tag = itemView.findViewById(R.id.status);
        pronto = itemView.findViewById(R.id.pronto);
        cancelar = itemView.findViewById(R.id.cancelar);
    }

    public void bind(Bloco bloco) {
        titulo.setText(bloco.getTitulo());
        previa.setText(bloco.getPrevia());
        data.setText(bloco.getData());
        hora.setText(bloco.getHora());
        tag.setText(bloco.getTag());

        pronto.setOnClickListener(v -> {
            Toast.makeText(itemView.getContext(), "Tarefa " + bloco.getTitulo() + " concluida", Toast.LENGTH_SHORT).show();
        });

        cancelar.setOnClickListener(v -> {
            Toast.makeText(itemView.getContext(), "Cancelado: " + bloco.getTitulo(), Toast.LENGTH_SHORT).show();
        });

    }
}

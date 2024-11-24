package com.example.agenda_fepi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlocoAdapter extends RecyclerView.Adapter<BlocoViewHolder> {
    private List<Bloco> blocos;
    private Context context;

    public BlocoAdapter(Context context, List<Bloco> blocos) {
        this.context = context;
        this.blocos = blocos;

    }

    public void updateBlocos(List<Bloco> blocos) {
        this.blocos = blocos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BlocoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bloco, parent, false);
        return new BlocoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BlocoViewHolder holder, int position) {

        Bloco bloco = blocos.get(position);
        holder.bind(bloco);
        if (bloco.getTag().equals("Completa")) {
            holder.pronto.setVisibility(View.GONE);
            holder.cancelar.setVisibility(View.GONE);
        } else {
            holder.pronto.setVisibility(View.VISIBLE);
            holder.cancelar.setVisibility(View.VISIBLE);
        }

        // pronto e cancelar funçao
        holder.pronto.setOnClickListener(v -> {
            if (bloco.getTag().equals("Pendente") || bloco.getTag().equals("Urgente")) {
                bloco.setTag("Completa");
                notifyDataSetChanged();
            }
        });

        holder.cancelar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Tem certeza de que deseja excluir esta anotação?")
                    .setCancelable(false)
                    .setPositiveButton("Sim", (dialog, id) -> {
                        blocos.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Não", (dialog, id) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Detalhes.class);
            intent.putExtra("bloco", bloco);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return blocos.size();
    }
}
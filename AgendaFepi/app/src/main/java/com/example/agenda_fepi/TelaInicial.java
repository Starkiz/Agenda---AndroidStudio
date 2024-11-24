package com.example.agenda_fepi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends AppCompatActivity {
    private ActivityResultLauncher<Intent> resultLauncher;
    private RecyclerView recyclerView;
    private BlocoAdapter adapter;
    private List<Bloco> blocos;
    private ImageButton btnCriar;

    //mudar cor e tamanho dos boteoes urgencia, tarefa e agenda
    private Button urgente, tarefas, agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //iniciando o ActivityResultLauncher
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bloco novoBloco = (Bloco) result.getData().getSerializableExtra("novoBloco");
                blocos.add(novoBloco);
                adapter.updateBlocos(blocos);
                adapter.notifyDataSetChanged();
            }
        });

        blocos = new ArrayList<>();
        carregarBlocos();

        //recyclerview
        recyclerView = findViewById(R.id.listaBlocos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BlocoAdapter(this, blocos);
        recyclerView.setAdapter(adapter);

        //botoes
        urgente = findViewById(R.id.Urgente);
        tarefas = findViewById(R.id.tarefas);
        agenda = findViewById(R.id.Agenda);
        btnCriar = findViewById(R.id.btnCriar);


        urgente.setOnClickListener(v -> {
            btnSelect(urgente);
            filterBlocos("Urgente");
        });
        tarefas.setOnClickListener(v -> {
            btnSelect(tarefas);
            filterBlocos("Pendente");
        });
        agenda.setOnClickListener(v -> {
            btnSelect(agenda);
            filterBlocos("Completa");
        });

        //botao criar novo bloco
        btnCriar.setOnClickListener(v -> {
            Intent intent = new Intent(this, Detalhes.class);
            resultLauncher.launch(intent);
        });

        //mostrar todos
        TextView LogoAll = findViewById(R.id.Logo);
        LogoAll.setOnClickListener(v -> {
            mostrarTodosBlocos();
            resetarSeleçao();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bloco novoBloco = (Bloco) data.getSerializableExtra("novoBloco");
            blocos.add(novoBloco);
            adapter.updateBlocos(blocos);
            filterBlocos("Pendente");
            adapter.notifyDataSetChanged();
        }
    }

    //blocos criados manualmente para testar a lista
    private void carregarBlocos() {
        blocos.add(new Bloco("Teste", "To testando", "12/12/12", "14:14", "Pendente", "teste 1"));
        blocos.add(new Bloco("Teste2", "ainda testando", "13/13/12", "14:14", "Urgente", "teste 2"));
        blocos.add(new Bloco("Teste3", "continuo testando", "14/14/12", "14:14", "Completa", "teste 3"));
    }

    // filtrar bloco
    private void filterBlocos(String tag) {
        if (blocos != null && !blocos.isEmpty()) {
            List<Bloco> blocosFiltrados = new ArrayList<>();
            for (Bloco bloco : blocos) {
                if (bloco.getTag().equals(tag)) {
                    blocosFiltrados.add(bloco);
                }
            }
            adapter.updateBlocos(blocosFiltrados);
        }
    }

    //mostrar todos os blocos ao clicar em PLANIFY
    private void mostrarTodosBlocos() {
        adapter.updateBlocos(blocos);
    }

    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void resetarSeleçao() {
        setDefaultStyle(urgente);
        setDefaultStyle(tarefas);
        setDefaultStyle(agenda);
    }

    private void btnSelect(Button btnSelect) {
        setDefaultStyle(urgente);
        setDefaultStyle(tarefas);
        setDefaultStyle(agenda);

        setSelectedStyle(btnSelect);
    }

    private void setSelectedStyle(Button button) {
        button.animate()
                .scaleX(1.2f)
                .setDuration(0)
                .start();

        button.setBackgroundColor(Color.parseColor("#2e4d86"));
    }

    private void setDefaultStyle(Button button) {
        button.animate()
                .scaleX(1.0f) // Volta ao tamanho original
                .setDuration(0)
                .start();

        button.setBackgroundColor(Color.parseColor("#2c3554"));
    }

}

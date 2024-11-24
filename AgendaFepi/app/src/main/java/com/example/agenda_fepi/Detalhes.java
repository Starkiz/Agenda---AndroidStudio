package com.example.agenda_fepi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Detalhes extends AppCompatActivity {

    private EditText editTitulo, editData, editPrevia, editHorario, editDescricaoTarefa;
    private List<Bloco> blocos;
    private Button btnPronto, btnCancelar;
    private Bloco bloco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes);

        // iniciando os campos
        editTitulo = findViewById(R.id.editTitulo);
        editData = findViewById(R.id.editData);
        editHorario = findViewById(R.id.editHorario);
        editPrevia = findViewById(R.id.editPrevia);
        editDescricaoTarefa = findViewById(R.id.editDescricaoTarefa);

        blocos = new ArrayList<>();

        //botoes dos blocos
        btnPronto = findViewById(R.id.btnPronto);
        btnCancelar = findViewById(R.id.btnCancelar);

        //recuperando a intent
        bloco = (Bloco) getIntent().getSerializableExtra("bloco");

        //verificar se o bloco foi passado
        if (bloco != null) {
            //caso tenha um bloco existente
            editTitulo.setText(bloco.getTitulo());
            editData.setText(bloco.getData());
            editHorario.setText(bloco.getHora());
            editPrevia.setText(bloco.getPrevia());
            editDescricaoTarefa.setText(bloco.getDescricaoTarefa());
        } else {
            //se nao for um bloco, mostre erro
            Toast.makeText(this, "Anotação nao encontrada", Toast.LENGTH_SHORT).show();
        }

        //açao do botao pronto
        btnPronto.setOnClickListener(v1 -> {
            //verificar criar/editar
            if (bloco != null) {
                bloco.setTitulo(editTitulo.getText().toString());
                bloco.setData(editData.getText().toString());
                bloco.setHora(editHorario.getText().toString());
                bloco.setPrevia(editPrevia.getText().toString());
                bloco.setDescricaoTarefa(editDescricaoTarefa.getText().toString());

                Toast.makeText(Detalhes.this, "Anotação atualizada com sucesso", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } else {
                String titulo = editTitulo.getText().toString();
                String data = editData.getText().toString();
                String horario = editHorario.getText().toString();
                String previa = editPrevia.getText().toString();
                String descricaoTarefa = editDescricaoTarefa.getText().toString();

                //validar campo
                if (titulo.isEmpty() || previa.isEmpty() || data.isEmpty()) {
                    Toast.makeText(Detalhes.this, "Preencha todos os campos obrigatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                //criando bloco novo
                Bloco novoBloco = new Bloco(titulo, data, horario, previa, "Pendente", descricaoTarefa);

                //enviar bloco para lista
                Intent intent = new Intent();
                intent.putExtra("novoBloco", novoBloco);
                setResult(RESULT_OK, intent);
            }
            finish();
        });

        btnCancelar.setOnClickListener(v1 -> {
            //cancela alteraçoes}
            setResult(RESULT_CANCELED);
            finish();
        });

        //campos data;hora
        editData.setOnClickListener(v1 -> abrirDatePicker());
        editHorario.setOnClickListener(v1 -> abrirTimePicker());
    }


    private void abrirDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, anoSelecionado, mesSelecionado, diaSelecionado) -> {
            String dataFormada = String.format("%02d/%02d/%02d", diaSelecionado, mesSelecionado + 1, anoSelecionado);
            editData.setText(dataFormada);
        }, ano, mes, dia);
        datePickerDialog.show();
    }

    private void abrirTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, horaSelecionada, minutoSelecionado) -> {
            String horaFormatada = String.format("%02d:%02d", horaSelecionada, minutoSelecionado);
            editHorario.setText(horaFormatada);
        }, hora, minuto, true);

        timePickerDialog.show();

    }
}

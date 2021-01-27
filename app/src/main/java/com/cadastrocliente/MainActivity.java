package com.cadastrocliente;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cadastrocliente.dao.DAOFactory;
import com.cadastrocliente.entidade.Cliente;
import com.cadastrocliente.util.Valida;

import java.util.List;

/**
 * Classe Principal
 *
 * @author osmarbraz
 * @version 1.0
 * @updated 19-abr-2020 22:00:00
 */

public class MainActivity extends AppCompatActivity {
    private EditText EditTextClienteId;
    private EditText EditTextNome;
    private EditText EditTextCpf;
    private TextView TextViewRegistros;
    private EditText EditTextListaDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associa os componentes da interface aos componentes da classe
        EditTextClienteId = findViewById(R.id.EditTextClienteId);
        EditTextNome = findViewById(R.id.EditTextNome);
        EditTextCpf = findViewById(R.id.EditTextCpf);
        TextViewRegistros = findViewById(R.id.TextViewRegistros);
        EditTextListaDados = findViewById(R.id.EditTextListaDados);

        //Define o contexto do banco de dados
        DAOFactory.setContext(getApplicationContext());

        //Cria a tabela
        Cliente cliente =  new Cliente();
        cliente.criar();

        //Atualiza a quantidade de registros
        atualizaRegistros();
    }

    public void onClickIncluir(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Preenche os dtributos do objeto com os dados da interface
        cliente.setClienteId(EditTextClienteId.getText().toString());
        cliente.setNome(EditTextNome.getText().toString());
        cliente.setCpf(EditTextCpf.getText().toString());
        Valida valida = new Valida();
        boolean cpfValido = valida.validaCPF(cliente.getCpf());
        if (cpfValido == true) {
            boolean resultado = cliente.inserir();
            if (resultado == true) {
                Toast.makeText(MainActivity.this, "Inclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
                atualizaRegistros();
            } else {
                Toast.makeText(MainActivity.this, "Inclusão não realizada!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "CPF Inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAlterar(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Preenche os dtributos do objeto com os dados da interface
        cliente.setClienteId(EditTextClienteId.getText().toString());
        cliente.setNome(EditTextNome.getText().toString());
        cliente.setCpf(EditTextCpf.getText().toString());
        Valida valida = new Valida();
        boolean cpfValido = valida.validaCPF(cliente.getCpf());
        if (cpfValido == true) {
            int resultado = cliente.alterar();
            if (resultado != 0) {
                Toast.makeText(MainActivity.this, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Alteração não realizada!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "CPF Inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickConsultar(View v) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(EditTextClienteId.getText().toString());
        boolean resultado = cliente.abrir();
        if (resultado == true) {
            EditTextNome.setText(cliente.getNome());
            EditTextCpf.setText(cliente.getCpf());
            Toast.makeText(MainActivity.this, "Cliente encontrado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Cliente não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickExcluir(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Preenche os dtributos do objeto com os dados da interface
        cliente.setClienteId(EditTextClienteId.getText().toString());
        int resultado = cliente.excluir();
        if (resultado != 0) {
            Toast.makeText(MainActivity.this, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
            atualizaRegistros();
        } else {
            Toast.makeText(MainActivity.this, "Exclusão não realizada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickEsvaziarBD(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Apaga a tabela
        cliente.esvaziarTabela();
        Toast.makeText(MainActivity.this, "Tabela Apagada!", Toast.LENGTH_SHORT).show();
        //Cria a tabela novamente
        cliente.criar();
        Toast.makeText(MainActivity.this, "Tabela Criada!", Toast.LENGTH_SHORT).show();
        atualizaRegistros();
    }

    public void onClickListar(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Recupera a lista de todos os clientes aplicando o fitro sem atribuir nenhum valor ao objeto
        List lista = cliente.aplicarFiltro();
        //Cabeçalho da listagem
        String saida = "clienteId - nome - cpf\n";
        //Percorre a lista recuperando os dados do objeto
        for (int i=0;i<lista.size();i++){
            Cliente cli = (Cliente)lista.get(i);
            saida = saida +  cli.getClienteId() + "-" + cli.getNome() + "-" + cli.getCpf() + "\n";
        }
        EditTextListaDados.setText(saida);
    }

    public void onClickLimpar(View v) {
        //Limpa os textos da inteface
        EditTextClienteId.setText("");
        EditTextNome.setText("");
        EditTextCpf.setText("");
        EditTextListaDados.setText("");
    }

    public void onClickFechar(View v) {
        System.exit(0);
    }

    public void atualizaRegistros() {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Coloca a informação na interface
        TextViewRegistros.setText("Registros: " + cliente.getNumeroRegistros());
    }

}
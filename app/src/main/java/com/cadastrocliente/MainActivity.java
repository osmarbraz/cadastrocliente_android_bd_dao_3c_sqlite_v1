package com.cadastrocliente;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    //Componentes a serem manipulados
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
        Cliente cliente = new Cliente();
        cliente.criar();

        //Atualiza a quantidade de registros
        atualizaRegistros();
    }

    public void onClickIncluir(View v) {
        //Verifica se o clienteId foi preenchido
        if (!EditTextClienteId.getText().toString().equals("")) {
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
                //Coloca o foco na caixa de texto cpf
                EditTextCpf.requestFocus();
            }
        } else {
            Toast.makeText(MainActivity.this, "Digite um clienteId!", Toast.LENGTH_SHORT).show();
            //Coloca o foco na caixa de texto clienteId
            EditTextClienteId.requestFocus();
        }
    }

    public void onClickAlterar(View v) {
        //Verifica se o clienteId foi preenchido
        if (!EditTextClienteId.getText().toString().equals("")) {
            //Instancia o objeto Cliente
            Cliente cliente = new Cliente();
            //Preenche os dtributos do objeto com os dados da interface
            cliente.setClienteId(EditTextClienteId.getText().toString());
            boolean resultadoConsulta = cliente.abrir();
            if (resultadoConsulta == true) {
                // Seta os outros dados do cliente
                cliente.setNome(EditTextNome.getText().toString());
                cliente.setCpf(EditTextCpf.getText().toString());
                //Valida o cpf
                Valida valida = new Valida();
                boolean cpfValido = valida.validaCPF(cliente.getCpf());
                if (cpfValido == true) {
                    int resultadoAlteracao = cliente.alterar();
                    if (resultadoAlteracao != 0) {
                        Toast.makeText(MainActivity.this, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Alteração não realizada!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "CPF Inválido!", Toast.LENGTH_SHORT).show();
                    //Coloca o foco na caixa de texto cpf
                    EditTextCpf.requestFocus();
                }
            } else {
                Toast.makeText(MainActivity.this, "Cliente não encontrado, digite um clienteId válido!", Toast.LENGTH_SHORT).show();
                //Coloca o foco na caixa de texto clienteId
                EditTextClienteId.requestFocus();
            }
        } else {
            Toast.makeText(MainActivity.this, "Digite um clienteId!", Toast.LENGTH_SHORT).show();
            //Coloca o foco na caixa de texto clienteId
            EditTextClienteId.requestFocus();
        }

    }

    public void onClickConsultar(View v) {
        //Verifica se o clienteId foi preenchido
        if (!EditTextClienteId.getText().toString().equals("")) {
            //Instancia o objeto Cliente
            Cliente cliente = new Cliente();
            //Preenche os dtributos do objeto com os dados da interface
            cliente.setClienteId(EditTextClienteId.getText().toString());
            boolean resultadoConsulta = cliente.abrir();
            if (resultadoConsulta == true) {
                EditTextNome.setText(cliente.getNome());
                EditTextCpf.setText(cliente.getCpf());
                Toast.makeText(MainActivity.this, "Cliente encontrado!", Toast.LENGTH_SHORT).show();
                EditTextClienteId.requestFocus();
            } else {
                Toast.makeText(MainActivity.this, "Cliente não encontrado!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Digite um clienteId!", Toast.LENGTH_SHORT).show();
            //Coloca o foco na caixa de texto clienteId
            EditTextClienteId.requestFocus();
        }
    }

    public void onClickExcluir(View v) {
        //Verifica se o clienteId foi preenchido
        if (!EditTextClienteId.getText().toString().equals("")) {
            //Instancia o objeto Cliente
            Cliente cliente = new Cliente();
            //Preenche os atributos do objeto com os dados da interface
            cliente.setClienteId(EditTextClienteId.getText().toString());
            boolean resultadoConsulta = cliente.abrir();
            if (resultadoConsulta == true) {
                //Confirma a exclusão dos dados da tabela
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Excluir cliente"); //Título da janela de diálogo
                dialogo.setMessage("Desejar excluir o registro?"); //Mensagem da janela de diálogo
                dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //Evento para o botão sim
                    public void onClick(DialogInterface dialog, int which) {
                        //Ação para a resposta sim
                        int resultadoExclusao = cliente.excluir();
                        if (resultadoExclusao != 0) {
                            Toast.makeText(MainActivity.this, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            atualizaRegistros();
                        } else {
                            Toast.makeText(MainActivity.this, "Exclusão não realizada!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener() { //Evento para o botão não
                    public void onClick(DialogInterface dialog, int which) {
                        //Ação para a resposta não

                        //Apenas fecha a mensagem
                        dialog.dismiss();
                    }
                });
                dialogo.show(); //Exibe o diálogo
            } else {
                Toast.makeText(MainActivity.this, "Cliente não encontrado, digite um clienteId válido!", Toast.LENGTH_SHORT).show();
                //Coloca o foco na caixa de texto clienteId
                EditTextClienteId.requestFocus();
            }
        } else {
            Toast.makeText(MainActivity.this, "Digite um clienteId!", Toast.LENGTH_SHORT).show();
            //Coloca o foco na caixa de texto clienteId
            EditTextClienteId.requestFocus();
        }
    }

    public void onClickEsvaziarBD(View v) {
        //Confirma a exclusão dos dados da tabela
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Esvaziar BD"); //Título da janela de diálogo
        dialogo.setMessage("Deseja esvaziar a tabela Cliente?"); //Mensagem da janela de diálogo
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //Evento para o botão sim
            public void onClick(DialogInterface dialog, int which) {
                //Ação para a resposta sim

                //Instancia o objeto Cliente
                Cliente cliente = new Cliente();
                //Apaga a tabela
                cliente.apagarTabela();
                Toast.makeText(MainActivity.this, "Tabela Apagada!", Toast.LENGTH_SHORT).show();
                //Cria a tabela novamente
                cliente.criar();
                Toast.makeText(MainActivity.this, "Tabela Criada!", Toast.LENGTH_SHORT).show();
                atualizaRegistros();
            }
        });
        dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener() { //Evento para o botão não
            public void onClick(DialogInterface dialog, int which) {
                //Ação para a resposta não

                //Apenas fecha a mensagem
                dialog.dismiss();
            }
        });
        dialogo.show(); //Exibe o diálogo
    }

    public void onClickListar(View v) {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Recupera a lista de todos os clientes aplicando o fitro sem atribuir nenhum valor ao objeto
        List lista = cliente.aplicarFiltro();
        //Cabeçalho da listagem
        String saida = "clienteId - nome - cpf\n";
        //Percorre a lista recuperando os dados do objeto
        for (int i = 0; i < lista.size(); i++) {
            //Recupera o cliente i da lista
            Cliente cli = (Cliente) lista.get(i);
            saida = saida + cli.getClienteId() + "-" + cli.getNome() + "-" + cli.getCpf() + "\n";
        }
        EditTextListaDados.setText(saida);
    }

    public void onClickLimpar(View v) {
        //Limpa os textos da inteface
        EditTextClienteId.setText("");
        EditTextNome.setText("");
        EditTextCpf.setText("");
        EditTextListaDados.setText("");
        //Coloca o foco na caixa de texto clienteId
        EditTextClienteId.requestFocus();
    }

    public void onClickFechar(View v) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Fechar aplicativo"); //Título da janela de diálogo
        dialogo.setMessage("Você tem certeza que deseja sair?"); //Mensagem da janela de diálogo
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() { //Evento para o botão sim
            public void onClick(DialogInterface dialog, int which) {
                //Ação para a resposta sim
                Toast.makeText(MainActivity.this, "Fechando o aplicativo", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });
        dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener() { //Evento para o botão não
            public void onClick(DialogInterface dialog, int which) {
                //Ação para a resposta não
                //Apenas fecha a mensagem
                dialog.dismiss();
            }
        });
        dialogo.show(); //Exibe o diálogo
    }

    public void atualizaRegistros() {
        //Instancia o objeto Cliente
        Cliente cliente = new Cliente();
        //Coloca a informação na interface
        TextViewRegistros.setText("Registros: " + cliente.getNumeroRegistros());
    }

}
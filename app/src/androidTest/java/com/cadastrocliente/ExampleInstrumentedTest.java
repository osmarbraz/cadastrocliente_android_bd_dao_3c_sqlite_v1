package com.cadastrocliente;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cadastrocliente.dao.DAOFactory;
import com.cadastrocliente.entidade.Cliente;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Teste unitário dependente do contexto, que serão executando no dispositivo Android.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    // Contexto do teste
    private Context appContext = null;

    @Before
    public void inicializa(){
        // Recupera o contexto do teste.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void useAppContext() {
        assertEquals("com.cadastrocliente", appContext.getPackageName());
    }
    @Test
    public void inserirCliente(){
        //Define o contexto do banco de dados
        DAOFactory.setContext(appContext);

        //Instancia um cliente co dados
        Cliente cliente = new Cliente("1","Teste","11111111111");
        //Realiza a inserção
        cliente.incluir();
        //Recupera o cliente
        List lista = cliente.aplicarFiltro();
        //Se a lista não é vazia
        assertNotNull(lista);
    }

    @Test
    public void consultarCliente(){
        //Define o contexto do banco de dados
        DAOFactory.setContext(appContext);

        //Instancia um cliente co dados
        Cliente cliente = new Cliente("1","Teste","11111111111");
        //Realiza a inserção
        cliente.incluir();

        //Objeto de consulta
        Cliente consulta = new Cliente();
        consulta.setClienteId(cliente.getClienteId());

        //Recupera o cliente
        consulta.abrir();
        //Verifica se o cliente armazenado é ingual ao cliente consultado
        assertTrue(consulta.getClienteId().equals(cliente.getClienteId()));
    }

    @Test
    public void excluirCliente(){
        //Define o contexto do banco de dados
        DAOFactory.setContext(appContext);

        //Instancia um cliente co dados
        Cliente cliente = new Cliente("1","Teste","11111111111");
        //Realiza a inserção
        cliente.incluir();
        //Consulta a lista
        List lista = cliente.aplicarFiltro();

        if (lista.size() > 0) {
            Cliente cli = (Cliente)lista.get(0);
            cli.excluir();
            lista = cli.aplicarFiltro();
            assertTrue(lista.size() == 0);
        } else
            assertFalse(false);
    }
}
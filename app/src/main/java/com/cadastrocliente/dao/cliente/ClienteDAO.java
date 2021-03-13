package com.cadastrocliente.dao.cliente;

import java.util.List;

/**
 * Interface que define as operações para a persistência de cliente.
 *
 * @author osmarbraz
 * @version 1.0
 * @updated 19-abr-2020 22:00:00
 */
public interface ClienteDAO {

    public boolean incluir(Object obj);

    public int alterar(Object obj);

    public int excluir(Object obj);

    public List aplicarFiltro(Object obj);

    public void criar();

    public void apagarTabela();

    public long getNumeroRegistros();
}

package com.cadastrocliente.dao;

import android.database.sqlite.SQLiteDatabase;

import com.cadastrocliente.dao.cliente.ClienteDAO;
import com.cadastrocliente.dao.cliente.SQLiteClienteDAO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Implementa a fonte de dado para persistência em arquivo utilizando SGBD
 * SQLite.
 *
 * @author osmarbraz
 * @version 1.0
 * @updated 19-abr-2020 22:00:00
 */
public class SQLiteDAOFactory extends DAOFactory implements SQLiteDadosBanco {

    /**
     * Retorna uma conexão aberta para as subclasses da fábrica para SQLite.
     *
     * @return Connection Um objeto com a conexão aberta
     */
    protected SQLiteDatabase getConnection() {
        SQLiteDatabase con = getContext().openOrCreateDatabase(DATABASE, 0, null);

        return con;
    }

    /**
     * Retorna uma Cliente DAO.
     *
     * @return ClienteDAO Um DAO para cliente.
     */
    public ClienteDAO getClienteDAO() {
        return new SQLiteClienteDAO();
    }

    /**
     * Operação para prepara a string que será enviada ao banco de dados Se ela.
     * possui uma ' será duplicada para anular o efeito de sql injetado.
     *
     * @param valor string a ser preparada para envio ao banco de dados.
     * @return String texto com ' duplicado.
     */
    protected String preparaSQL(String valor) {
        if (valor != null) {
            return valor.replaceAll("\'", "''");
        } else {
            return "";
        }
    }

    /**
     * Concatena String baseado nos valores Strings de uma Collection
     *
     * @param separator  Separador dos campos.
     * @param collection Coleção com os campos.
     * @return String Com os literais conctatenados.
     */
    public String implode(String separator, Collection collection) {
        StringBuffer textBufferReturn = new StringBuffer();
        @SuppressWarnings("rawtypes")
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            String text = (String) it.next();
            textBufferReturn.append(text);
            if (it.hasNext()) {
                textBufferReturn.append(separator);
            }
        }
        return textBufferReturn.toString();
    }

    /**
     * Monta a string do filtro.
     *
     * @param lista     Lista dos campos do filtro.
     * @param separador Separador os dados do filtro.
     * @return Retorna uma string com os dados do filtro.
     */
    public String montaFiltro(List<String> lista, String separador) {
        StringBuffer filtro = new StringBuffer();
        Iterator<String> filtroIt = lista.iterator();
        while (filtroIt.hasNext()) {
            String texto = filtroIt.next();
            filtro.append(texto);
            if (filtroIt.hasNext()) {
                filtro.append(" " + separador + " ");
            }
        }
        return filtro.toString();
    }
}

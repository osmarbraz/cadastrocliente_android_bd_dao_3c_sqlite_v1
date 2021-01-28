package com.cadastrocliente.dao;

import android.content.Context;

import com.cadastrocliente.dao.cliente.ClienteDAO;

/**
 * Abstrai as fontes de dados do sistema.
 *
 * @author osmarbraz
 * @version 1.0
 * @updated 19-abr-2020 22:00:00
 */
public abstract class DAOFactory {
    //Guarda contexto do aplicativo
    private static Context context;

    //Retorna o contexto do aplicativo
    public static Context getContext() {
        return context;
    }

    //Seta o contexto do aplicativo
    public static void setContext(Context context) {
        DAOFactory.context = context;
    }

    //Retorna a Factory do tipo especificado	
    public static DAOFactory getDAOFactory() {
        return new SQLiteDAOFactory();
    }

    //Retorna o DAO instanciado
    public abstract ClienteDAO getClienteDAO();

    //Outros DAOs vão aqui!!!!
}

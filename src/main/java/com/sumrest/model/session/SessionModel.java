package com.sumrest.model.session;

import com.sumrest.model.User;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class SessionModel {

    private static User user;
    private static String level;

    /**
     * Constructor privado.
     */
    private SessionModel() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }

    /**
     * Inicia la sesion del usuario iniciado.
     * 
     * @param user usuario que se ha iniciado.
     */
    public static void startSesion(User user) {
        SessionModel.user = user;
        SessionModel.level = "Medium";
    }

    /**
     * Cierra la sesion actual.
     */
    public static void endSesion() {
        SessionModel.user = null;
        SessionModel.level = "Medium";
    }

    /**
     * Muestra el nombre del usuario en sesion.
     * 
     * @return retorna el nombre del usuario en sesion.
     */
    public static User getUser() {
        return (user != null) ? user : null;
    }

    /**
     * Muestra ek nivel de la sesion.
     * 
     * @return retorna el nivel de la sesion.
     */
    public static String getLevel() {
        return (level != null) ? level : null;
    }

    /**
     * Cambia el nivel de la sesion.
     * 
     * @param level nivel a cambiar.
     */
    public static void setLevel(String level) {
        SessionModel.level = level;
    }

}
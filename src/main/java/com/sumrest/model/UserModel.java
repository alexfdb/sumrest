package com.sumrest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sumrest.model.database.DatabaseModel;

/**
 * @author alexfdb
 * @version 1.0.0
 */
public class UserModel extends DatabaseModel {

    /**
     * Constructor general.
     */
    public UserModel() {
        super();
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * 
     * @param user usuraio a insertar.
     * @return retorna true si el usuario a sido insertado.
     */
    public boolean createUser(User user) {
        String query = "INSERT INTO users(name, password) VALUES (?, ?)";
        try (Connection connection = createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Busca un usuario en la base de datos.
     * 
     * @param user usuario a buscar.
     * @return retorna el usuario buscado.
     */
    public User readUser(User user) {
        String query = "SELECT name, password, answers, hits FROM users WHERE name = ? AND password = ?";
        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    Integer answers = resultSet.getInt("answers");
                    Integer hits = resultSet.getInt("hits");
                    return new User(name, password, answers, hits);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza un usuario de la base de datos.
     * 
     * @param user       usuario a actualizar.
     * @param updateUser usuario actualizado.
     * @return retorna true si el usuario fue actualizado
     */
    public boolean updateUser(User user, User updateUser) {
        String query = "UPDATE users SET name = ?, password = ?, answers = ?, hits = ? WHERE name = ? AND password = ? ";
        try (Connection connection = createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updateUser.getName());
            preparedStatement.setString(2, updateUser.getPassword());
            preparedStatement.setInt(3, updateUser.getAnswers());
            preparedStatement.setInt(4, updateUser.getHits());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getPassword());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina un usurio de la base de datos;
     * 
     * @param user usuario a eliminar.
     * @return retorna true si el usuario fue eliminado.
     */
    public boolean deleteUser(User user) {
        String query = "DELETE FROM users WHERE name = ? AND password = ?";
        try (Connection connection = createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
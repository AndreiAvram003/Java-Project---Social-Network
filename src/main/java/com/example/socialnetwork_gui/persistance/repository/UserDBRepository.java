package com.example.socialnetwork_gui.persistance.repository;


import com.example.socialnetwork_gui.config.DatabaseProperties;
import com.example.socialnetwork_gui.persistance.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDBRepository implements UserRepository {
    private final String url = DatabaseProperties.URL;
    private final String username = DatabaseProperties.username;
    private final String password = DatabaseProperties.password;

    @Override
    public Optional<User> save(User user) {
        String sql = "INSERT INTO users(uid, username, password, email) VALUES(?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setObject(1, user.getUid());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                Long id = this.getGeneratedId(statement);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(id, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(User user) {
        String sql = """
                UPDATE users
                SET uid = ?, username = ?, password = ?, email = ?
                WHERE id = ?
                """;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, user.getUid());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setLong(5,user.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Optional<User> userToDelete = this.getByColumn(id, "SELECT * FROM users WHERE id = ?", connection);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return userToDelete;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            return this.mapToEntities(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getByUid(UUID uid) {
        String sql = "SELECT * FROM users WHERE uid = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(uid, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(url, this.username, password);
        ) {
            return this.getByColumn(username, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(email, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapToEntity(ResultSet resultSet) {
        try {
            return new User(
                    resultSet.getLong("id"),
                    UUID.fromString(resultSet.getString("uid")),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
            );
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> mapToEntities(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while(resultSet.next()) {
            users.add(this.mapToEntity(resultSet));
        }
        return users;
    }

    private Long getGeneratedId(PreparedStatement preparedStatement) {
        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private <T> Optional<User> getByColumn(T column, String sql, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, column);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = this.mapToEntity(resultSet);
                return Optional.of(user);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
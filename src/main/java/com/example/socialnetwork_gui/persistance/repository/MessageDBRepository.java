package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.config.DatabaseProperties;
import com.example.socialnetwork_gui.persistance.model.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDBRepository implements MessageRepository{
    private final String url = DatabaseProperties.URL;
    private final String username = DatabaseProperties.username;
    private final String password = DatabaseProperties.password;

    @Override
    public Optional<Message> save(Message message) {

        String sql = "INSERT INTO message(from_id, to_users, message, delivered_at,uid,reply) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, message.getFrom());
            statement.setLong(2, message.getTo());
            statement.setString(3, message.getMessage());
            statement.setTimestamp(4,Timestamp.valueOf(message.getData()));
            statement.setObject(5,message.getUid());
            statement.setObject(6,message.getReply());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                Long id = this.getGeneratedId(statement);
                message.setId(id);
                return Optional.of(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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


    @Override
    public Optional<Message> update(Message obj) {
        return Optional.empty();
    }

    @Override
    public Optional<Message> deleteById(Long id) {
        String sql = "DELETE FROM message WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Optional<Message> messageToDelete = this.getByColumn(id, "SELECT * FROM message WHERE id = ?", connection);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return messageToDelete;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Message> getAll() {
        String sql = "SELECT * FROM message";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            return this.mapToEntities(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Message> mapToEntities(ResultSet resultSet) throws SQLException {
        List<Message> messages = new ArrayList<>();
        while(resultSet.next()) {
            messages.add(this.mapToEntity(resultSet));
        }
        return messages;
    }


    @Override
    public List<Message> getByIds(Long from, Long to) {
        String sql = "SELECT * FROM message WHERE (from_id = ? AND to_users = ?) OR (to_users = ? AND from_id = ?) ORDER BY delivered_at";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, from);
            preparedStatement.setLong(2, to);
            preparedStatement.setLong(3, from);
            preparedStatement.setLong(4, to);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(mapToEntity(resultSet));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Message> getById(Long from) {
        String sql = "SELECT * FROM message WHERE from = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(from, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> getByUid(UUID replyUid) {
        String sql = "SELECT * FROM message WHERE uid = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(replyUid, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Message mapToEntity(ResultSet resultSet) {
        try {
            return new Message(
                    resultSet.getLong("id"),
                    UUID.fromString(resultSet.getString("uid")),
                    resultSet.getLong("from_id"),
                    resultSet.getLong("to_users"),
                    resultSet.getString("message"),
                    resultSet.getTimestamp("delivered_at").toLocalDateTime(),
                    resultSet.getLong("reply")


            );
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Optional<Message> getByColumn(T column, String sql, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, column);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Message message = this.mapToEntity(resultSet);
                return Optional.of(message);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}

package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.config.DatabaseProperties;
import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FriendshipDBRepository implements FriendshipRepository {
    private final String url = DatabaseProperties.URL;
    private final String username = DatabaseProperties.username;
    private final String password = DatabaseProperties.password;

    @Override
    public Optional<Friendship> save(Friendship friendship) {
        String sql = "INSERT INTO friendships(idfirstuser, idseconduser, created_at, status) VALUES(?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, friendship.getIdFirstUser());
            statement.setLong(2, friendship.getIdSecondUser());
            statement.setTimestamp(3, Timestamp.valueOf(friendship.getCreatedAt()));
            statement.setString(4,friendship.getStatus());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                Long id = this.getGeneratedId(statement);
                friendship.setId(id);
                return Optional.of(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> getById(Long id) {
        String sql = "SELECT * FROM friendship WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
        ) {
            return this.getByColumn(id, sql, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship friendship) {
        String sql = """
                UPDATE friendships
                SET idfirstuser = ?, idseconduser = ?, created_at = ?, status = ?
                WHERE id = ?
                """;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, friendship.getIdFirstUser());
            statement.setLong(2, friendship.getIdSecondUser());
            statement.setTimestamp(3, Timestamp.valueOf(friendship.getCreatedAt()));
            statement.setString(4, friendship.getStatus());
            statement.setLong(5,friendship.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return Optional.of(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> deleteById(Long id) {
        String sql = "DELETE FROM friendships WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            Optional<Friendship> friendshipToDelete = this.getByColumn(id, "SELECT * FROM friendships WHERE id = ?", connection);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return friendshipToDelete;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Friendship> getAll() {
        String sql = "SELECT * FROM friendships";
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
    public Optional<Friendship> getByUserIds(Long idFirstUser, Long idSecondUser) {
        String sql = "SELECT * FROM friendships WHERE idfirstuser = ? AND idseconduser = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, idFirstUser);
            statement.setLong(2, idSecondUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Friendship friendship = this.mapToEntity(resultSet);
                return Optional.of(friendship);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Friendship> getAllByUserId(Long userId) {
        String sql = "SELECT * FROM friendships WHERE idfirstuser = ? OR idseconduser = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return this.mapToEntities(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllNonFriends(Long userId) {
        String sql = "SELECT * FROM friendships WHERE idfirstuser != ? AND idseconduser != ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            return this.mapToEntitiesforUsers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateObserver(Long deletedUserId) {

    }

    private Friendship mapToEntity(ResultSet resultSet) {
        try {
            return new Friendship(
                    resultSet.getLong("id"),
                    resultSet.getLong("idfirstuser"),
                    resultSet.getLong("idseconduser"),
                    resultSet.getTimestamp("created_at").toLocalDateTime(),
                    resultSet.getString("status")
            );
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private User mapToEntityforUser(ResultSet resultSet) {
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

    private List<Friendship> mapToEntities(ResultSet resultSet) throws SQLException {
        List<Friendship> friendships = new ArrayList<>();
        while(resultSet.next()) {
            friendships.add(this.mapToEntity(resultSet));
        }
        return friendships;
    }

    private List<User> mapToEntitiesforUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while(resultSet.next()) {
            users.add(this.mapToEntityforUser(resultSet));
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

    private <T> Optional<Friendship> getByColumn(T column, String sql, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, column);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Friendship friendship = this.mapToEntity(resultSet);
                return Optional.of(friendship);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
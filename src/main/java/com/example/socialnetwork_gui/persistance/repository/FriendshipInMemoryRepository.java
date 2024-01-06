package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.Friendship;
import com.example.socialnetwork_gui.persistance.model.Observer;
import com.example.socialnetwork_gui.persistance.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FriendshipInMemoryRepository extends BaseInMemoryRepository<Friendship> implements FriendshipRepository, Observer<Long> {
    @Override
    public Optional<Friendship> getByUserIds(Long idFirstUser, Long idSecondUser) {
        return this.getByFilter(friendship -> friendship.getIdFirstUser().equals(idFirstUser) &&
                friendship.getIdSecondUser().equals(idSecondUser));
    }

    public List<Friendship> getAllByUserId(Long userId) {
        return cache
                .values()
                .stream()
                .filter(friendship -> friendship.getIdFirstUser().equals(userId) ||
                        friendship.getIdSecondUser().equals(userId))
                .toList();
    }

    @Override
    public List<User> getAllNonFriends(Long userId) {
        return null;
    }

    private Optional<Friendship> getByFilter(Function<Friendship, Boolean> filterFct) {
        return cache
                .values()
                .stream()
                .filter(filterFct::apply)
                .findAny();
    }

    @Override
    public void updateObserver(Long deletedUserId) {
        List<Friendship> friendshipsToDelete = this.getAllByUserId(deletedUserId);
        friendshipsToDelete.forEach(friendship -> super.deleteById(friendship.getId()));
    }

}

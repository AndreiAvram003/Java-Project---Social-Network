package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends BaseRepository<Friendship, Long> {
    Optional<Friendship> getByUserIds(Long idFirstUser, Long idSecondUser);
    List<Friendship> getAllByUserId(Long userId);
}

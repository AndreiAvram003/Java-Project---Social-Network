package persistance.repository;

import persistance.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends BaseRepository<Friendship, Long> {
    Optional<Friendship> getByUserIds(Long idFirstUser, Long idSecondUser);
    List<Friendship> getAllByUserId(Long userId);
}

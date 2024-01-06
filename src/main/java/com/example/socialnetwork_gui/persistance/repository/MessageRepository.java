package com.example.socialnetwork_gui.persistance.repository;

import com.example.socialnetwork_gui.persistance.model.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends BaseRepository<Message,Long>{

    List<Message> getByIds(Long from,Long to);


    Optional<Message> getById(Long userId);

    Optional<Message> getByUid(UUID replyUid);
}

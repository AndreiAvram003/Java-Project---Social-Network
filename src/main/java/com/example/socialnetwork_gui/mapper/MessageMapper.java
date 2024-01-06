package com.example.socialnetwork_gui.mapper;

import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.MessageRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MessageMapper {

    Message toMessage(MessageRequest messageRequest,Long from, Long to,Message reply);
    List<MessageDto> toDto(List<Message> messages,Map<Long, UserDto> userIdToDtoMapping,Map<Long,Message> mapIdToMapping);

    MessageDto toDto(UserDto fromUserDto, UserDto toUserDto, Message message, Message reply);
}

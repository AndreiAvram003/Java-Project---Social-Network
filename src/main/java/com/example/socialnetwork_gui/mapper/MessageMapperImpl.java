package com.example.socialnetwork_gui.mapper;

import com.example.socialnetwork_gui.persistance.model.Entity;
import com.example.socialnetwork_gui.persistance.model.Message;
import com.example.socialnetwork_gui.persistance.model.User;
import com.example.socialnetwork_gui.persistance.model.dtos.MessageDto;
import com.example.socialnetwork_gui.persistance.model.dtos.UserDto;
import com.example.socialnetwork_gui.request.MessageRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageMapperImpl implements MessageMapper {
    @Override
    public Message toMessage(MessageRequest messageRequest,Long from,Long to,Message reply) {
        return new Message(
                null,
                UUID.randomUUID(),
                from,
                to,
                messageRequest.getMessage(),
                LocalDateTime.now(),
                reply == null ? null : reply.getId()

        );
    }


    @Override
    public List<MessageDto> toDto(List<Message> messages,Map<Long, UserDto> userIdToDtoMapping,Map<Long,Message> messageIdToMapping) {
        return messages
                .stream()
                .map(message -> this.toDto(message,userIdToDtoMapping,messageIdToMapping))
                .toList();
    }

    @Override
    public MessageDto toDto(UserDto fromUserDto, UserDto toUserDto, Message message, Message reply) {
        if (message == null) {
            return null;
        }
        MessageDto replyDto = message.getReply() == null ? null : toDto(fromUserDto, toUserDto, reply, null);
        return new MessageDto(message.getUid(), fromUserDto, toUserDto, message.getMessage(), message.getData(), replyDto);
    }

    private MessageDto toDto(Message message, Map<Long, UserDto> userIdToDtoMapping,Map<Long,Message> messageIdToMapping) {
        if (message == null) {
            return null;
        }
        UserDto from = userIdToDtoMapping.get(message.getFrom());
        UserDto to = userIdToDtoMapping.get(message.getTo());
        MessageDto replyDto = message.getReply() == null ? null : toDto(messageIdToMapping.get(message.getReply()), userIdToDtoMapping, messageIdToMapping);
        return new MessageDto(message.getUid(),
                from, to, message.getMessage(),
                message.getData(),
                replyDto
        );
    }
}

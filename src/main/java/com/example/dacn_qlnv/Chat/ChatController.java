package com.example.dacn_qlnv.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Lưu tin nhắn vào cơ sở dữ liệu
        MessageEntity messageEntity = MessageEntity.builder()
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .timestamp(System.currentTimeMillis()) // Lấy thời gian hiện tại
                .build();
        messageRepository.save(messageEntity);

        // Trả về tin nhắn để gửi tới WebSocket
        chatMessage.setTimestamp(messageEntity.getTimestamp());
        return chatMessage;
    }

    @GetMapping("/api/chat/history")
    public List<ChatMessage> getChatHistory() {
        return messageRepository.findAll().stream()
                .map(entity -> ChatMessage.builder()
                        .sender(entity.getSender())
                        .content(entity.getContent())
                        .timestamp(entity.getTimestamp())
                        .type(MessageType.CHAT)
                        .build())
                .collect(Collectors.toList());
    }

}

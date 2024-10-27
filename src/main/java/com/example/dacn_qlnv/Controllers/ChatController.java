//package com.example.dacn_qlnv.Controllers;
//
//import com.example.dacn_qlnv.Models.Chat;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class ChatController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public ChatController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @MessageMapping("/sendMessage")
//    public void sendMessage(Message<String> message) {
//        messagingTemplate.convertAndSend("/topic/messages", message.getPayload());
//    }
//    @GetMapping("/chat")
//    public String chat() {
//        return "chat/chat"; // Tên template HTML (chat.html)
//    }
//}

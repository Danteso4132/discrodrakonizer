//package com.danteso.discrodrakonizer.db;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@ContextConfiguration(classes = TestMongoConfig.class)
//public class ChatMessageRepositoryTest {
//
//    @Autowired
//    private ChatRepo chatMessageRepository;
//
//    @Test
//    public void testSaveAndGetChatMessage() {
//        // Create a sample ChatMessage object
//        ChatMessage chatMessage = ChatMessage.builder().messageContent("test content").id("123").build();
//
//        // Save the ChatMessage object
//        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
//
//        // Retrieve the ChatMessage object
//        ChatMessage retrievedMessage = chatMessageRepository.findById(savedMessage.getId()).orElse(null);
//
//        // Assert that the retrieved message is not null
//        assertNotNull(retrievedMessage);
//    }
//}
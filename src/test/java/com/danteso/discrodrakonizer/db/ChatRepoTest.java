package com.danteso.discrodrakonizer.db;

import com.danteso.discrodrakonizer.db.ChatMessage;
import com.danteso.discrodrakonizer.db.ChatRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DirtiesContext
public class ChatRepoTest {

    @Autowired
    private ChatRepo chatRepo;

    @Test
    public void testChatRepoOperations() {
        // Create a sample ChatMessage
        ChatMessage chatMessage = ChatMessage.builder().messageContent("test second").id("333").build();

        // Save the ChatMessage
        ChatMessage savedMessage = chatRepo.save(chatMessage);

        // Retrieve the saved message
        ChatMessage retrievedMessage = chatRepo.findById(savedMessage.getId()).orElse(null);

        // Update the message content
        retrievedMessage.setMessageContent("Updated message content");
        chatRepo.save(retrievedMessage);

        // Verify the updated message
        ChatMessage updatedMessage = chatRepo.findById(retrievedMessage.getId()).orElse(null);
        System.out.println("Updated Message: " + updatedMessage);

        // Delete the message
        chatRepo.delete(updatedMessage);

        // Verify deletion
        ChatMessage deletedMessage = chatRepo.findById(retrievedMessage.getId()).orElse(null);
        assert deletedMessage == null;
    }

    @Test
    public void testFindAllChatMessages() {
        // Retrieve all chat messages
        List<ChatMessage> chatMessages = chatRepo.findAll();

        // Print all chat messages
        chatMessages.forEach(System.out::println);
    }





}
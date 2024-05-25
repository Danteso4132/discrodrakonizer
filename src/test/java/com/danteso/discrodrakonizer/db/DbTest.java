//package com.danteso.discrodrakonizer.db;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class DbTest {
//
//    @Autowired
//    ChatRepo repo;
//
//    @Test
//    public void testConnection(){
//        ChatMessage test_content = ChatMessage.builder().id("123").messageContent("Test content").build();
//
//        repo.save(test_content);
//        boolean b = repo.existsById("123");
//        //repo.deleteById("123");
//    }
//}

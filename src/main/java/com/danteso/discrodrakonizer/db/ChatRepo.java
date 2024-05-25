package com.danteso.discrodrakonizer.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends MongoRepository<ChatMessage, String> {
}

package com.danteso.discrodrakonizer.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import net.dv8tion.jda.api.entities.Message;

import java.time.OffsetDateTime;
import java.util.List;

@Document
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ChatMessage {

    @Id
    private String id;
    private String messageContent;
    private String attachedLink;
    private String authorName;
    private ImageAttachment imageAttachment;
    private String channelId;
    private OffsetDateTime timeEdited;


    public static ChatMessage buildChatMessage(Message m) {
        ChatMessage builtMessage = ChatMessage.builder()
                .id(m.getId())
                .messageContent(m.getContentRaw())
                .authorName(m.getAuthor().getEffectiveName())
                .channelId(m.getChannelId())
                .imageAttachment(new ImageAttachment(m))
                .timeEdited(m.getTimeEdited()).build();
        return builtMessage;
    }
}

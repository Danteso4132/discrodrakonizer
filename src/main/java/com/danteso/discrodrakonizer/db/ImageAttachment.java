package com.danteso.discrodrakonizer.db;

import lombok.*;
import net.dv8tion.jda.api.entities.Message;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImageAttachment {

    //TODO Change to real attachment Class
    private byte[] attachment;
    private String recognisedText;

    public ImageAttachment(Message message) {
        Optional<Attachment> firstAttachment = message.getAttachments().stream().findFirst();
        if (firstAttachment.isPresent()) {
            Attachment attachment = firstAttachment.get();
            try {
                InputStream attachmentStream = attachment.retrieveInputStream().get(); // Get input stream for attachment
                this.attachment = attachmentStream.readAllBytes(); // Convert attachment input stream to byte array
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Handle the IOException properly
            }
        }
    }
}

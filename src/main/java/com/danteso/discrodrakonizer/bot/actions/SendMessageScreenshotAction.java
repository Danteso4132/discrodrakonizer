package com.danteso.discrodrakonizer.bot.actions;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import com.danteso.discrodrakonizer.bot.MessageScreenshotter;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@EqualsAndHashCode
@ToString
public class SendMessageScreenshotAction extends AbstractOnMessageReceivedAction{

    MessageScreenshotter ms;

    private int weight = 500;

    public SendMessageScreenshotAction(DiscordBot bot, MessageScreenshotter messageScreenshotter) {
        super(bot);
        this.ms = messageScreenshotter;
        bot.addAction(this);
        System.out.println("Added SendMessageScreenshotAction");

        System.out.println("SendMessageScreenshotAction.hashCode = " + hashCode());
    }

    @Override
    public void doAction(){
        System.out.println("SendMessageScreenshotAction");
        String randomWordFromMessage = getRandomWordFromMessage(bot.getLastMessageReceivedEvent().getMessage().getContentRaw());
        formatAndSendScreenshot(bot.getLastMessageReceivedEvent(), randomWordFromMessage);
    }

    @Override
    public int getWeighting(){
        return weight;
    }



    @SneakyThrows
    private void formatAndSendScreenshot(MessageReceivedEvent event, String wordToFind) {
        List<Message> messagesInChannelBySubstring = findMessagesInChannelBySubstring(event.getChannel(), wordToFind);
        messagesInChannelBySubstring.remove(event.getMessage());
        CompletableFuture<File> future = event.getAuthor().getAvatar().downloadToFile(new File("src/main/resources/templates/user_icon.png"));
        future.exceptionally(error -> { // handle possible errors
            error.printStackTrace();
            return null;
        });
        if (!messagesInChannelBySubstring.isEmpty()) {
            Collections.shuffle(messagesInChannelBySubstring);
            Message randomMessage = messagesInChannelBySubstring.get(0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            ms.createHtmlBasedScreenshot(randomMessage.getAuthor().getEffectiveName(), randomMessage.getTimeCreated().plusHours(3).format(dtf).toString(), randomMessage.getContentRaw());
            File file = new File("generated_message.png");
            bot.sendMessageWithFile("", file);
        }
    }

    private List<Message> findMessagesInChannelBySubstring(MessageChannelUnion channel, String substring) {
        System.out.println("channel.getIterableHistory().limit = " + channel.getIterableHistory().getLimit());
        MessagePaginationAction messages = channel.getIterableHistory().limit(100);
        int messageProcessed = 0;
        substring = substring.toLowerCase(Locale.ROOT);
        substring = substring.replaceAll("[А-ЯЁ][-А-яЁё]+", ""); //TODO Fix this regex
        System.out.println("Searching for substring " + substring);
        List<Message> results = new ArrayList<>();
        if (substring.isEmpty()) {
            return results;
        }
//        String finalSubstring = substring;
//        channel.getIterableHistory().forEach(message -> {
//            messageProcessed.getAndIncrement();
//            if (messageProcessed.get() % 1000 == 0){
//                System.out.println("Processed " + messageProcessed + " messages");
//            }
//            if (!message.getContentRaw().startsWith("!") && message.getContentDisplay().contains(finalSubstring.toLowerCase(Locale.ROOT))) {
//                results.add(message);
//            }
//        });

        for (Message message : messages) {
            messageProcessed++;
            if (messageProcessed > 5000){
                return results;
            }
            if (messageProcessed % 1000 == 0){
                System.out.println("Processed " + messageProcessed + " messages");
            }
            if (!message.getContentRaw().startsWith("!") && message.getContentRaw().toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT))) {
                results.add(message);
                 if (results.size() > 10){
                    return results;
                }
            }
        }
        System.out.println("Got messages = " + results);
        return results;
    }

}

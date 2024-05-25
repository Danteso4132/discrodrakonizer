package com.danteso.discrodrakonizer.bot;

import com.danteso.discrodrakonizer.bot.actions.BotAction;
import com.danteso.discrodrakonizer.bot.actions.SendPngAction;
import com.danteso.discrodrakonizer.browser.PikabuParser;
import com.danteso.discrodrakonizer.db.ChatMessage;
import com.danteso.discrodrakonizer.db.ChatRepo;
import com.danteso.discrodrakonizer.db.ImageUtils;
import com.danteso.discrodrakonizer.utils.WeightedOperations;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class DiscordBot extends ListenerAdapter implements CommandLineRunner {


    private Set<BotAction> allBotActions = new HashSet<>();


    private MessageReceivedEvent lastMessageReceivedEvent;


    @Value("${token}")
    private String token;

    private final String TEST_CHAT_ID = "1235894103066279979";

    MessageScreenshotter messageScreenshotter;

    PikabuParser pikabuParser;

    private ChatRepo chatRepo;

    public DiscordBot(MessageScreenshotter messageScreenshotter, PikabuParser pikabuParser, ChatRepo chatRepo)
    {
        this.messageScreenshotter = messageScreenshotter;
        this.pikabuParser = pikabuParser;
        this.chatRepo = chatRepo;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!TEST_CHAT_ID.equals(event.getChannel().getId())) {
            System.out.println("Non-test chat");
            return;
        }
        if (event.getAuthor().isBot()) {
            return;
        }
        this.lastMessageReceivedEvent = event;

        if (event.getMessage().getContentRaw().startsWith("download")){
            ChatMessage cm = ChatMessage.buildChatMessage(event.getMessage());
            chatRepo.save(cm);
        }
        else if (event.getMessage().getContentRaw().startsWith("show")){
                List<ChatMessage> all = chatRepo.findAll();
                ChatMessage chatMessage = all.stream().filter(a -> a.getImageAttachment() != null).findFirst().orElse(null);
                if (chatMessage != null){
                    byte[] attachment = chatMessage.getImageAttachment().getAttachment();
                    System.out.println("found attachment");
                    ImageUtils.saveImageFromByteArray(attachment, "testimage.png", "png");
                }
        } else if (event.getMessage().getContentRaw().startsWith("getMessage")) {
            Message messageRestAction = event.getChannel().retrieveMessageById("1240242848025280532").complete();
            messageRestAction.reply("Yay").queue();
        }


        int i = ThreadLocalRandom.current().nextInt(0, 100);
        //Chance to anything happens on message received
        if (i > 80){
            getRandomBotAction().doAction();
        }


    }



    private BotAction getRandomBotAction() {
        return WeightedOperations.getRandomWeightedObject(allBotActions).get();
    }



    public void sendMessageInChat(String message){
        lastMessageReceivedEvent.getChannel().sendMessage(message).queue();
    }

    public void sendMessageWithFile(String message, File file){
        MessageCreateAction messageCreateAction = lastMessageReceivedEvent.getChannel().sendMessage(message);
        messageCreateAction.addFiles(FileUpload.fromData(file));
        messageCreateAction.queue();
    }

    @Override
    public void run(String... args) {
        startBot();
    }

    public void startBot() {
        JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.MESSAGE_CONTENT)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(this)
                .build();
    }




    public void addAction(BotAction action) {
        this.allBotActions.add(action);
    }

    public MessageReceivedEvent getLastMessageReceivedEvent() {
        return lastMessageReceivedEvent;
    }
}

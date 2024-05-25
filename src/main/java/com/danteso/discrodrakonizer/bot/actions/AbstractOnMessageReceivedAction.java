package com.danteso.discrodrakonizer.bot.actions;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ToString
public class AbstractOnMessageReceivedAction implements OnMessageReceivedAction{

    public AbstractOnMessageReceivedAction(DiscordBot bot) {
        this.bot = bot;
    }

    private int weight = 0;

    DiscordBot bot;

    @Override
    public void doAction(){

    }


    @NotNull
    protected List<String> getWordsFromMessage(String message) {
        String[] words = message.split(" ");
        List<String> wordsInMessage = new ArrayList<>();
        for (String s : words) {
            if (s.length() > 4) {
                wordsInMessage.add(s);
            }
        }
        return wordsInMessage;
    }

    protected String getRandomWordFromMessage(String message) {
        List<String> wordsInMessage = getWordsFromMessage(message);
        String s = wordsInMessage.stream().skip(ThreadLocalRandom.current().nextInt(wordsInMessage.size())).findFirst().orElse("");
        return s;

    }

    @Override
    public int getWeighting(){
        return weight;
    }
}

package com.danteso.discrodrakonizer.bot.actions;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import com.danteso.discrodrakonizer.browser.PikabuParser;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EqualsAndHashCode
@ToString
public class SendPikabuLinkAction extends AbstractOnMessageReceivedAction {

    PikabuParser pikabuParser;

    private int weight = 50;

    public SendPikabuLinkAction(DiscordBot bot, PikabuParser parser) {
        super(bot);
        this.pikabuParser = parser;
        bot.addAction(this);
        System.out.println("Added SendPikabuLinkAction");
        System.out.println("SendPikabuLinkAction.hashCode = " + hashCode());
    }

    public void doAction() {
        System.out.println("SendPikabuLinkAction");
        MessageReceivedEvent event = bot.getLastMessageReceivedEvent();
        String rawContent = event.getMessage().getContentRaw();
        String randomWord = getRandomWordFromMessage(rawContent);
        Optional<String> pikabuMemeUrlByWord = pikabuParser.getPikabuMemeUrlByWord(randomWord);
        pikabuMemeUrlByWord.ifPresent(s -> bot.sendMessageInChat(s));
    }

    @Override
    public int getWeighting() {
        return weight;
    }


}

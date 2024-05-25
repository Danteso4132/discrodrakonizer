package com.danteso.discrodrakonizer.bot.actions;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode
@ToString
public class IdleBotAction implements BotAction{

    DiscordBot bot;

    public IdleBotAction(DiscordBot bot){
        this.bot = bot;
        bot.addAction(this);
    }

    @Override
    public void doAction(){
        System.out.println("IdleBotAction");
    }

    @Override
    public int getWeighting(){
        return 50;
    }
}

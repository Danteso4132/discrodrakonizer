package com.danteso.discrodrakonizer.bot;

import com.danteso.discrodrakonizer.bot.actions.BotAction;
import com.danteso.discrodrakonizer.bot.actions.IdleBotAction;
import com.danteso.discrodrakonizer.bot.actions.SendMessageScreenshotAction;
import com.danteso.discrodrakonizer.bot.actions.SendPikabuLinkAction;
import com.danteso.discrodrakonizer.browser.PikabuParser;
import com.jayway.jsonpath.internal.Utils;
import net.dv8tion.jda.api.JDA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class DiscordBotTest {

    @Mock
    private JDA mockedJda;

    @InjectMocks
    private DiscordBot bot;

    @BeforeEach
    public void setup() {
        // Setup any necessary configurations or mocks before each test
    }

    @Test
    public void testStartBot() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DiscordBot.class.getDeclaredMethod("getRandomBotAction");
        method.setAccessible(true);

        BotAction idleAction = new IdleBotAction(bot);
        BotAction sendMessageScreenshotAction = new SendMessageScreenshotAction(bot, new MessageScreenshotter());
        BotAction pikabuAction = new SendPikabuLinkAction(bot, new PikabuParser());

        Map<BotAction, Integer> actionsDistribution = new HashMap<>();
        for (int i = 0; i < 100; i++){
            BotAction action = (BotAction) method.invoke(bot);
            if (actionsDistribution.containsKey(action)){
                actionsDistribution.put(action, actionsDistribution.get(action) + 1);
            }
            else{
                actionsDistribution.put(action, 1);
            }
        }
        System.out.println(actionsDistribution);

    }



}
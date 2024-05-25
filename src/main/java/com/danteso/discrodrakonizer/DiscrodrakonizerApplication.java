package com.danteso.discrodrakonizer;

import com.danteso.discrodrakonizer.bot.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumSet;

@SpringBootApplication
public class DiscrodrakonizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscrodrakonizerApplication.class, args);


	}

}

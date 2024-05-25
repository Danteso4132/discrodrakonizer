package com.danteso.discrodrakonizer.browser;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RandomQuery {



    public String getRandomQuery(){
        Collections.shuffle(themes);
        Collections.shuffle(types);
        return themes.get(0) + " " + types.get(0);
    }


    List<String> themes = new ArrayList<>(List.of("poe", "path of exile", "anime", "аниме", "анимэ", "pillars of eternity", "fallout 4", "warhammer 40000", "warhammer", "poe reddit", "chris wilson"));
    List<String> types = new ArrayList<>(List.of("meme", "мем", "диалог", "dialogue", "game bug", "strange", "удивление", "баг", "одноклассники", "modding", "mod", "nexus mods", "customization", "modification", "упячка", "погода", "reddit", "творог", "bashorg",  "poeninja", "маринад"));


}

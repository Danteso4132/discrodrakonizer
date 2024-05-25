package com.danteso.discrodrakonizer.bot;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageScreenshotter {


    public void createHtmlBasedScreenshot(String nickname, String date, String content){

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/main/resources/templates/messageTemplate.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String htmlTemplate = contentBuilder.toString();
        //String htmlTemplate = "<html><body><div>User: $user</div><div>Date: $date</div><div>Content: $content</div></body></html>";
        System.out.println(htmlTemplate);
        // Populate parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", nickname);
        parameters.put("date", date);
        parameters.put("content", content);

        String html = htmlTemplate;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            html = html.replace("$" + entry.getKey(), entry.getValue());
        }

        HtmlImageGenerator imageGenerator = new HtmlImageGenerator() {
            protected JEditorPane createJEditorPane() {
                JEditorPane editor = super.createJEditorPane();
                editor.setOpaque(false); // The solution
                return editor;
            }
        };
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage("generated_message.png");
    }
}

package com.danteso.discrodrakonizer.bot.actions;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import com.danteso.discrodrakonizer.browser.RandomQuery;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


@Component
@EnableScheduling
public class SendPngAction implements BotAction {

    DiscordBot bot;
    RandomQuery randomQuery;

    Date lastFiringDate = new Date(System.currentTimeMillis());


    public SendPngAction(DiscordBot bot, RandomQuery randomQuery) {
        this.bot = bot;
        this.randomQuery = randomQuery;
    }

    /**
     * Method executes every second
     *
     */
    @Scheduled(fixedRate = 60000L)
    @Override
    public void doAction() {

        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - lastFiringDate.getTime();
        long minutesFromLastMessage = TimeUnit.MILLISECONDS.toMinutes(timeDifference);

        double randomValue = ThreadLocalRandom.current().nextDouble();
        double threshold = Math.exp(-minutesFromLastMessage / 100.0); //TODO Rethink threshold calculation

        System.out.println("threshold = " + threshold);
        System.out.println("randomValue = " + randomValue);
        if (randomValue > (threshold + 0.3)) { //adding some constant thresholding to avoid spamming
            if (bot.getLastMessageReceivedEvent().getAuthor().isBot()){
                return;
            }
            lastFiringDate = new Date(currentTime);
            try {
                String imageLink = findImageLink();
                System.out.println(imageLink);
                bot.sendMessageInChat(imageLink);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String findImageLink() throws IOException {
        String searchTerm = randomQuery.getRandomQuery();
        searchTerm = searchTerm.replace(" ", "%20");
        System.out.println("Searching image for term " + searchTerm);


        String API_KEY = "AIzaSyAkKhWrM1kOXvFRN9CYlicp-_ueTx31cBs";
        String SEARCH_ENGINE_ID = "309f977372805428f";
        //String apiEndPoint = "https://serpapi.com/search.json?q=Apple&engine=google_images&ijn=0";

        Customsearch customsearch = null;


        try {
            customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    try {
                        // set connect and read timeouts
                        httpRequest.setConnectTimeout(3 * 600000);
                        httpRequest.setReadTimeout(3 * 600000);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Result> resultList = null;
        try {
            Customsearch.Cse.List list = customsearch.cse().list(searchTerm);
            list.setKey(API_KEY);
            list.setCx(SEARCH_ENGINE_ID);
            Search results = list.execute();
            resultList = results.getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(resultList);
        List<String> imageLinks = new ArrayList<>();

        for (Result result : resultList) {
            Map<String, List<Map<String, Object>>> pagemap = result.getPagemap();
            if (pagemap != null && pagemap.containsKey("cse_image")) {
                List<Map<String, Object>> images = (List<Map<String, Object>>) pagemap.get("cse_image");
                for (Map<String, Object> image : images) {
                    if (image.containsKey("src")) {
                        imageLinks.add((String) image.get("src"));
                    }
                }
            }
        }
        System.out.println(imageLinks);

        return imageLinks.get(0);
    }

//    public double calculateChance(long minutesFromLastMessage) {
//        return (1 - Math.exp(-minutesFromLastMessage / 480.0));
//    }


    private void scanDirectoryAndSendFile() {
        Optional<File> png = Stream.of(new File("png").listFiles())
                .filter(file -> (!file.isDirectory() && file.getName().endsWith(".png")))
                .findFirst();
        if (png.isPresent()) {
            png.ifPresent(file -> bot.sendMessageWithFile("", file));
            File fileToDelete = png.get();
            new Thread(() -> {
                try {
                    // Sleep for 8 seconds before deleting the file
                    Thread.sleep(8000); // 8 seconds delay
                    Files.deleteIfExists(fileToDelete.toPath());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public int getWeighting() {
        return 0;
    }


}

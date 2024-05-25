package com.danteso.discrodrakonizer.browser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class PikabuParser {


    public Optional<String> getPikabuMemeUrlByWord(String word){

        String className = "story__title";
        StringBuilder url = new StringBuilder();
        url.append("https://pikabu.ru/search?q=");
        url.append(word);
        url.append("&n=4&st=2&r=7&d=1461&D=2557");
        return Optional.ofNullable(extractHrefFromElement(url.toString(), className));
    }

//    public String parsePage(String url) {
//        try {
//            Document document = Jsoup.connect(url).get();
//            Element storyMainElement = document.getElementsByClass("story__main").first();
//
//            if (storyMainElement != null) {
//                return storyMainElement.text();
//            } else {
//                return "Element with class 'story__main' not found on the page.";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error occurred while fetching and parsing the page.";
//        }
//    }


    private String extractHrefFromElement(String url, String className) {
        try {
            Document document = Jsoup.connect(url).get();
            Element targetElement = document.selectFirst("." + className);

            if (targetElement != null) {
                Element childElement = getNextLevelChildWithHref(targetElement);

                if (childElement != null) {
                    String href = childElement.attr("href");
                    return href;
                } else {
                    System.out.println("Child element with href not found within " + className + ".");
                    return null;
                }
            } else {
                System.out.println("Element with class '" + className + "' not found on the page.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Element getNextLevelChildWithHref(Element parent) {
        Element curElement = parent;
        while (curElement != null) {
            if (!curElement.select("a[href]").isEmpty()) {
                return curElement.select("a[href]").first();
            }
            curElement = curElement.children().first();
        }
        return null;
    }
}

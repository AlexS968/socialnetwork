package main.core;

import org.jsoup.Jsoup;

public class TextUtilities {
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}

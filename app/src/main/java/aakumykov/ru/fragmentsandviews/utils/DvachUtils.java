package aakumykov.ru.fragmentsandviews.utils;

import org.jsoup.Jsoup;

public class DvachUtils {

    private DvachUtils(){}


    public static String processComment(String text) {
        text = (text + "").trim();
        text = clearThreadComment(text);
        text = br2nl(text);
        text = html2text(text);
        return text;
    }

    private static String clearThreadComment(String rawComment) {
        return rawComment.replaceFirst("<a[^>]*>.*</a>(<br>)*", "");
    }

    private static String br2nl(String inputString) {
        return inputString.replaceAll("<br>", "\n");
    }

    private static String html2text(String inputString) {
        String text = Jsoup.parse(inputString).text();
        return text;
    }
}

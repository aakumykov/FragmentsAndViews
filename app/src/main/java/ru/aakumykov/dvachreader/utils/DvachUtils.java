package ru.aakumykov.dvachreader.utils;

import org.jsoup.Jsoup;

public class DvachUtils {

    private DvachUtils(){}


    public static String preProcessComment(String text) {
        text = (text + "").trim();
        text = clearThreadComment(text);

        text = processEscapes(text);
        text = specialchars2html(text); // должна идти пред br2nl (или запихать её внутрь?)
        text = br2nl(text);

        text = html2text(text);
        return text;
    }

    private static String clearThreadComment(String rawComment) {
        return rawComment.replaceFirst("<a[^>]*>.*</a>(<br>)*", "");
    }

    private static String processEscapes(String inputString) {
        inputString = inputString.replaceAll("(\\\\r)+", "");
        inputString = inputString.replaceAll("(\\\\t)+", " ");
        inputString = inputString.replaceAll("(\\\\n)+", "\n");
        return inputString;
    }

    private static String specialchars2html(String inputString) {
        inputString = inputString.replaceAll("&amp;", "&");
        inputString = inputString.replaceAll("&lt;", "<");
        inputString = inputString.replaceAll("&gt;", ">");
        inputString = inputString.replaceAll("&nbsp;", " ");
        return inputString;
    }

    private static String br2nl(String inputString) {
        return inputString.replaceAll("<br>", "\n");
    }

    private static String html2text(String inputString) {
        String text = Jsoup.parse(inputString).text();
        return text;
    }
}

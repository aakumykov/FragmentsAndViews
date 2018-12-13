package aakumykov.ru.fragmentsandviews.utils;

public class DvachUtils {

    private DvachUtils(){}


    public static String processComment(String text) {
        text = (text + "").trim();
        text = clearThreadComment(text);
        text = br2nl(text);
        return text;
    }

    private static String clearThreadComment(String rawComment) {
        return rawComment.replaceFirst("<a[^>]*>.*</a>(<br>)*", "");
    }

    private static String br2nl(String inputString) {
        return inputString.replaceAll("<br>", "\n");
    }
}

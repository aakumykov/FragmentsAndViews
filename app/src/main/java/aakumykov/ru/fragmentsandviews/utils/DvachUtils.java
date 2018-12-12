package aakumykov.ru.fragmentsandviews.utils;

public class DvachUtils {

    private DvachUtils(){}

    public static String clearThreadComment(String rawComment) {
        rawComment += ""; // защита от NULL
        rawComment = rawComment.trim();
        return rawComment.replaceFirst("<a[^>]*>.*</a>(<br>)*", "");
    }
}

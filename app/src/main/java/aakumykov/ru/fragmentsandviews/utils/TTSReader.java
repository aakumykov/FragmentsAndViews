package aakumykov.ru.fragmentsandviews.utils;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// TODO: прикрутить сюда AAC-жизненный цикл
public class TTSReader {

    private static final String TAG = "TTSReader";

    private Context context;

    private TextToSpeech textToSpeech;
    private ArrayList<String> readingPool = new ArrayList<>();
    private boolean isActive = false;
    private int currentParagraph = 0;
    private int currentSentence = 0;


    public TTSReader(Context context) {
        this.context = context;
    }


    public void init() {

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    Locale locale = new Locale("ru");

                    int result = textToSpeech.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Извините, этот язык не поддерживается");
                    }

                } else {
                    Log.e("TTS", "Ошибка первичной настройки движка TTS.");
                }
            }
        });
    }

    public void shutdown() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    public void setText(ArrayList<String> paragraphsList) {
        readingPool.addAll(paragraphsList);
    }

    public Bundle getState() {
        Bundle state = new Bundle();
        state.putStringArrayList("POOL", readingPool);
        state.putInt("PARAGRAPH", currentParagraph);
        state.putInt("SENTENCE", currentSentence);
        state.putBoolean("IS_ACTIVE", isActive);
        return state;
    }

    public void setState(Bundle state) {
        readingPool = state.getStringArrayList("POOL");
        currentParagraph = state.getInt("PARAGRAPH", 0);
        currentSentence = state.getInt("SENTENCE", 0);
        boolean isActive = state.getBoolean("IS_ACTIVRE", false);
    }

    public void start() {
        start(0,0);
    }

    public void start(int paragraph, int sentence) {
        currentParagraph = paragraph;
        currentSentence = sentence;
        isActive = true;

        for (int i=currentParagraph; i<readingPool.size(); i++) {

            String p = readingPool.get(paragraph);

            if (null != p) {
                String[] sentences = p.split("\\.\\s+");

                int sCount = sentences.length;

                int startSentence = (currentSentence > 0 && currentSentence < sCount) ? currentSentence : 0;

                for (int j=startSentence; j<sCount; j++) {
                    String s = sentences[j];
                    read(s);
                    currentSentence++;
                }
            }
            currentParagraph++;
        }
    }

    public void stop() {
        textToSpeech.stop();
        isActive = false;
    }

    public void read(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
    }
}

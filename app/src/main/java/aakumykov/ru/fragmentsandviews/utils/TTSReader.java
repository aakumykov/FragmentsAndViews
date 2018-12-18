package aakumykov.ru.fragmentsandviews.utils;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

// TODO: прикрутить сюда AAC-жизненный цикл
public class TTSReader {

    public interface ReadingCallbacks {
        void onReadingStart();
        void onReadingStop();
        void onReadingError();
    }

    private static final String TAG = "TTSReader";

    private Context context;

    private TextToSpeech textToSpeech;
    private UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
            readingCallbacks.onReadingStart();
        }

        @Override
        public void onDone(String utteranceId) {
            sentenceNum += 1;

            if (sentenceNum >= currentParagraphSize) {
                currentParagraphNum += 1;
                sentenceNum = 0;
            }

            if (currentParagraphNum <= paragraphsList.size()) {
                continueRead();
            }
        }

        @Override
        public void onError(String utteranceId) {
            readingCallbacks.onReadingError();
        }
    };
    private ReadingCallbacks readingCallbacks;

    private ArrayList<String> paragraphsList = new ArrayList<>();
    private int currentParagraphNum = 0;
    private int currentParagraphSize = 0;
    private int sentenceNum = 0;
    private boolean isActive = false;
    private boolean textEnds = false;

    // Конструктор
    public TTSReader(Context context, ReadingCallbacks readingCallbacks) {
        this.context = context;
        this.readingCallbacks = readingCallbacks;
    }

    // Внешние методы
    public void init() {

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);

                    Locale locale = new Locale("ru");

                    int result = textToSpeech.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Извините, этот язык не поддерживается");
                        readingCallbacks.onReadingError();
                    }

                } else {
                    Log.e("TTS", "Ошибка первичной настройки движка TTS.");
                    readingCallbacks.onReadingError();
                }
            }
        });
    }

    public void shutdown() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    public void setText(ArrayList<String> paragraphsList) {
        this.paragraphsList.addAll(paragraphsList);
    }

    public Bundle getState() {
        Bundle state = new Bundle();
        state.putStringArrayList("POOL", paragraphsList);
        state.putInt("PARAGRAPH", currentParagraphNum);
        state.putInt("SENTENCE", sentenceNum);
        state.putBoolean("IS_ACTIVE", isActive);
        return state;
    }

    public void setState(Bundle state) {
        if (null != state) {
            Log.d("TTSState", "параграф5: "+state.getInt("PARAGRAPH")+", предложение5: "+state.getInt("SENTENCE"));

            paragraphsList = new ArrayList<>();
            paragraphsList = state.getStringArrayList("POOL");
            currentParagraphNum = state.getInt("PARAGRAPH", 0);
            sentenceNum = state.getInt("SENTENCE", 0);
            boolean isActive = state.getBoolean("IS_ACTIVE", false);
            if (isActive) {
                start(currentParagraphNum, sentenceNum);
            } else {
                Log.d(TAG, "not active");
            }
        }
    }

    public void start() {
        start(0,0);
    }

    public void start(int paragraphNum, int sentenceNum) {

        this.currentParagraphNum = paragraphNum;
        this.sentenceNum = sentenceNum;
        this.isActive = true;

        continueRead();
    }

    public void stop() {
        textToSpeech.stop();
        isActive = false;
        readingCallbacks.onReadingStop();
    }

    public void speak(String text) {
        // TODO: нужно, чтобы это не мешало основному тексту
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, text);
    }

    // Внутренние методы
    private void continueRead() {

        String p = paragraphsList.get(currentParagraphNum);
        if (null != p) {
            ArrayList<String> sentencesList = new ArrayList<>(Arrays.asList(p.split("[.!?]+\\s+")));

            currentParagraphSize = sentencesList.size();

            String oneSentence = sentencesList.get(sentenceNum);

            speak(oneSentence);
        }
    }
}

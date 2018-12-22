package ru.aakumykov.dvachreader.utils;

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
        void onReadingPause();
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
            currentSentenceNum += 1;

            if (currentSentenceNum >= currentParagraphSize) {
                currentParagraphNum += 1;
                currentSentenceNum = 0;
            }

            if (currentParagraphNum <= paragraphsList.size()) {
                speakNext();
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
    private int currentSentenceNum = 0;
    private boolean isActive = false;
    private boolean textEnds = false;

    // Конструктор
    public TTSReader(Context context, ReadingCallbacks readingCallbacks) {
        this.context = context;
        this.readingCallbacks = readingCallbacks;
        init();
    }

    // Внешние методы
    private void init() {

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
        state.putInt("SENTENCE", currentSentenceNum);
        state.putBoolean("IS_ACTIVE", isActive);
        return state;
    }

    public void setState(Bundle state) {
        if (null != state) {
            Log.d("TTSState", "параграф5: "+state.getInt("PARAGRAPH")+", предложение5: "+state.getInt("SENTENCE"));

            paragraphsList = new ArrayList<>();
            paragraphsList = state.getStringArrayList("POOL");
            currentParagraphNum = state.getInt("PARAGRAPH", 0);
            currentSentenceNum = state.getInt("SENTENCE", 0);
            boolean isActive = state.getBoolean("IS_ACTIVE", false);
            if (isActive) {
                start(currentParagraphNum, currentSentenceNum);
            } else {
                Log.d(TAG, "not active");
            }
        }
    }


    public void start() {
        start(0,0);
    }

    public void start(int paragraphNum, int sentenceNum) {
        if (hasText()) {
            this.currentParagraphNum = paragraphNum;
            this.currentSentenceNum = sentenceNum;
            this.isActive = true;
            speakNext();
        }
    }

    public void speak(String text) {
        // TODO: нужно, чтобы это не мешало основному тексту
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, text);
    }


    public void stop() {
        paragraphsList.clear();
        isActive = false;
        readingCallbacks.onReadingStop();
    }

    public void resume() {
        if (hasText()) {
            start(this.currentParagraphNum, this.currentSentenceNum);
        }
    }

    public void pause() {
        textToSpeech.stop();
        isActive = false;
        readingCallbacks.onReadingPause();
    }


    public boolean isActive() {
        return isActive;
    }

    public boolean hasText() {
        return (null != paragraphsList && paragraphsList.size() > 0);
    }


    public int getCurrentParagraphNum() {
        return currentParagraphNum;
    }


    // Внутренние методы
    private void speakNext() {

        String p = paragraphsList.get(currentParagraphNum);
        if (null != p) {
            ArrayList<String> sentencesList = new ArrayList<>(Arrays.asList(p.split("[.!?]+\\s+")));

            currentParagraphSize = sentencesList.size();

            String oneSentence = sentencesList.get(currentSentenceNum);

            speak(oneSentence);
        }
    }
}

package aakumykov.ru.fragmentsandviews.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MyUtils {

    private MyUtils() {}

    public static void show(View view) {
        view.setVisibility(View.VISIBLE);
    }
    public static void hide(View view) {
        view.setVisibility(View.GONE);
    }

    public static void enable(View view) {
        view.setEnabled(true);
    }
    public static void disable(View view) {
        view.setEnabled(false);
    }

    public static void showKeyboard(Context ctx, EditText editText) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, 0);
        }
    }
    public static void hideKeyboard(Context ctx, EditText editText) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}

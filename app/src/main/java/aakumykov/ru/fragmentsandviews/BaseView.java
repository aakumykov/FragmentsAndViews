package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import aakumykov.ru.fragmentsandviews.interfaces.iInformer;
import aakumykov.ru.fragmentsandviews.interfaces.iPage;
import aakumykov.ru.fragmentsandviews.utils.MyUtils;

@SuppressLint("Registered")
public class BaseView extends AppCompatActivity implements
        iPage
{

    // реализация интерфейса Страница
    @Override
    public void setPageTitle(int titleId) {
        String title = getResources().getString(titleId);
        setPageTitle(title);
    }

    @Override
    public void setPageTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.setTitle(title);
    }

    @Override
    public void activateUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }


//    // реализация интерфейса Информатор
//    @Override
//    public void showProgressMessage(int messageId) {
//        showMessage(messageId);
//        showProgressBar();
//    }
//
//    @Override
//    public void hideProgressMessage() {
//        hideMessage();
//        hideProgressBar();
//    }
//
//    @Override
//    public void showErrorMsg(int messageId) {
//        showMessage(messageId);
//        hideProgressBar();
//    }
//
//    @Override
//    public void showErrorMsg(int messageId, String messageForConsole) {
//        showMessage(messageId);
//        hideProgressBar();
//        // TODO: реализовать getTag()...
//        //Log.e(getTag(), messageForConsole);
//    }
//
//    @Override
//    public void showToast(int messageId) {
//        String text = getResources().getString(messageId);
//        showToast(text);
//    }
//
//    @Override
//    public void showToast(String message) {
//        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//
//    private void showProgressBar() {
//        ProgressBar progressBar = findViewById(R.id.progressBar);
//        if (null != progressBar) {
//            MyUtils.show(progressBar);
//        }
//    }
//
//    private void hideProgressBar() {
//        ProgressBar progressBar = findViewById(R.id.progressBar);
//        if (null != progressBar) {
//            MyUtils.hide(progressBar);
//        }
//    }
//
//    private void showMessage(int messageId) {
//        String text = getResources().getString(messageId);
//        TextView messageView = findViewById(R.id.messageView);
//        if (null != messageView) {
//            messageView.setText(text);
//            MyUtils.show(messageView);
//        }
//    }
//
//    private void hideMessage() {
//        TextView messageView = findViewById(R.id.messageView);
//        if (null != messageView) {
//            MyUtils.hide(messageView);
//        }
//    }

}

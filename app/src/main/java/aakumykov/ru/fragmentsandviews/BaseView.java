package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import aakumykov.ru.fragmentsandviews.interfaces.iPage;

@SuppressLint("Registered")
public class BaseView extends AppCompatActivity implements
        iPage
{
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

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

    @Override
    public void disactivateUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void changeMenuIcon(int menuId, int iconDrawableId) {
        if (null != menu) {
            MenuItem menuItem = menu.findItem(menuId);
            Drawable newIcon = getResources().getDrawable(iconDrawableId);

            menuItem.setIcon(newIcon);
            invalidateOptionsMenu();
        }
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

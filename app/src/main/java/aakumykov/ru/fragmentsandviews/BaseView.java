package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

@SuppressLint("Registered")
public class BaseView extends AppCompatActivity implements iPage {

    @Override
    public void setPageTitle(int titleId) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.setTitle(titleId);
    }
}

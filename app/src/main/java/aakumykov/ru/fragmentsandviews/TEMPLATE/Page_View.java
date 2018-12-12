package aakumykov.ru.fragmentsandviews.TEMPLATE;

import android.os.Bundle;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;

public class Page_View extends BaseView implements
        Page_Fragment.iInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setPageTitle(R.string.MAIN_VIEW_page_title);
    }

    @Override
    public void onButtonClicked(String message) {
        setPageTitle(message);
    }


}

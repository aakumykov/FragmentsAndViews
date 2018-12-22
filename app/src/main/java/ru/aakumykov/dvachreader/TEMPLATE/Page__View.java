package ru.aakumykov.dvachreader.TEMPLATE;

import android.os.Bundle;

import ru.aakumykov.dvachreader.BaseView;
import ru.aakumykov.dvachreader.R;

public class Page__View extends BaseView implements
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

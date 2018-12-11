package aakumykov.ru.fragmentsandviews;

import android.os.Bundle;

public class MainView extends BaseView implements
        MainFragment.iInteractionListener
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

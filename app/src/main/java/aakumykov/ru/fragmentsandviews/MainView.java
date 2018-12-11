package aakumykov.ru.fragmentsandviews;

import android.os.Bundle;

public class MainView extends BaseView implements
        MainFragment.iInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public void onButtonClicked(String message) {
        setPageTitle(message);
    }


}

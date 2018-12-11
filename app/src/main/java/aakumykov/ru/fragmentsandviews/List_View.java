package aakumykov.ru.fragmentsandviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class List_View extends BaseView implements ListFragment.iInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        setPageTitle(R.string.LIST_VIEW_page_title);
    }

    @Override
    public void onListItemClicked(int position) {
        setPageTitle("Нажата строка "+position);
    }

    @Override
    public void onListItemLongClicked(int position) {
        setPageTitle("Выбрана строка "+position);
    }
}

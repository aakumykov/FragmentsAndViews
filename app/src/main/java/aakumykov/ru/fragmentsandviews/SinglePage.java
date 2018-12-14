package aakumykov.ru.fragmentsandviews;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class SinglePage extends BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_page_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestFragment testFragment = new TestFragment();
        fragmentTransaction.add(R.id.fragment_place, testFragment);
        fragmentTransaction.commit();
    }


}

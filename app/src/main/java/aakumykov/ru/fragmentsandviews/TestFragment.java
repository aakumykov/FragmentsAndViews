package aakumykov.ru.fragmentsandviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import aakumykov.ru.fragmentsandviews.interfaces.iInformer;
import aakumykov.ru.fragmentsandviews.interfaces.iPage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class TestFragment extends Fragment {

    @BindView(R.id.button) Button button;

    private iPage page;
    private iInformer informer;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // TODO: перенести это в базовый класс
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof iPage) {
            page = (iPage) context;
        } else {
            throw new RuntimeException(context+" must implement iPage interface.");
        }

        if (context instanceof iInformer) {
            informer = (iInformer) context;
        } else {
            throw new RuntimeException(context+" must implement iInformer interface.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        informer.showToast("TestFragment стартовал");
        informer.showProgressMessage(R.string.app_name);
    }

    @Optional
    @OnClick(R.id.button)
    void hideProgressMessage() {
        informer.hideProgressMessage();
    }
}

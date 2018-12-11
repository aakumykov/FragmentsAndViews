package aakumykov.ru.fragmentsandviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class MainFragment extends BaseFragment {

    public interface iInteractions {
        void onButtonClicked(String message);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showToast(R.string.fragment_started);
    }

    @Override
    public void onStop() {
        super.onStop();
        showToast(R.string.fragment_stopped);
    }

}

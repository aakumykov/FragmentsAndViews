package aakumykov.ru.fragmentsandviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {

    public interface iInteractionListener {
        void onButtonClicked(String message);
    }


    @BindView(R.id.button) Button button;
    private iInteractionListener interactionsListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iInteractionListener) {
            interactionsListener = (iInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement iInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionsListener = null;
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


    @OnClick(R.id.button)
    void onButtonClicked() {
        interactionsListener.onButtonClicked("Привет из фрагмента!");
    }
}

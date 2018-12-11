package aakumykov.ru.fragmentsandviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import aakumykov.ru.fragmentsandviews.utils.MyUtils;

public class BaseFragment extends Fragment implements iFragment {





    @Override
    public void showProgressMessage(int messageId) {
        View view = getView();
        if (null != view) {
            String text = view.getResources().getString(messageId);

            TextView messageView = view.findViewById(R.id.messageView);
            if (null != messageView) {
                messageView.setText(text);
                MyUtils.show(messageView);
            }

            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            if (null != progressBar) {
                MyUtils.show(progressBar);
            }
        }
    }

    @Override
    public void hideProgressMessage() {
        View view = getView();
        if (null != view) {
            TextView messageView = view.findViewById(R.id.messageView);
            if (null != messageView)
                MyUtils.show(messageView);

            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            if (null != progressBar)
                MyUtils.show(progressBar);
        }
    }

    @Override
    public void showToast(int messageId) {
        View view = getView();
        if (null != view) {
            String text = view.getResources().getString(messageId);

            Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}

package aakumykov.ru.fragmentsandviews;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import aakumykov.ru.fragmentsandviews.utils.MyUtils;

public class BaseFragment extends Fragment implements iBaseFragment {

    @Override
    public void showProgressMessage(int messageId) {
        showMessage(messageId);
        showProgressBar();
    }

    @Override
    public void hideProgressMessage() {
        hideMessage();
        hideProgressBar();
    }

    @Override
    public void showErrorMsg(int messageId) {
        showMessage(messageId);
        hideProgressBar();
    }

    @Override
    public void showToast(int messageId) {
        View view = getView();
        if (null != view) {
            String text = view.getResources().getString(messageId);

            Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    private void showProgressBar() {
        View view = getView();
        if (null != view) {
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            if (null != progressBar) {
                MyUtils.show(progressBar);
            }
        }
    }

    private void hideProgressBar() {
        View view = getView();
        if (null != view) {
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            if (null != progressBar) {
                MyUtils.hide(progressBar);
            }
        }
    }

    private void showMessage(int messageId) {
        View view = getView();
        if (null != view) {
            String text = view.getResources().getString(messageId);
            TextView messageView = view.findViewById(R.id.messageView);
            if (null != messageView) {
                messageView.setText(text);
                MyUtils.show(messageView);
            }
        }
    }

    private void hideMessage() {
        View view = getView();
        if (null != view) {
            TextView messageView = view.findViewById(R.id.messageView);
            if (null != messageView) {
                MyUtils.hide(messageView);
            }
        }
    }

}

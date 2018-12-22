package ru.aakumykov.dvachreader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.aakumykov.dvachreader.interfaces.iPage;
import ru.aakumykov.dvachreader.utils.MyUtils;
import ru.aakumykov.fragmentsandviews.R;

public abstract class BaseFragment extends Fragment implements iBaseFragment {

    private iPage page;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof iPage) {
            page = (iPage) context;
        } else {
            throw new RuntimeException(context+" must implement iPage interface.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        page = null;
    }


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
    public void showErrorMsg(int messageId, String messageForConsole) {
        showMessage(messageId);
        hideProgressBar();
        // TODO: реализовать getTag()...
        //Log.e(getTag(), messageForConsole);
    }

    @Override
    public void showToast(int messageId) {
        View view = getView();
        if (null != view) {
            String text = view.getResources().getString(messageId);
            showToast(text);
        }
    }

    @Override
    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public iPage getPage() {
        return page;
    }


    public abstract void onBringToFront();

    protected abstract void setDefaultPageTitle();


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

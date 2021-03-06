package ru.aakumykov.dvachreader.TEMPLATE;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.aakumykov.dvachreader.BaseFragment;
import ru.aakumykov.fragmentsandviews.R;
import ru.aakumykov.dvachreader.models.Element;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class List_Fragment extends BaseFragment {

    public interface iInteractionListener {
        void onListItemClicked(int position);
        void onListItemLongClicked(int position);
    }

    @BindView(R.id.listView) ListView listView;

    public static final String TAG = "List_Fragment";
    private List_Adapter listAdapter;
    private List<Element> elementsList;
    private iInteractionListener interactionListener;


    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        elementsList = new ArrayList<>();
            elementsList.add(new Element("Земля"));
            elementsList.add(new Element("Вода"));
            elementsList.add(new Element("Огонь"));
            elementsList.add(new Element("Воздух"));
        listAdapter = new List_Adapter(getContext(), R.layout.list_item, elementsList);
        listView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iInteractionListener) {
            interactionListener = (iInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement iInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onBringToFront() {
        setDefaultPageTitle();
    }

    @Override
    protected void setDefaultPageTitle() {
        getPage().setPageTitle("Шаблон фрагмента со списком");
    }


    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        interactionListener.onListItemClicked(position);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        interactionListener.onListItemLongClicked(position);
        return true;
    }
}

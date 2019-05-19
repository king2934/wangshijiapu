package com.wangshijiapu.wsjp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wangshijiapu.wsjp.R;

public class UserFragment extends BaseFragment {
    private View mView;
    private ListView mListView1;
    private ListView mListView2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, null);
        mListView1 = mView.findViewById(R.id.id_user_listview_1);
        mListView2 = mView.findViewById(R.id.id_user_listview_2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1);
        adapter.add("string1");
        adapter.add("string2");
        adapter.add("string3");
        mListView1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1);
        adapter2.add("item1");
        adapter2.add("item2");
        adapter2.add("item3");
        adapter2.add("item4");
        mListView2.setAdapter(adapter2);

        return mView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}

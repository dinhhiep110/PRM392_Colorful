package com.coloful.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.coloful.R;
import com.coloful.model.Question;

import java.util.List;

public class ListViewStudySetAdapter extends BaseAdapter {
    private List<Question> itemList;
    private Activity activity;

    public ListViewStudySetAdapter(Activity activity, List<Question> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.listview_study_set_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_study_item);

        tv.setText(itemList.get(i).toString());

        return view;
    }
}
package com.itguai.blank.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.itguai.biz.App;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected LayoutInflater inflater;
    protected List<T> dataList;
    private Runnable callback;
    private boolean loadindMore;
    private boolean noMoreData;

    public BaseAdapter() {
        this.inflater = LayoutInflater.from(App.getIns());
        this.dataList = new ArrayList<T>();
    }

    public List<T> getData() {
        return dataList;
    }

    public void setList(List<T> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    protected abstract int getViewResourceId();

    protected abstract void bindView(View convertView, int position);

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= dataList.size()) {
            return null;
        }
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setOnFooterCallBack(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(getViewResourceId(), parent, false);
        }
        bindView(convertView, position);
        if (position >= dataList.size() - 1 && null != callback && !loadindMore && !noMoreData) {
            loadindMore = true;
            callback.run();
        }
        return convertView;
    }

    public void loadMoreFinished() {
        loadindMore = false;
    }

    public void noMoreData(){
        noMoreData=true;
    }

}

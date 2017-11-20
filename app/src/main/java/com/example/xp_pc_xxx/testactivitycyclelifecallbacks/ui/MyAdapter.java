package com.example.xp_pc_xxx.testactivitycyclelifecallbacks.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xp_pc_xxx.testactivitycyclelifecallbacks.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Date: 2017/11/17
 * Created by XP-PC-XXX
 */

public class MyAdapter extends RecyclerView.Adapter {
    private List<String> mData;
    private Context mContex;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(List<String> mData, Context mContex) {
        this.mData = mData;
        this.mContex = mContex;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContex).inflate(R.layout.layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).textView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_view);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
}

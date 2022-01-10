package com.thbelief.simplecountdownday.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.model.TodayModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/10 8:55 下午
 * Description:
 *
 * @author thbelief
 */
public class FragmentTodayAdapter extends RecyclerView.Adapter<FragmentTodayAdapter.MyViewHolder> {

    public TodayModel mModel;

    public FragmentTodayAdapter(TodayModel model) {
        this.mModel = model;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Application.getInstance());
        View view = inflater.inflate(R.layout.item_today, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.day.setText(mModel.getDay());
        holder.date.setText(mModel.getResult().get(position).getDate());
        holder.title.setText(mModel.getResult().get(position).getTitle());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return mModel.getResult().size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView day;
        private TextView date;
        private TextView title;
        private int pos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
        }
    }
}

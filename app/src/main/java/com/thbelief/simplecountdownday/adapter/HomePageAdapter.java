package com.thbelief.simplecountdownday.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.model.DataModel;
import com.thbelief.simplecountdownday.utils.DateUtil;
import com.thbelief.simplecountdownday.utils.ResourceHelper;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/8 11:17 下午
 * Description:
 *
 * @author thbelief
 */
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    List<DataModel> mData = new ArrayList<>();

    public HomePageAdapter(List<DataModel> list) {
        if (list == null) {
            return;
        }
        this.mData = list;
    }

    public void resetData(List<DataModel> list) {
        if (list == null) {
            return;
        }
        this.mData.clear();
        this.mData = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Application.getInstance());
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new MyViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (mData == null || position >= mData.size()) {
            return;
        }
        MyViewHodler viewHolder = (MyViewHodler) holder;
        if (R.color.blue_bg_light != mData.get(position).getColorId()) {
            viewHolder.mCardView.setCardBackgroundColor(mData.get(position).getColorId());
        }
        viewHolder.pos = position;
        viewHolder.mTitle.setText(mData.get(position).getTitle());
        viewHolder.mDate.setText(mData.get(position).getDate());
        viewHolder.mUnit.setText(transformData(mData.get(position).getUnit(), mData.get(position).getDate()));
        viewHolder.mContent.setText(mData.get(position).getContent());
        if (mData.get(position).getIsClock()) {
            viewHolder.mClock.setVisibility(View.VISIBLE);
        }
        if (mData.get(position).getIsDisplay()) {
            viewHolder.mDisplay.setVisibility(View.VISIBLE);
        }
    }

    private String transformData(int type, String dateString) {
        Date targetDate = DateUtil.getData(dateString);
        Date curDate = new Date();
        Temporal target = null;
        Temporal cur = null;
        long day = 0;
        long week = 0;
        long month = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            cur = curDate.toInstant();
            target = targetDate.toInstant();
            day = ChronoUnit.DAYS.between(cur, target);
        }
        String result = "";
        switch (type) {
            case 0:
                result = String.valueOf(day) + ResourceHelper.getString(R.string.time_unit_2);
                break;
            case 1:
                result = String.valueOf(week) + ResourceHelper.getString(R.string.time_unit_3);
                break;
            case 2:
                result = String.valueOf(month) + ResourceHelper.getString(R.string.time_unit_4);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();

    }

    private class MyViewHodler extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDate;
        private TextView mUnit;
        private TextView mContent;
        private ImageView mClock;
        private ImageView mDisplay;
        private CardView mCardView;
        private ConstraintLayout mLayout;
        private int pos;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mUnit = itemView.findViewById(R.id.unit);
            mContent = itemView.findViewById(R.id.content);
            mClock = itemView.findViewById(R.id.clock);
            mDisplay = itemView.findViewById(R.id.display);
            mCardView = itemView.findViewById(R.id.card_view);
            mLayout = itemView.findViewById(R.id.layout);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

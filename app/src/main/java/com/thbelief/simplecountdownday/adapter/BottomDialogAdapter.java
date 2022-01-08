package com.thbelief.simplecountdownday.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.utils.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/8 11:17 下午
 * Description:
 *
 * @author thbelief
 */
public class BottomDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<String> mOptions = new ArrayList<>();
    private IClick mClick;
    private final static int TYPE_ITEM = 0;
    private final static int TYPE_BOTTOM = 1;

    public BottomDialogAdapter(List<String> options, IClick click) {
        this.mOptions = options;
        this.mClick = click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Application.getInstance());
        View view = null;
        if (viewType == TYPE_ITEM) {
            view = inflater.inflate(R.layout.item_bottom_dialog, parent, false);
            return new MyViewHodler(view);
        }
        return new BottomViewHodler(inflater.inflate(R.layout.item_bottom_button, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof MyViewHodler) {
            MyViewHodler myViewHodler = (MyViewHodler) holder;
            myViewHodler.itemView.setTag(myViewHodler);
            myViewHodler.itemView.setOnClickListener(this);
            myViewHodler.mName.setText(mOptions.get(position));
            myViewHodler.pos = position;
        } else {
            BottomViewHodler viewHodler = (BottomViewHodler) holder;
            viewHodler.mCancel.setOnClickListener(this);
            viewHodler.mCancel.setTag(-2);
            viewHodler.mCancel.setText(ResourceHelper.getString(R.string.cancel));
            viewHodler.mSure.setOnClickListener(this);
            viewHodler.mSure.setTag(-1);
            viewHodler.mSure.setText(ResourceHelper.getString(R.string.sure));
        }
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof MyViewHodler) {
            MyViewHodler myViewHodler = (MyViewHodler) tag;
            mClick.itemClick(myViewHodler.pos);
        } else if (tag instanceof Integer) {
            switch ((int) tag) {
                case -1:
                    mClick.sureClick();
                    break;
                case -2:
                    mClick.cancelClick();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mOptions.size()) {
            return TYPE_ITEM;
        }
        return TYPE_BOTTOM;
    }

    private class MyViewHodler extends RecyclerView.ViewHolder {
        private TextView mName;
        private int pos;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
        }
    }

    private class BottomViewHodler extends RecyclerView.ViewHolder {
        private Button mSure;
        private Button mCancel;

        public BottomViewHodler(@NonNull View itemView) {
            super(itemView);
            mSure = itemView.findViewById(R.id.sure);
            mCancel = itemView.findViewById(R.id.cancel);
        }
    }

    @Override
    public int getItemCount() {
        return mOptions.size() + 1;
    }
}

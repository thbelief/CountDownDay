package com.thbelief.simplecountdownday.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.utils.ResourceHelper;

/**
 * Author:thbelief
 * Date:2022/1/9 12:20 上午
 * Description:
 *
 * @author thbelief
 */
public class BottomItemDecoration extends RecyclerView.ItemDecoration {
    public BottomItemDecoration() {
        super();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildLayoutPosition(view);
        if (pos == 0) {
            int padding = (int) ResourceHelper.getDimen(R.dimen.dp_10);
            view.setPaddingRelative(padding, padding, padding, padding);
            view.setBackgroundResource(R.drawable.shape_item_bottom);
        } else if (pos == parent.getChildCount() - 1) {
            int padding = (int) ResourceHelper.getDimen(R.dimen.dp_20);
            view.setPaddingRelative(padding, padding, padding, padding);
        }
    }
}

package com.dong.eleven.customnavigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ItemAdapter extends BaseItemDraggableAdapter<NavigationItem, BaseViewHolder> {

    public ItemAdapter(int layoutResId, @Nullable List<NavigationItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationItem item) {
        LinearLayout lv = helper.getView(R.id.itemLv);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
        lv.startAnimation(animation);
        ImageView imageView = helper.getView(R.id.imgIv);
        imageView.setImageResource(item.getImgSrc());
        helper.setText(R.id.nameTv, item.getName());
    }

}

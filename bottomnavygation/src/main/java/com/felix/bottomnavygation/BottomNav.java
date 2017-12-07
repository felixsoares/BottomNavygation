package com.felix.bottomnavygation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;


import com.felix.bottomnavygation.Util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07/11/2017.
 */

public class BottomNav extends LinearLayout {

    private List<ItemNav> itens;
    private OnTabSelectedListener tabSelectedListener;

    public BottomNav(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomNav(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void addItemNav(ItemNav nav) {
        if (itens == null) {
            itens = new ArrayList<>();
        }

        itens.add(nav);
    }

    public void setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public void build() {
        if (itens != null && itens.size() > 0) {
            deselectAll();

            for (int i = 0; i < itens.size(); i++) {
                final ItemNav item = itens.get(i);
                final int position = i;

                if (position == 0) {
                    item.select();
                }

                item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tabSelectedListener != null) {
                            deselectAll();
                            item.select();

                            TypedValue outValue = new TypedValue();
                            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
                            item.setBackgroundResource(outValue.resourceId);

                            tabSelectedListener.onTabSelected(position);
                        }
                    }
                });

                item.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (item.isProfile()) {
                            deselectAll();
                            item.select();

                            TypedValue outValue = new TypedValue();
                            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
                            item.setBackgroundResource(outValue.resourceId);

                            tabSelectedListener.onTabLongSelected(position);
                            return true;
                        }
                        return false;
                    }
                });

                addView(item);
            }
        }
    }

    public void dimentionsItemDp(int dp){
        Util.VALUE_SIZE = dp;
        Util.VALUE_SIZE = dp + 5;
    }

    private void deselectAll() {
        for (ItemNav itemNav : this.itens) {
            itemNav.deselect();
        }
    }

    public void updateImageProfile(String path) {
        for (ItemNav itemNav : this.itens) {
            if (itemNav.isProfile()) {
                itemNav.updatePathImageProfile(path, "", "");
            }
        }
    }

    public void selectTab(int newPosition) {
        if (tabSelectedListener != null) {

            if(itens != null && itens.size() > 0) {
                boolean found = false;

                for (int i = 0; i < itens.size(); i++) {
                    if (newPosition == i) {
                        deselectAll();
                        found = true;
                        itens.get(newPosition).select();
                    }
                }

                if (found) {
                    tabSelectedListener.onTabSelected(newPosition);
                }
            }
        }
    }

    public interface OnTabSelectedListener {

        /**
         * Called when a tab click.
         *
         * @param position The position of the tab that was selected
         */
        void onTabSelected(int position);

        /**
         * Called when a tab long click.
         *
         * @param position The position of the tab that was long selected
         */
        void onTabLongSelected(int position);
    }
}

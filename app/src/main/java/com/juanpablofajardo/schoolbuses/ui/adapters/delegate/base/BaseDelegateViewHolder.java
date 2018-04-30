package com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewType;

/**
 * Created by juan.fajardo on 7/04/2017.
 * <p>
 * All ViewHolders must extend from this.
 */

public abstract class BaseDelegateViewHolder extends RecyclerView.ViewHolder {

    public BaseDelegateViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Use this method to set all the information from the given {@link ViewType} into the ViewHolder
     *
     * @param viewType
     */
    protected abstract void onBindViewHolder(ViewType viewType);
}

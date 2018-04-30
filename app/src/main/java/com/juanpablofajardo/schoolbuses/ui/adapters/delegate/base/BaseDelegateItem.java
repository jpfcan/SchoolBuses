package com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base;


import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewDelegateAdapter;
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewType;

/**
 * Created by juan.fajardo on 7/04/2017.
 * <p>
 * All items to be used in {@link android.support.v7.widget.RecyclerView} with
 * {@link com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewDelegateAdapter}
 * must extend from this.
 */

public abstract class BaseDelegateItem implements ViewDelegateAdapter<BaseDelegateViewHolder, ViewType> {

    @Override
    public void onBindViewHolder(BaseDelegateViewHolder holder, ViewType item) {
        holder.onBindViewHolder(item);
    }

}

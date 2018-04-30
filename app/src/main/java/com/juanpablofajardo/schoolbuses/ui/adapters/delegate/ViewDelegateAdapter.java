package com.juanpablofajardo.schoolbuses.ui.adapters.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by juan.fajardo on 7/04/2017.
 * <p>
 * Interface to be used when creating an adapter for a RecyclerView.
 * To create and bind {@link RecyclerView.ViewHolder}
 */

public interface ViewDelegateAdapter<VH extends RecyclerView.ViewHolder, T extends ViewType> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH holder, T item);

}

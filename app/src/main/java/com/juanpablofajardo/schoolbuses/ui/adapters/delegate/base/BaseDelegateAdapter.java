package com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewDelegateAdapter;
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.fajardo on 7/04/2017.
 * <p>
 * All delegates adapters must extend from this, in order to have access to basic methods and implementation
 */

public class BaseDelegateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int ERROR_GETTING_ITEM = -1;

    /**
     * This list defines the possibles ViewDelegates (different ViewHolders)
     * in this adapter.
     */
    protected final SparseArrayCompat<ViewDelegateAdapter> delegates;

    /**
     * This list defines the items that are going be showing in the
     * adapter.
     */
    protected List<ViewType> items;

    /**
     * @param delegates the SparseArray to be used, just send the new {@link SparseArrayCompat} in the constructor
     * @param items    the list of items to populate the {@link RecyclerView}
     */
    public BaseDelegateAdapter(SparseArrayCompat<ViewDelegateAdapter> delegates, List<ViewType> items) {
        this.delegates = delegates;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegates.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewType item = this.items.get(position);
        delegates.get(item.getViewType()).onBindViewHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        ViewType viewType = this.items.get(position);
        return viewType.getViewType();
    }

    public void addViewTypeRange(List<? extends ViewType> itemsToAdd) {
        int pos = items.size() - 1;
        items.addAll(itemsToAdd);
        notifyItemRangeInserted(pos, itemsToAdd.size() - 1);
    }

    public void addViewTypeRangeAtPosition(List<? extends ViewType> itemsToAdd, int position) {
        items.addAll(position, itemsToAdd);
        notifyItemRangeInserted(position, itemsToAdd.size());
    }

    public void addViewTypeRangeAndClear(List<? extends ViewType> itemsToAdd) {
        items = new ArrayList<>();
        items.addAll(itemsToAdd);
        notifyDataSetChanged();
    }

    protected void removeViewType(ViewType viewTypeToRemove) {
        int position = getItemPosition(viewTypeToRemove);
        if (position != ERROR_GETTING_ITEM) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected void removeViewType(int position) {
        if (items.size() > position) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeViewTypeRange(int startingPosition, int amountToRemove) {
        if (items.size() > startingPosition && items.size() > startingPosition + amountToRemove) {
            for (int i = 0; i < amountToRemove; i ++) {
                items.remove(startingPosition);
            }
            notifyItemRangeRemoved(startingPosition, amountToRemove);
        }
    }

    public List<ViewType> getItems() {
        return items;
    }

    public void clearItems() {
        items = new ArrayList<>();
    }

    public int getItemPosition(ViewType viewType) {
        return items.indexOf(viewType);
    }

    public void notifyViewTypeChanged(ViewType itemChanged) {
        try {
            int pos = getItemPosition(itemChanged);
            if (pos != ERROR_GETTING_ITEM) {
                notifyItemChanged(pos);
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}

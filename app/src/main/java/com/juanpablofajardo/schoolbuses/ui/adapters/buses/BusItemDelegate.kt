package com.juanpablofajardo.schoolbuses.ui.adapters.buses

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.juanpablofajardo.schoolbuses.R
import com.juanpablofajardo.schoolbuses.R.id.*
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.ViewType
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base.BaseDelegateItem
import com.juanpablofajardo.schoolbuses.ui.adapters.delegate.base.BaseDelegateViewHolder
import com.squareup.picasso.Picasso
import kotterknife.bindView

/**
 * Created by Juan Pablo Fajardo Cano on 4/25/18.
 */

class BusItemDelegate(val listener: BusItemListener): BaseDelegateItem() {

    interface BusItemListener {
        fun onBusViewTypeClicked(clickedViewType: BusViewType, sharedElements: List<View>, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): BaseDelegateViewHolder = BusItemViewHolder(parent)

    inner class BusItemViewHolder(parent: ViewGroup?):
            BaseDelegateViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_bus, parent, false)) {

        val ivBusIcon: ImageView by bindView(bus_item_icon)
        val tvBusName: TextView by bindView(bus_item_name)
        val tvBusDescription: TextView by bindView(bus_item_description)

        override fun onBindViewHolder(viewType: ViewType?) {
            val busViewType = viewType as BusViewType

            //If Picasso fails to load the image from URL, for any reason, it'll use the default icon
            Picasso.with(itemView.context).load(busViewType.imageUrl).error(R.drawable.ic_default_bus).into(ivBusIcon)

            tvBusName.text = busViewType.name
            tvBusDescription.text = busViewType.description

            setTransitionNames()

            itemView.setOnClickListener({
                listener.onBusViewTypeClicked(busViewType, listOf(ivBusIcon, tvBusName, tvBusDescription), adapterPosition)
            })
        }

        private fun setTransitionNames() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivBusIcon.transitionName = itemView.resources.getString(R.string.bus_icon_transition_name_base) + adapterPosition
                tvBusName.transitionName = itemView.resources.getString(R.string.bus_name_transition_name_base) + adapterPosition
                tvBusDescription.transitionName = itemView.resources.getString(R.string.bus_description_transition_name_base) + adapterPosition
            }
        }

    }

}
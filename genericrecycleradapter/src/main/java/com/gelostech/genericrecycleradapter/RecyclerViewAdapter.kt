package com.gelostech.genericrecycleradapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 *  RecyclerViewAdapter takes 2 arguments:
 *  @param layouts -> List of layouts
 *  @param callbacks -> The AdapterCallback interface which is used to set data to the UI and handle view clicks
 */
class RecyclerViewAdapter<T>(private val layouts: List<Int>, private val callbacks: AdapterCallback<T>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder<T>>() {
    constructor(layout: Int, callbacks: AdapterCallback<T>): this(listOf(layout), callbacks)


    private val items = mutableListOf<T>()

    /**
     *  Function to add single item to the RecyclerView
     */
    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     *  Function to add a list of items to the RecyclerView
     */
    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     *  Function to clear the RecyclerView
     */
    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     *  Function to inflate the layout item
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder<T> {
        return ViewHolder(viewGroup.inflate(layouts[viewType]), callbacks)
    }

    /**
     *  Function to get the total number of items in the RecyclerView
     */
    override fun getItemCount(): Int = items.size

    /**
     *  Function to bind each item in the items list to the RecyclerView at a given position
     */
    override fun onBindViewHolder(holder: ViewHolder<T>, pos: Int) {
        holder.bind(items[pos], pos)
    }

    /**
     *  The ViewHolder class which binds the data to the RecyclerView item
     */
    class ViewHolder<T>(private val view: View, private val callbacks: AdapterCallback<T>): RecyclerView.ViewHolder(view) {
        fun bind(item: T, position: Int) {
            callbacks.bindViews(view, item, position, itemViewType)

            view.setOnClickListener {
                callbacks.onViewClicked(view, item)
            }
        }

    }
}
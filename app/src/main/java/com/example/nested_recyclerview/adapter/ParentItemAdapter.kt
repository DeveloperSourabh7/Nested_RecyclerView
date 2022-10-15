package com.example.nested_recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nested_recyclerview.R
import com.example.nested_recyclerview.model.ChildItem
import com.example.nested_recyclerview.model.ParentItem

class ParentItemAdapter(
    private val itemList: List<ParentItem>,
    private val clickListener: (clickedItem: ChildItem) -> Unit):
    RecyclerView.Adapter<ParentItemAdapter.viewHolder>(){

    // An object of RecyclerView.RecycledViewPool
    //is created to share the views
    //between the child and
    //the parent RecyclerViews
    private val viewPool=RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View= LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_parent_recycler_view,
                parent, false
            )
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        //create instance of the ParentItem
        //Class for the given position
        val parentItem = itemList[position]

        //For the created instance,
        //get the title and set it
        //as text for the Textview
        holder.parentItemTitle.text=parentItem.title

        //Create a Layout Manager
        // to assign a layout
        //to the RecyclerView

        //Here we have assigned the layout
        // as Linear layout with vertical orientation
        val layoutManager= LinearLayoutManager(
            holder.childRecyclerView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // Since this is the nested layout, so
        // to define how many child items
        // should be prefetched when the child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager.initialPrefetchItemCount= parentItem.ChildItemList.size

        //Create an instance of the child
        // item view adapter and set its
        // adapter, layoutManager and RecycleViewPool
        val childItemAdapter= ChildItemAdapter(parentItem){

        }
        holder.childRecyclerView.layoutManager=layoutManager
        holder.childRecyclerView.adapter=childItemAdapter
        holder.childRecyclerView.setRecycledViewPool(viewPool)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class viewHolder internal constructor(itemView: View):
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        var parentItemTitle: TextView = itemView.findViewById(R.id.parent_item_title)
        var childRecyclerView: RecyclerView= itemView.findViewById(R.id.child_recyclerview)
//
        override fun onClick(p0: View?) {

            }
    }

}
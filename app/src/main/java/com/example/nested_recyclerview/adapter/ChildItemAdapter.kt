package com.example.nested_recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nested_recyclerview.R
import com.example.nested_recyclerview.model.ChildItem
import com.example.nested_recyclerview.model.ParentItem
import com.example.nested_recyclerview.utiils.toPx


class ChildItemAdapter(
    private val parentItem: ParentItem,
    private val clickListener: (clickedItem: ChildItem) -> Unit) :
    RecyclerView.Adapter<ChildItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_child_recycler_view,
            viewGroup, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.progressBar.progress=30
        holder.progressBar.max=100
        if (parentItem.title=="Continue Watching" ) {
            var imageViewLayoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            imageViewLayoutParams.width = 205.toPx(holder.itemView.context)
            imageViewLayoutParams.height = 135.toPx(holder.itemView.context)
            holder.progressBar.visibility=View.VISIBLE
            holder.playicon.visibility=View.VISIBLE
        }
        else if (parentItem.title=="Favorites" ) {
            var imageViewLayoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            imageViewLayoutParams.width = 240.toPx(holder.itemView.context)
            imageViewLayoutParams.height = 135.toPx(holder.itemView.context)
        }
        else if(parentItem.title=="Serials") {
            var rootLayoutParams = holder.cvRoot.layoutParams as RecyclerView.LayoutParams
            var imageViewLayoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            var contentViewLayoutParams = holder.content.layoutParams as FrameLayout.LayoutParams
            //Changing the root layout params
            rootLayoutParams.width = 99.toPx(holder.itemView.context)
            rootLayoutParams.height = 99.toPx(holder.itemView.context)
            holder.cvRoot.radius = 52.toPx(holder.itemView.context).toFloat()
            //Changing the imageView layout params
            imageViewLayoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            imageViewLayoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            imageViewLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
            imageViewLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
            imageViewLayoutParams.topToTop = ConstraintLayout.LayoutParams.UNSET
            //Changing the content ConstraintLayout params
            contentViewLayoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        }else if (parentItem.title=="Live Tv") {
            var imageViewLayoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            imageViewLayoutParams.width = 192.toPx(holder.itemView.context)
            imageViewLayoutParams.height = 108.toPx(holder.itemView.context)
            holder.tvSeason.visibility = View.VISIBLE
            holder.tvDetails.visibility = View.VISIBLE
        }else if (parentItem.title=="Serials" || parentItem.title=="My Serials") {
            var imageViewLayoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            imageViewLayoutParams.width = 240.toPx(holder.itemView.context)
            imageViewLayoutParams.height = 135.toPx(holder.itemView.context)
            if (parentItem.title=="My Serials"){
                holder.cvRoot.setOnClickListener{
                    holder.ivPlayer.visibility = View.VISIBLE
                    clickListener(parentItem.ChildItemList[position])
                }
            }
        }
        holder.cvRoot.setOnClickListener{
//            holder.ivPlayer.visibility = View.VISIBLE
            clickListener(parentItem.ChildItemList[position])
        }

        // Create an instance of the ChildItem
        // class for the given position
        val childItem = parentItem.ChildItemList[position]

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
/** .with(), .load() and .into() is the only necessary field*/
           Glide.with(holder.imageView.context)
                .load(childItem.imageUrl) // image url
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.drawable.ic_launcher_foreground)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .dontAnimate()
                .into(holder.imageView);  // imageview object
    }

    override fun getItemCount(): Int {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return parentItem.ChildItemList.size
    }


    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView = itemView.findViewById(R.id.iv_content_image)
        var cvRoot: CardView = itemView.findViewById(R.id.root)
        var content: ConstraintLayout = itemView.findViewById(R.id.content)
        var tvSeason: TextView = itemView.findViewById(R.id.tvSeasonName)
        var tvDetails: TextView = itemView.findViewById(R.id.tvEpisodeDetails)
        var ivPlayer: ImageView = itemView.findViewById(R.id.ivPlay)
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        var playicon: ImageView = itemView.findViewById(R.id.play)
      override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }
}
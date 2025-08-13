package com.daniella.recyclerviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class PostsRvAdapter(val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostsViewHolder>() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
//Inflate from .xml
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item1, parent, false)
        return PostsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val currentPost = posts[position]

//Row and item at current positions are bound

        holder.tvTitle.text = currentPost.title
        holder.tvBody.text = currentPost.body
        holder.tvUserId.text = "User ID: ${currentPost.userId}"
        holder.cvPost.setOnClickListener{
            val intent = Intent(context,ViewPostActivity::class.java)
            intent.putExtra("POST_ID",currentPost.id)
            context.startActivity(intent)
        }

//        or
//        holder.tvUserId.text = currentPost.userId.toString()

    }
}


class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tvPostTitle)
    val tvBody = itemView.findViewById<TextView>(R.id.tvPostBody)
    val tvUserId = itemView.findViewById<TextView>(R.id.tvUserId)
    val cvPost =itemView.findViewById<CardView>(R.id.cvPost)
}

package com.app.chopechatdemo.presentation.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.databinding.ItemFriendsBinding
import java.util.*
import javax.inject.Inject

class FriendsAdapter @Inject constructor() :
    ListAdapter<User, FriendsAdapter.FriendsViewHolder>(FriendsItemDiffUtil()) {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        var binding =
            ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val media = getItem(position)
        if (media != null) {
            holder.bind(media)
        }
    }

    inner class FriendsViewHolder(private val itemFriendsBinding: ItemFriendsBinding) :
        RecyclerView.ViewHolder(itemFriendsBinding.root) {


        fun bind(user: User) {
            itemFriendsBinding.apply {
                val friendName = user.name
                tvFriendItemName.text = friendName
                tvFriendItemLatestMessage.text = user.lastMessage
                cvFriendsSection.setCardBackgroundColor(getRandomColor())
                tvContactInitial.text = user.name[0].toString()

                itemView.setOnClickListener {
                    onItemClick?.invoke(user)
                }
            }
        }
    }

    class FriendsItemDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}
package com.milantejani.theonepractical.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.milantejani.theonepractical.data.model.User
import com.milantejani.theonepractical.databinding.ItemUserListBinding
import com.zerobranch.layout.SwipeLayout

class UserListAdapter(val onUserClick: (() -> Unit)? = null,val onUserDelete: ((user: User) -> Unit)? = null) :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallBack) {

    inner class UserViewHolder(val itemBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(user: User) {
            itemBinding.txtUserName.text = "${user.firstName} ${user.lastName}"
            itemBinding.txtUserEmail.text = user.email
            itemBinding.dragItem.setOnClickListener {
                onUserClick?.invoke()
            }

            itemBinding.rightView.setOnClickListener {
                onUserDelete?.invoke(user)
            }

            itemBinding.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if (direction == SwipeLayout.LEFT && isContinuous) {
                        if (adapterPosition != RecyclerView.NO_POSITION) {

                        }
                    }
                }

                override fun onClose() {

                }

            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    object UserDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
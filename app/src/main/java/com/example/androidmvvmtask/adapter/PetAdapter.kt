package com.example.androidmvvmtask.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvmtask.PetDetailActivity
import com.example.androidmvvmtask.constant.AppConstant
import com.example.androidmvvmtask.databinding.PetItemBinding
import com.example.androidmvvmtask.extension.getContentTimeHour
import com.example.androidmvvmtask.extension.isValidTime
import com.example.androidmvvmtask.model.PetX



class PetAdapter() : ListAdapter<PetX, PetAdapter.PetViewHolder>(FlowerDiffCallback) {
    var callBackListener :((PetX) ->Unit) ? =null

    class PetViewHolder(val petItemBinding: PetItemBinding) :
        RecyclerView.ViewHolder(petItemBinding.root) {
    }

    object FlowerDiffCallback : DiffUtil.ItemCallback<PetX>() {
        override fun areItemsTheSame(oldItem: PetX, newItem: PetX): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PetX, newItem: PetX): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = PetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petX = getItem(position)
        holder.petItemBinding.myPetViewModel = petX

        holder.itemView.rootView.setOnClickListener{
            callBackListener?.invoke(petX)
        }



      /*  Glide.with(holder.itemView.context)
            .load("https://i.imgur.com/tGbaZCY.jpg")
            .placeholder(R.drawable.ic_launcher_background)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.v("vinay", "fail")
                   // holder.petItemBinding.progress.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.v("vinay", "success")
                   // holder.petItemBinding.progress.visibility = View.GONE
                    return false
                }

            })
            .into(holder.petItemBinding.flowerImage);*/
      //  holder.petItemBinding.executePendingBindings()

    }


}
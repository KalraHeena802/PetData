package com.example.androidmvvmtask.binding

import android.R
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with




@BindingAdapter("bind:setImage", requireAll = false)
fun setImages(view: ImageView, url: String) {
  //  Picasso.get().load(url).into(view)
}

/*@BindingAdapter("urlImage")
fun bindUrlImage(view: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(view)
    } else {
        view.setImageBitmap(null)
    }
}*/

@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String){
    with(this.context).load(url).into(this)
}


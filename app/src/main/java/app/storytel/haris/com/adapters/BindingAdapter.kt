package app.storytel.haris.com.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.storytel.haris.com.R
import com.bumptech.glide.Glide

@BindingAdapter("postTitle")
fun bindPostTitle(view: TextView, value: String?) {
    view.text = value ?: view.context.getString(R.string.no_title)
}

@BindingAdapter("postBody")
fun bindPostBody(view: TextView, value: String?) {
    view.text = value ?: view.context.getString(R.string.no_body)
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, value: String?) {
    if (value == null || value.isEmpty()) return
    Glide.with(view.context).load(value).into(view)
}
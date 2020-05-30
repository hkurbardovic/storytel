package app.storytel.candidate.com.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.storytel.candidate.com.R
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
    if (value == null) return
    Glide.with(view.context).load("$value.png").into(view)
}

@BindingAdapter(value = ["detailsTitle", "detailsBody"])
fun bindDetailsTitleAndBody(view: TextView, title: String?, body: String?) {
    view.text = String.format("%s\n%s", title ?: "", body ?: "")
}
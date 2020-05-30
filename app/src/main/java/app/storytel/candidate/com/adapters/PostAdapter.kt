package app.storytel.candidate.com.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.commands.ClickCommands
import app.storytel.candidate.com.commands.LaunchActivityCommand
import app.storytel.candidate.com.databinding.PostItemBinding
import app.storytel.candidate.com.details.DetailsActivity
import app.storytel.candidate.com.network.models.PhotoSchema
import app.storytel.candidate.com.network.models.PostSchema
import java.util.*

class PostAdapter(val context: Context) :
    ListAdapter<PostSchema, PostAdapter.ViewHolder>(PostDiffCallback()) {

    private val photos: ArrayList<PhotoSchema?> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        val index = Random().nextInt(photos.size - 1)
        val photo = photos[index]
        holder.apply { bind(post, photo, createClickListener(post, photo)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createClickListener(post: PostSchema?, photo: PhotoSchema?) =
        ClickCommands(
            listOf(
                LaunchActivityCommand(
                    context = context,
                    activity = DetailsActivity::class.java,
                    bundle = createBundle(post, photo)
                )
            )
        )

    private fun createBundle(post: PostSchema?, photo: PhotoSchema?) =
        Bundle().apply {
            putInt(DetailsActivity.ID_KEY, post?.id ?: 0)
            putString(DetailsActivity.TITLE_KEY, post?.title)
            putString(DetailsActivity.BODY_KEY, post?.body)
            putString(DetailsActivity.URL_KEY, photo?.url)
        }

    fun setPhotos(photos: List<PhotoSchema?>) {
        this.photos.addAll(photos)
    }

    class ViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: PostSchema?,
            photo: PhotoSchema?,
            clickListener: View.OnClickListener
        ) {
            binding.apply {
                this.post = post
                this.thumbnailUrl = photo?.thumbnailUrl
                this.clickListener = clickListener
                executePendingBindings()
            }
        }
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostSchema>() {

    override fun areItemsTheSame(oldItem: PostSchema, newItem: PostSchema): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PostSchema, newItem: PostSchema): Boolean {
        return oldItem == newItem
    }
}
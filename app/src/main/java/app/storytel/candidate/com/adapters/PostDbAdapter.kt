package app.storytel.candidate.com.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.commands.ClickCommands
import app.storytel.candidate.com.commands.LaunchActivityCommand
import app.storytel.candidate.com.database.models.Post
import app.storytel.candidate.com.databinding.PostDbItemBinding
import app.storytel.candidate.com.details.DetailsActivity
import app.storytel.candidate.com.network.models.PhotoSchema
import java.util.*

class PostDbAdapter(val context: Context) :
    PagedListAdapter<Post, PostDbAdapter.ViewHolder>(PostDbDiffCallback()) {

    private val photos: ArrayList<PhotoSchema?> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        val photo = getPhoto()

        holder.apply { bind(post, photo, createClickListener(post, photo)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostDbItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createClickListener(post: Post?, photo: PhotoSchema?) =
        ClickCommands(
            listOf(
                LaunchActivityCommand(
                    context = context,
                    activity = DetailsActivity::class.java,
                    bundle = createBundle(post, photo)
                )
            )
        )

    private fun createBundle(post: Post?, photo: PhotoSchema?) =
        Bundle().apply {
            putInt(DetailsActivity.ID_KEY, post?.id ?: 0)
            putString(DetailsActivity.TITLE_KEY, post?.title)
            putString(DetailsActivity.BODY_KEY, post?.body)
            putString(DetailsActivity.URL_KEY, photo?.url)
        }

    private fun getPhoto(): PhotoSchema? {
        if (photos.isEmpty()) return null

        val index = Random().nextInt(photos.size - 1)
        return photos[index]
    }

    fun setPhotos(photos: List<PhotoSchema?>) {
        this.photos.addAll(photos)
    }

    class ViewHolder(private val binding: PostDbItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post?,
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

private class PostDbDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
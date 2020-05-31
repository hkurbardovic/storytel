package app.storytel.haris.com.network.models

import app.storytel.haris.com.database.models.Post

data class PostSchema(
    var userId: Int?,
    var id: Int?,
    var title: String?,
    var body: String?
) {
    fun toPost(): Post? {
        val id = this.id ?: return null
        return Post(
            id,
            userId ?: 0,
            title ?: "",
            body ?: ""
        )
    }
}
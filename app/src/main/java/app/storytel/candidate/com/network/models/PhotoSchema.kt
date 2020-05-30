package app.storytel.candidate.com.network.models

import app.storytel.candidate.com.database.models.Photo

data class PhotoSchema(
    val albumId: Int?,
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
) {
    fun toPhoto(): Photo? {
        val id = this.id ?: return null
        return Photo(
            id,
            albumId ?: 0,
            title ?: "",
            url ?: "",
            thumbnailUrl ?: ""
        )
    }
}

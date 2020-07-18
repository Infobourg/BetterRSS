package org.infobourg.betterrss.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "RssFeed")
data class RssFeed(
    @Id
    val id: String = "",
    val idWorkspace: String = "",
    val idChannel: String = "",
    val idThread: String? = null,
    val link: String = "",
    val lastUpdate: LocalDateTime = LocalDateTime.now()
)

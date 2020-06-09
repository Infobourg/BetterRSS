package org.infobourg.betterrss.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

//@Document
@Document(collection = "RssFlux")
data class RssFlux(
        @Id
        var id: String,
        var idWorkspace: String="",
        var idChannel: String="",
        var idThread: String="",
        var link: String="",
        var lastUpdate: OffsetDateTime= OffsetDateTime.now()
) {
}
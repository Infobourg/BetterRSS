package org.infobourg.betterrss.repositories

import org.infobourg.betterrss.models.RssFlux
import org.springframework.data.mongodb.repository.MongoRepository

interface RssFluxRepository : MongoRepository<RssFlux, String> {
    fun findByIdChannel(idChannel: String): List<RssFlux>
    fun findByIdWorkspace(idWorkspace: String): List<RssFlux>
    fun findByIdMessage(idMessage: String): List<RssFlux>
    fun findByIdThread(idThread: String): List<RssFlux>
}
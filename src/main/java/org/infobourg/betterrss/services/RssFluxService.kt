package org.infobourg.betterrss.services

import org.infobourg.betterrss.models.RssFlux
import org.infobourg.betterrss.repositories.RssFluxRepository
import org.springframework.stereotype.Service


@Service
class RssFluxService(var rssFluxRepository: RssFluxRepository){
    fun findAll(): List<RssFlux> = rssFluxRepository.findAll()
    fun findByIdChannel(idChannel: String): List<RssFlux> = rssFluxRepository.findByIdChannel(idChannel)
    fun findByIdWorkspace(idWorkspace: String): List<RssFlux> = rssFluxRepository.findByIdWorkspace(idWorkspace)
    fun findByIdThread(idThread: String): List<RssFlux> = rssFluxRepository.findByIdThread(idThread)
}
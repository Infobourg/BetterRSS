package org.infobourg.betterrss.services

import org.infobourg.betterrss.models.RssFlux
import org.infobourg.betterrss.repositories.RssFluxRepository
import org.springframework.stereotype.Service


@Service
class RssFluxService(var rssFluxRepository: RssFluxRepository){
    fun findAll(): List<RssFlux> = rssFluxRepository.findAll()
}
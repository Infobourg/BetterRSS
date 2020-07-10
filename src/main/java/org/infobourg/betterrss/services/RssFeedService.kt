package org.infobourg.betterrss.services

import org.infobourg.betterrss.models.RssFeed
import org.infobourg.betterrss.repositories.RssFeedRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class RssFeedService(private var rssFeedRepository: RssFeedRepository){
    fun findAll(): List<RssFeed> = rssFeedRepository.findAll()
    fun existsById(id: String): Boolean = rssFeedRepository.existsById(id)
    fun existsByLink(link: String): Boolean = rssFeedRepository.existsByLink(link)
    fun findByIdWorkspace(idWorkspace: String): List<RssFeed> = rssFeedRepository.findByIdWorkspace(idWorkspace)
    fun findByIdChannel(idChannel: String): List<RssFeed> = rssFeedRepository.findByIdChannel(idChannel)
    fun findByIdThread(idThread: String): List<RssFeed> = rssFeedRepository.findByIdThread(idThread)
    fun removeByIdRssFeed(idRssFeed: String) = rssFeedRepository.deleteById(idRssFeed)
    fun removeByLink(idRssFeed: String) = rssFeedRepository.deleteByLink(idRssFeed)
}
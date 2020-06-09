package org.infobourg.betterrss.repositories

import org.infobourg.betterrss.models.RssFlux
import org.springframework.data.mongodb.repository.MongoRepository

interface RssFluxRepository : MongoRepository<RssFlux, String>
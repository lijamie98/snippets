package com.jamie.quickweb.repo

import com.jamie.quickweb.model.SiteInfo
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SiteRepository : MongoRepository<SiteInfo, String> {
    fun findOneById(id: ObjectId): SiteInfo
    override fun deleteAll()

}

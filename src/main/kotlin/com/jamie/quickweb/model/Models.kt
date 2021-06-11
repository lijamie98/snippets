package com.jamie.quickweb.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class SiteInfo(
    @Id
    val id: ObjectId = ObjectId.get(),
    val siteId: String,
    val siteName: String
)


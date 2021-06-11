package com.jamie.quickweb.api

import com.jamie.quickweb.model.SiteInfo
import com.jamie.quickweb.repo.SiteRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class QuickWebApiTest @Autowired constructor(
    private val siteRepo: SiteRepository
) {
    private val defaultSiteId = ObjectId.get()

    @BeforeEach
    fun setUp() {
        siteRepo.deleteAll()
    }

    private fun saveOneSite() = siteRepo.save(SiteInfo(defaultSiteId, "Name", "Description"))

    @Test
    fun listSites() {
        saveOneSite()
    }
}
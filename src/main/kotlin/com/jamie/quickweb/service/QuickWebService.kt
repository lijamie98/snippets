package com.jamie.quickweb.service

import com.jamie.quickweb.model.SiteInfo
import com.jamie.quickweb.repo.SiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuickWebService (@Autowired val siteRepo: SiteRepository) {
    fun listSites(): List<SiteInfo> {
        val sites = siteRepo.findAll()
        return sites
    }

    fun createSite(siteInfo: SiteInfo) : SiteInfo {
        return siteRepo.save(siteInfo)
    }
}

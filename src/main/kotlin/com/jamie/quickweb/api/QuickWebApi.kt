package com.jamie.quickweb.api

import com.jamie.quickweb.model.SiteInfo
import com.jamie.quickweb.service.QuickWebService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class QuickWebApi(
    @Autowired private val qwService: QuickWebService
) {
    @GetMapping("/quickweb/sites")
    fun listSites(): List<SiteResponse> {
        return qwService.listSites()
            .map {
                SiteResponse(it.id.toString(), it.siteId, it.siteName)
            }
    }

    @PostMapping("/quickweb/sites")
    fun createSite(@RequestBody siteRequest: SiteRequest): ResponseEntity<SiteResponse> {

        val site = qwService.createSite(SiteInfo(
            siteId = siteRequest.siteId,
            siteName = siteRequest.siteName
        ))

        return ResponseEntity(SiteResponse(site.id.toString(), site.siteId, site.siteName), HttpStatus.CREATED)
    }
}

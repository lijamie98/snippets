package com.jamie.quickweb.api

import org.jetbrains.annotations.NotNull

data class SiteResponse(
    @NotNull
    var id: String,
    @NotNull
    var siteId: String,
    @NotNull
    var siteName: String
)

data class SiteRequest(
    @NotNull
    var siteId: String,
    @NotNull
    var siteName: String
)


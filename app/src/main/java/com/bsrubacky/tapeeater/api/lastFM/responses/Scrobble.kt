package com.bsrubacky.tapeeater.api.lastFM.responses

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Scrobble(
    @field:Element var track: ScrobbleField,
    @field:Element var artist: ScrobbleField,
    @field:Element var album: ScrobbleField,
    @field:Element var albumArtist: ScrobbleField,
    @field:PropertyElement var timestamp: Long,
    @field:Element var ignoredMessage: ScrobbleField
)
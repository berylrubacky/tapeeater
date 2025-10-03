package com.bsrubacky.tapeeater.api.lastFM.responses

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Scrobbles(
    @field:Element var scrobbles: List<Scrobble>,
    @field:Attribute var accepted: Int,
    @field:Attribute var ignored: Int
): LastFMResponse

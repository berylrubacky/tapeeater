package com.bsrubacky.tapeeater.api.lastFM.responses

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Session(
    @field:PropertyElement var name: String,
    @field:PropertyElement var key: String,
    @field:PropertyElement var subscriber: Int
) : LastFMResponse
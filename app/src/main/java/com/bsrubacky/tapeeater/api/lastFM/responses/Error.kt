package com.bsrubacky.tapeeater.api.lastFM.responses

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Error (
    @field:Attribute var code: Int,
    @field:TextContent var message: String
): LastFMResponse
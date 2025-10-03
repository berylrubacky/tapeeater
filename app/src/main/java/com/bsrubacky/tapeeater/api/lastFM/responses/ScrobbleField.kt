package com.bsrubacky.tapeeater.api.lastFM.responses

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class ScrobbleField(
    @field:Attribute var corrected: Int,
    @field:TextContent var value: String
)

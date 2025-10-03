package com.bsrubacky.tapeeater.api.lastFM.responses;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.ElementNameMatcher;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "lfm")
public class ScrobbleResponse {
    @Attribute
    public String status;

    @Element(
            typesByElement = {
                    @ElementNameMatcher(type = Error.class),
                    @ElementNameMatcher(type = Scrobbles.class)
            }
    )
    public LastFMResponse response;
}

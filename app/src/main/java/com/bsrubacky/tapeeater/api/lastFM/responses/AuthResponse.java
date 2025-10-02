package com.bsrubacky.tapeeater.api.lastFM.responses;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.ElementNameMatcher;

public class AuthResponse {
    @Element(
            typesByElement = {
                    @ElementNameMatcher(type = Error.class),
                    @ElementNameMatcher(type = Session.class)
            }
    )
    public LastFMResponse response;
}

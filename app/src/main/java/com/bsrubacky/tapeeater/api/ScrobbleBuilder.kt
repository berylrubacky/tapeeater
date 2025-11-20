package com.bsrubacky.tapeeater.api

import android.content.Context
import com.bsrubacky.tapeeater.database.entities.Track

abstract class ScrobbleBuilder<T>(var tracks: List<Track>) {
    abstract fun build(context: Context): List<ApiRequest<T,*>>
}

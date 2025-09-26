package com.bsrubacky.tapeeater


import com.bsrubacky.tapeeater.ui.MediaDetailScreenTests
import com.bsrubacky.tapeeater.ui.MediaListScreenTests
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MediaListScreenTests::class,
    MediaDetailScreenTests::class
)
class UITestSuite
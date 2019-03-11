package au.com.pjwin.shiftmanager.util

import android.support.annotation.VisibleForTesting
import au.com.pjwin.commonlib.Common
import au.com.pjwin.shiftmanager.BuildConfig

@VisibleForTesting
open class TestConfig : Common.Config {

    override fun host() = "0.0.0.0"

    override fun port(): Int = 8081

    override fun schema(): String = "http"

    override fun contextRoot() = BuildConfig.CONTEXT_ROOT

    override fun readTimeout(): Long = 10

    override fun connectionTimeout(): Long = 10

    override fun debug(): Boolean = BuildConfig.DEBUG
}
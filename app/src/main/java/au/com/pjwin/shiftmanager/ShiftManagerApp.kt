package au.com.pjwin.shiftmanager

import android.app.Application
import au.com.pjwin.commonlib.Common
import au.com.pjwin.shiftmanager.util.AppConfig

open class ShiftManagerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initCommon()
    }

    protected open fun initCommon() {
        Common.init(applicationContext, AppConfig())
    }
}
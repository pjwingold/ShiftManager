package au.com.pjwin.shiftmanager

import au.com.pjwin.commonlib.Common
import au.com.pjwin.shiftmanager.util.AppConfig

class ShiftManagerTestApp : ShiftManagerApp() {

    override fun initCommon() {
        Common.init(applicationContext, AppConfig(), true)//todo change to TestConfig
    }
}
package au.com.pjwin.shiftmanager

import au.com.pjwin.commonlib.Common
import au.com.pjwin.shiftmanager.util.TestConfig

class ShiftManagerTestApp : ShiftManagerApp() {

    override fun initCommon() {
        Common.init(applicationContext, TestConfig(), true)
    }
}
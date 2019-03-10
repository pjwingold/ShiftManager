package au.com.pjwin.shiftmanager

import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AppTestRunner::class)
@Config(application = ShiftManagerTestApp::class, sdk = [27])
abstract class BaseTest {

    open fun setup() {

    }

    fun tearDown() {

    }
}
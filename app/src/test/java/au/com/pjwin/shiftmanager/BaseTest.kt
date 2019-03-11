package au.com.pjwin.shiftmanager

import android.support.annotation.CallSuper
import au.com.pjwin.commonlib.util.MockResource
import io.mockk.MockKAnnotations
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AppTestRunner::class)
@Config(application = ShiftManagerTestApp::class, sdk = [27])
abstract class BaseTest {

    protected var mockServer: MockWebServer? = null

    @CallSuper
    @Before
    open fun setup() {
        MockKAnnotations.init(this)
    }

    @CallSuper
    open fun init() {
        mockServer = MockResource.initMockWebServer()
    }

    @After
    @Throws
    fun after() {
        mockServer?.shutdown()
    }
}
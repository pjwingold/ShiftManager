package au.com.pjwin.shiftmanager.viewmodel

import au.com.pjwin.commonlib.util.MockResource
import au.com.pjwin.shiftmanager.BaseTest
import au.com.pjwin.shiftmanager.model.AddRequest
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ShiftViewModelTest : BaseTest() {

    @SpyK
    private var viewModel = ShiftViewModel()

    @Before
    override fun init() {
        super.init()
        every { viewModel.onData(any()) } just runs
        every { viewModel.onError(any()) } just runs
    }

    @Test
    fun getShifts() {
        val value = MockResource.loadResourceToString("search_response.json")
        val response = MockResource.initMockResponse(value)
        mockServer?.enqueue(response)
        viewModel.getShifts()

        verify(atMost = 1) { viewModel.onData(any()) }
    }

    @Test
    fun getShiftsFail() {
        val value = MockResource.loadResourceToString("server_error.json")
        val response = MockResource.initMockResponse(value, 500)
        mockServer?.enqueue(response)
        viewModel.getShifts()

        verify(atMost = 1) { viewModel.onError(any()) }
    }

    @Test
    fun addStartShift() {
        val value = MockResource.loadResourceToString("success.json")
        val response = MockResource.initMockResponse(value)
        mockServer?.enqueue(response)
        viewModel.startShift(AddRequest())

        viewModel.addResponse.observeForever {
            assertTrue(it?.getContentIfNotHandled()?.status == true)
        }
    }

    @Test
    fun addStartShiftFail() {
        val value = MockResource.loadResourceToString("server_error.json")
        val response = MockResource.initMockResponse(value, 500)
        mockServer?.enqueue(response)
        viewModel.startShift(AddRequest())

        viewModel.addResponse.observeForever {
            assertTrue(it?.getContentIfNotHandled()?.status == false)
        }
    }

    @Test
    fun addEndShift() {
        val value = MockResource.loadResourceToString("success.json")
        val response = MockResource.initMockResponse(value)
        mockServer?.enqueue(response)
        viewModel.endShift(AddRequest())

        viewModel.addResponse.observeForever {
            assertTrue(it?.getContentIfNotHandled()?.status == true)
        }
    }

    @Test
    fun addEndShiftFail() {
        val value = MockResource.loadResourceToString("server_error.json")
        val response = MockResource.initMockResponse(value, 500)
        mockServer?.enqueue(response)
        viewModel.endShift(AddRequest())

        viewModel.addResponse.observeForever {
            assertTrue(it?.getContentIfNotHandled()?.status == false)
        }
    }
}
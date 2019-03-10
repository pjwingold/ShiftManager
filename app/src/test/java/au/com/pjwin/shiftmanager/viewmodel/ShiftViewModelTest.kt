package au.com.pjwin.shiftmanager.viewmodel

import au.com.pjwin.shiftmanager.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.Test

class ShiftViewModelTest : BaseTest() {

    @SpyK
    private var viewModel = ShiftViewModel()

    override fun setup() {
        super.setup()
        every { viewModel.onData(any()) } just runs
        every { viewModel.onError(any()) } just runs
    }

    @Test
    fun getShifts() {
        viewModel.getShifts()

        verify(atMost = 1) { viewModel.onData(any()) }
    }

    @Test
    fun addStartShift() {
    }

    @Test
    fun addEndShift() {
    }
}
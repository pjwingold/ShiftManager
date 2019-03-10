package au.com.pjwin.shiftmanager.viewmodel

import android.arch.lifecycle.MutableLiveData
import au.com.pjwin.commonlib.util.LDEvent
import au.com.pjwin.commonlib.viewmodel.DataViewModel
import au.com.pjwin.shiftmanager.model.AddRequest
import au.com.pjwin.shiftmanager.model.AddResponse
import au.com.pjwin.shiftmanager.model.Shift
import au.com.pjwin.shiftmanager.repo.AppRepo

class ShiftViewModel : DataViewModel<List<Shift>>() {

    //fire a single event to be consumed
    internal val addResponse: MutableLiveData<LDEvent<AddResponse>> by lazy {
        MutableLiveData<LDEvent<AddResponse>>()
    }

    fun getShifts() {
        launchJob {
            val response = executeAwait({ performGetShifts() },
                    { onError(it) },
                    true)

            if (viewModelJob.isActive) {
                onData(response)
            }
        }
    }

    fun startShift(startRequest: AddRequest) {
        launchJob {
            val startJob = executeAsync({ addStartShiftAsync(startRequest) },
                    {
                        addResponse.postValue(LDEvent(AddResponse(true)))
                    },
                    {
                        addResponse.postValue(LDEvent(AddResponse(false, AddShiftException(it.message))))
                    },
                    true)
            startJob.await()
        }
    }

    fun endShift(endRequest: AddRequest) {
        launchJob {
            executeAwait({ addEndShiftAsync(endRequest) },
                    {
                        addResponse.postValue(LDEvent(AddResponse(false, EndShiftException(it.message))))
                    },
                    true)

            if (viewModelJob.isActive) {
                addResponse.postValue(LDEvent(AddResponse(true)))
            }
        }
    }

    private suspend fun performGetShifts() =
            AppRepo.shiftService().getShiftsAsync().await()

    private suspend fun addStartShiftAsync(startRequest: AddRequest) =
            AppRepo.shiftService().addStartShiftAsync(startRequest).await()

    private suspend fun addEndShiftAsync(endRequest: AddRequest) =
            AppRepo.shiftService().addEndShiftAsync(endRequest).await()

    inner class AddShiftException @JvmOverloads constructor(message: String? = null) : Exception(message)
    inner class EndShiftException @JvmOverloads constructor(message: String? = null) : Exception(message)
}
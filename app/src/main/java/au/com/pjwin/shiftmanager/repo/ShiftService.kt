package au.com.pjwin.shiftmanager.repo

import au.com.pjwin.shiftmanager.model.AddRequest
import au.com.pjwin.shiftmanager.model.AddResponse
import au.com.pjwin.shiftmanager.model.Shift
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShiftService {

    @GET("shifts")
    fun getShiftsAsync(): Deferred<List<Shift>>

    @POST("shift/start")
    fun addStartShiftAsync(@Body request: AddRequest): Deferred<String>

    @POST("shift/end")
    fun addEndShiftAsync(@Body request: AddRequest): Deferred<String>
}
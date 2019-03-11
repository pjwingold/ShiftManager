package au.com.pjwin.shiftmanager.model

import com.google.gson.annotations.SerializedName

data class AddResponse(
        val status: Boolean,
        val throwable: Throwable? = null)

data class Shift(
        val id: Int,
        val start: String,
        val end: String,
        val startLat: String,
        val startLng: String,
        val endLat: String,
        val endLng: String,
        @SerializedName("image") val imageUrl: String
)

data class RequestDatetime(
        var year: Int = 0,
        var month: Int = 0,
        var day: Int = 0,
        var hour: Int = 0,
        var minute: Int = 0
) {
    override fun toString() =
    //month matches that of Calendar
            "$year-${month + 1}-${day}T$hour:$minute:00"

    fun getDate() = "$day-${month + 1}-$year"

}

data class AddRequest(var time: String = "",
                      var latitude: String = "0.0000",
                      var longitude: String = "0.0000")
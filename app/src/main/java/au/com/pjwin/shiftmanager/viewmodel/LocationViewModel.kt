package au.com.pjwin.shiftmanager.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import au.com.pjwin.commonlib.Common
import au.com.pjwin.commonlib.util.Pref
import au.com.pjwin.commonlib.util.Util
import au.com.pjwin.commonlib.util.get
import au.com.pjwin.shiftmanager.model.LatLng
import au.com.pjwin.shiftmanager.util.PrefKey
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//todo refactor to common
class LocationViewModel : ViewModel() {

    val locationData = MutableLiveData<LatLng?>()

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(Util.context())
    }

    fun getDeviceLocation() {
        try {
            if (Pref.SHARED_PREF[PrefKey.HAS_LOCATION_PERM, false] == true) {
                Common.cachedThreadPool.execute {
                    fusedLocationProviderClient.lastLocation
                        .addOnSuccessListener {
                            locationData.postValue(LatLng(it.latitude.toString(), it.longitude.toString()))
                        }
                        .addOnFailureListener {
                            clearLocation()
                        }
                }
            }

        } catch (e: SecurityException) {
            clearLocation()
        }
    }

    private fun clearLocation() {
        locationData.postValue(null)
    }
}
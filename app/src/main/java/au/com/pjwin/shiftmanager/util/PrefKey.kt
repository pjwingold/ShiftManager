package au.com.pjwin.shiftmanager.util

import au.com.pjwin.commonlib.util.Pref

enum class PrefKey() : Pref.Key {
    HAS_LOCATION_PERM;

    override var value: String = name
}
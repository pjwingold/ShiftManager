package au.com.pjwin.shiftmanager.repo

import au.com.pjwin.commonlib.repo.retrofit.RetrofitRepo

object AppRepo {

    fun shiftService(): ShiftService = RetrofitRepo.RETROFIT_BASIC_AUTH.create(ShiftService::class.java)
}
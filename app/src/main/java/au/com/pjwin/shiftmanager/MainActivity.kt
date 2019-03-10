package au.com.pjwin.shiftmanager

import android.os.Bundle
import au.com.pjwin.commonlib.ui.BaseFragment
import au.com.pjwin.commonlib.ui.SwipeRefreshActivity
import au.com.pjwin.commonlib.ui.ViewActivity
import au.com.pjwin.commonlib.util.Util
import au.com.pjwin.shiftmanager.ui.ShiftListFragment

class MainActivity : ViewActivity(), SwipeRefreshActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Util.color(R.color.colorPrimary)
    }

    //pull down refresh
    override fun performRefresh(force: Boolean) {
        val fragment = getCurrentFragmentNav<BaseFragment<*, *, *>>()

        when (fragment) {
            is ShiftListFragment -> fragment.getShiftsOnReady()
        }
    }
}

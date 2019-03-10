package au.com.pjwin.shiftmanager.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import androidx.navigation.findNavController
import au.com.pjwin.commonlib.Common
import au.com.pjwin.commonlib.extension.baseActivity
import au.com.pjwin.commonlib.ui.BaseFragment
import au.com.pjwin.commonlib.util.LDEvent
import au.com.pjwin.shiftmanager.R
import au.com.pjwin.shiftmanager.databinding.ShiftListFragmentBinding
import au.com.pjwin.shiftmanager.model.AddResponse
import au.com.pjwin.shiftmanager.model.Shift
import au.com.pjwin.shiftmanager.viewmodel.ShiftViewModel

class ShiftListFragment : BaseFragment<List<Shift>, ShiftViewModel, ShiftListFragmentBinding>() {

    private lateinit var shiftAdapter: ShiftAdapter
    private lateinit var shiftList: List<Shift>

    private val responseObserver = Observer<LDEvent<AddResponse>> { resp ->
        val content = resp?.peekContent()
        content?.let {
            if (it.status) {
                getShiftsOnReady()
            }
        }
    }

    override fun pageTitle() = R.string.app_name

    override fun layoutId() = R.layout.shift_list_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shiftList = listOf()

        getShiftsOnReady()

        viewModel.addResponse.observe(this, responseObserver)
    }

    //only make a request after screen fully loaded
    //does not make a request on normal go back
    fun getShiftsOnReady() {
        Common.uiHandler.post {
            viewModel.getShifts()
        }
    }

    override fun isFragmentObserver() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.enableRefreshing(true)

        binding.apply {
            addShiftButton.setOnClickListener {
                it.findNavController().navigate(R.id.add_shift)
            }
        }

        binding.shiftListView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            shiftAdapter = ShiftAdapter(context, shiftList)
            adapter = shiftAdapter
        }
    }

    override fun onData(data: List<Shift>?) {
        data?.let {
            shiftList = it
            shiftAdapter.list = it
            binding.shiftListView.layoutManager?.scrollToPosition(0)
        }
    }
}
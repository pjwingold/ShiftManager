package au.com.pjwin.shiftmanager.ui


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import au.com.pjwin.commonlib.extension.baseActivity
import au.com.pjwin.commonlib.ui.BaseFragment
import au.com.pjwin.commonlib.util.DateUtil
import au.com.pjwin.commonlib.util.LDEvent
import au.com.pjwin.shiftmanager.R
import au.com.pjwin.shiftmanager.databinding.ShiftAddFragmentBinding
import au.com.pjwin.shiftmanager.model.AddRequest
import au.com.pjwin.shiftmanager.model.AddResponse
import au.com.pjwin.shiftmanager.model.RequestDatetime
import au.com.pjwin.shiftmanager.model.Shift
import au.com.pjwin.shiftmanager.viewmodel.ShiftViewModel
import java.util.Calendar


class ShiftAddFragment : BaseFragment<List<Shift>, ShiftViewModel, ShiftAddFragmentBinding>() {

    //whether start or end shift
    private var isStart = true
    private val calender: Calendar by lazy {
        Calendar.getInstance()
    }
    private lateinit var startDatetime: RequestDatetime

    private val responseObserver = Observer<LDEvent<AddResponse>> { resp ->
        val content = resp?.getContentIfNotHandled()
        content?.let {
            if (it.status) {
                //go back on success
                findNavController(this).popBackStack()

            } else {
                onError(it.throwable)
            }
        }
    }

    override fun pageTitle() = R.string.add_shift_screen

    override fun layoutId() = R.layout.shift_add_fragment

    override fun isFragmentObserver() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.addResponse.observe(this, responseObserver)
        //todo maybe limit to future date
        //calender.add(Calendar.HOUR, -1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //no pull down refresh here
        baseActivity.enableRefreshing(false)

        startDatetime = RequestDatetime(
                hour = calender.get(Calendar.HOUR_OF_DAY),
                minute = calender.get(Calendar.MINUTE))

        binding.apply {
            startDateValue.setOnClickListener { datePickerDialog(it) }
            startTimeValue.setOnClickListener { timePickerDialog(it) }

            currentTime = DateUtil.formatTime24(calender.time)
            shiftSelect.setOnCheckedChangeListener { _, checkedId ->
                isStart = checkedId == R.id.start_radio
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_shift_tick -> {
            addShift()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun addShift() {
        if (startDatetime.year == 0) {
            showError(R.string.add_shift_error_no_date)

        } else {
            if (isStart) {
                viewModel.startShift(AddRequest(startDatetime.toString()))

            } else {
                viewModel.endShift(AddRequest(startDatetime.toString()))
            }
        }

    }

    override fun onError(throwable: Throwable?) {
        when (throwable) {
            is ShiftViewModel.AddShiftException ->
                showError(R.string.add_shift_error_start)

            is ShiftViewModel.EndShiftException ->
                showError(R.string.add_shift_error_end)

            else -> super.onError(throwable)
        }
    }

    private fun datePickerDialog(view: View) {
        val dialog = DatePickerDialog(view.context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    Log.d(TAG, "$year $month $dayOfMonth")

                    startDatetime.apply {
                        this.year = year
                        this.month = month
                        this.day = dayOfMonth
                    }

                    binding.startDateValue.text = startDatetime?.getDate()
                },
                calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH))

        //todo maybe limit to future date
        //dialog.datePicker.minDate = calender.timeInMillis
        dialog.show()
    }

    private fun timePickerDialog(view: View) {
        val dialog = TimePickerDialog(view.context,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    Log.d(TAG, "$hourOfDay $minute")
                    startDatetime.apply {
                        this.hour = hourOfDay
                        this.minute = minute
                    }
                },
                calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true)
        //dialog.tim.minDate = calender.timeInMillis
        dialog.show()
    }
}
package au.com.pjwin.shiftmanager.ui

import android.content.Context
import au.com.pjwin.commonlib.ui.adapter.RecyclerListAdapter
import au.com.pjwin.commonlib.ui.adapter.RecyclerViewHolder
import au.com.pjwin.shiftmanager.R
import au.com.pjwin.shiftmanager.databinding.ShiftItemViewBinding
import au.com.pjwin.shiftmanager.model.Shift

class ShiftAdapter(context: Context, shiftList: List<Shift>) :

    RecyclerListAdapter<Shift, ShiftItemViewBinding, RecyclerViewHolder>(context, shiftList) {
    override fun layoutId() = R.layout.shift_item_view

    override fun bindData(binding: ShiftItemViewBinding, data: Shift) {
        binding.shift = data
        //todo replace 'T' in date time
    }
}
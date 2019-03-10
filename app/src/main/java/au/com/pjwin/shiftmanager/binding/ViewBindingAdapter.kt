package au.com.pjwin.shiftmanager.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

class ViewBindingAdapter {

    companion object {

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun ImageView.setImageUrl(imageUrl: String) {
            Glide.with(context).load(imageUrl).into(this)
        }
    }
}
package net.sarangnamu.common

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 3.. <p/>
 */

class ViewModelBase(app: Application) : AndroidViewModel(app) {
    var weakActivity: WeakReference<FragmentActivity>? = null

    fun setActivity(activity: FragmentActivity) {
        weakActivity = WeakReference(activity)
    }

    fun getActivity() : FragmentActivity? {
        return weakActivity?.get()
    }
}
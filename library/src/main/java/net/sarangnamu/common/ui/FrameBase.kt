package net.sarangnamu.common.ui

import android.content.Context
import android.os.Build
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import net.sarangnamu.common.BkDim

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

abstract class FrameBase : FrameLayout {
    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initLayout()
    }

    protected fun setLayoutListener() {
        // lambda 로 listener 를 등록한 이후 this 로 했을 때 parent class 가 return 되는 문제 있음
        // 해결방법은 object 형태로 생성해서 전달하면 됨
//        viewTreeObserver.addOnGlobalLayoutListener({
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                viewTreeObserver.removeOnGlobalLayoutListener(this)
//            } else {
//                viewTreeObserver.removeGlobalOnLayoutListener(this)
//            }
//        })

        viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }

                globalLayout()
            }
        })
    }

    open fun globalLayout() { }

    protected fun string(@StringRes resid: Int) = context.resources.getString(resid)

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // abstract
    //
    ////////////////////////////////////////////////////////////////////////////////////

    abstract fun initLayout()
}
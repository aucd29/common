/*
 * Copyright (C) Hanwha S&C Ltd., 2017. All rights reserved.
 *
 * This software is covered by the license agreement between
 * the end user and Hanwha S&C Ltd., and may be
 * used and copied only in accordance with the terms of the
 * said agreement.
 *
 * Hanwha S&C Ltd., assumes no responsibility or
 * liability for any errors or inaccuracies in this software,
 * or any consequential, incidental or indirect damage arising
 * out of the use of the software.
 */

package net.sarangnamu.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

val FragmentManager.frgmts: List<Fragment>
    get() = (0..backStackEntryCount - 1).map { findFragmentByTag(getBackStackEntryAt(it).name) }

val FragmentManager.current: Fragment
    get() = frgmts.last()

val FragmentManager.count: Int
    get() = backStackEntryCount

fun FragmentManager.add(id: Int, clazz: Class<out Any>, listener: ((FragmentManager, FragmentTransaction) -> Unit)? = null) {
    val frgmt = clazz.newInstance() as Fragment
    val transaction = beginTransaction()

    if (frgmt.isVisible) {
        return
    }

    listener?.invoke(this, transaction)

    transaction.add(id, frgmt, frgmt.javaClass.name)
    transaction.commit()
}

fun FragmentManager.replace(id: Int, clazz: Class<out Any>, listener: ((FragmentManager, FragmentTransaction) -> Unit)? = null,
                            bundle: Bundle? = null, stack: Boolean = true): Fragment {
    val existFrgmt = findFragmentByTag(clazz.name)
    if (existFrgmt != null && existFrgmt.isVisible) {
        // manager 내에 해당 fragment 가 이미 존재하면 해당 fragment 를 반환 한다
        return existFrgmt
    }

    val frgmt = clazz.newInstance() as Fragment
    val transaction = beginTransaction()

    bundle?.let { frgmt.arguments = it }
    listener?.invoke(this, transaction)

    transaction.replace(id, frgmt, frgmt.javaClass.name)
    if (stack) {
        transaction.addToBackStack(frgmt.javaClass.name)
    }

    transaction.commit()

    return frgmt
}

fun FragmentManager.pop() {
    popBackStack()
}

fun FragmentManager.popAll() {
    (0..count - 1).map { popBackStack(it, FragmentManager.POP_BACK_STACK_INCLUSIVE) }
}

val FragmentTransaction.ANI_HORIZONTAL: Int
    get() = 1
val FragmentTransaction.ANI_VERTICAL: Int
    get() = 2

fun FragmentTransaction.animate(anim: Int) {
    when (anim) {
        ANI_HORIZONTAL -> setCustomAnimations(R.anim.slide_in_current, R.anim.slide_in_next,
                R.anim.slide_out_current, R.anim.slide_out_prev)
        ANI_VERTICAL   -> setCustomAnimations(R.anim.slide_up_current, R.anim.slide_up_next,
                R.anim.slide_down_current, R.anim.slide_down_prev)
        else -> setCustomAnimations(0, 0, 0, 0)
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// Fragment
//
////////////////////////////////////////////////////////////////////////////////////

abstract class FrgmtBase : Fragment() {
    protected lateinit var base: ViewGroup

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val id = layoutId()
        if (id == 0) {
            base = defaultView()
        } else {
            base = inflater!!.inflate(id, container!!) as ViewGroup
        }

        base.isClickable = true
        base.setBackgroundColor(0xffffff)

        initLayout()

        return base
    }

    protected fun defaultView() : ViewGroup {
        val view = LinearLayout(activity)
        view.lpmm()
        view.gravity = Gravity.CENTER

        val text = TextView(activity)
        text.gravityCenter()
        text.text = javaClass.simpleName

        view.addView(text)

        return view
    }

    open fun defaultMessage() : String {
        return javaClass.simpleName
    }

    abstract fun layoutId(): Int
    abstract fun initLayout()
}

abstract class RuleFrgmtBase: FrgmtBase() {
    private val log = LoggerFactory.getLogger(RuleFrgmtBase::class.java)

    companion object {
        protected val PREFIX = "view_"
        protected val SUFFIX = "Frgmt"
        protected val LAYOUT = "layout"
    }

    lateinit var className: String

    override fun defaultMessage(): String {
        return super.defaultMessage() + "\nFILE NOT FOUND (${inflateName()}.xml)"
    }

    override fun layoutId(): Int {
        val name = prefix() + inflateName()

        if (log.isDebugEnabled) {
            log.debug("INFLATE NAME : $name")
        }

        return resources.getIdentifier(name, LAYOUT, activity.packageName)
    }

    fun inflateName(): String {
        if (!TextUtils.isEmpty(className)) {
            return className
        }

        val name = javaClass.simpleName.replace(suffix(), "")
        className = name.get(0).toLowerCase().toString()

        name.substring(1, name.length).forEach {
            if (it.isUpperCase()) {
                className += "_" + it.toLowerCase()
            } else {
                className += it
            }
        }

        return className
    }

    open fun prefix(): String {
        return PREFIX
    }

    open fun suffix(): String {
        return SUFFIX
    }
}
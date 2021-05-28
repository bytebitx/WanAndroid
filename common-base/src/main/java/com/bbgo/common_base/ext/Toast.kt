package com.bbgo.common_base.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bbgo.common_base.RootApplication

/**
 * Created by xuhao on 2017/11/14.
 */

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(RootApplication.getContext(), content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}




package com.example.meliapp.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getSystemService
import com.example.meliapp.databinding.ActivityMainBinding

class BottomNavBarUtil {
    companion object {
        fun getBottomNavBarHeight(activity: Activity): Int {
            val resources = activity.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }

        fun hideBottomNav(binding: ActivityMainBinding ,context: Context ) {
            val activityRootView: View = binding.root
            activityRootView.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
                val heightDiff: Int = activityRootView.rootView.height - activityRootView.height
                if (heightDiff > dpToPx(context, 200)) {
                    binding.content.bottonNav.visibility = View.GONE // Lo haces invisible y que no ocupe espacio cuano exixte un teclado en searchview
                } else {
                    binding.content.bottonNav.visibility = View.VISIBLE
                }
            })
        }

        private fun dpToPx(context: Context, valueInDp: Int): Float {
            val metrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), metrics)
        }
    }
}
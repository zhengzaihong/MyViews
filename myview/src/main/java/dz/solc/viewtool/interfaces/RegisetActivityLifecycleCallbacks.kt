package dz.solc.viewtool.interfaces

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.os.Handler
import android.util.Log

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/6/22
 * create_time: 11:14
 * describe 监听注册后进入当前activit的生命周期
 */
open class RegisetActivityLifecycleCallbacks(delayTime: Long = 500L) : ActivityLifecycleCallbacks {

    private val TAG = RegisetActivityLifecycleCallbacks::class.java.simpleName
    private var foreground = false
    private var paused: Boolean = true
    protected val handler: Handler = Handler()
    private val listeners = ArrayList<ForegroundCallbacks>()
    private var check: Runnable? = null
    private var delayTime = delayTime


    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity is ForegroundCallbacks) {
            listeners.add(activity)
        }

    }

    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {
        if(activity is ForegroundCallbacks){
            paused = false
            val wasBackground = !foreground
            foreground = true
            if (check != null) handler.removeCallbacks(check)
            if (wasBackground) {
                Log.e(TAG, "come foreground")
                for (l in listeners) {
                    try {
                        l.onForeground()
                    } catch (exc: Exception) {
                        Log.e(TAG, "Listener throw exception!: $exc")
                    }
                }
            } else {
                Log.e(TAG, "still foreground")
            }
        }

    }

    override fun onActivityPaused(activity: Activity?) {
        if(activity is ForegroundCallbacks){
            paused = true
            if (check != null) handler.removeCallbacks(check)
            handler.postDelayed(Runnable {
                if (foreground && paused) {
                    foreground = false
                    Log.e(TAG, "come background")
                    for (l in listeners) {
                        try {
                            l.onBackground()
                        } catch (e: Exception) {
                            Log.e(TAG, "Listener throw exception!: $e")
                        }
                    }
                } else {
                    Log.e(TAG, "still foreground")
                }
            }.also { check = it }, delayTime)
        }

    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (activity is ForegroundCallbacks) {
            listeners.remove(activity)
        }
    }
}
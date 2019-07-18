package dz.solc.myviews

import android.app.Application
import android.content.Context
import com.dz.utlis.JavaUtils
import com.dz.utlis.JavaUtils.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        isdebug = true
    }
    companion object {

        var application: App? = null

        fun getinstance(): App? {
            return application
        }
    }

}

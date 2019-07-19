package dz.solc.myviews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dz.solc.myviews.R
import kotlinx.android.synthetic.main.activity_layout_may_view.*

class MayViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_may_view)

        PowerBatteryView.setLevelHeight(50)
    }


}

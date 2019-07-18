package dz.solc.myviews.ui

import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import dz.solc.myviews.R
import dz.solc.viewtool.dialog.LoadingDialog
import dz.solc.viewtool.dialog.OftenDialog
import dz.solc.viewtool.dialog.PhotoDialog
import kotlinx.android.synthetic.main.activity_layout_image_view.*
import kotlinx.android.synthetic.main.activity_layout_tips_dialog.*

class ImageViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_image_view)


        seek_bar_progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circle_progress_normal.progress = progress
                circle_progress_fill_in.progress = progress
                circle_progress_fill_in_arc.progress = progress
                horizontal_progress.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        seek_bar_inner.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circle_progress_normal.normalBarSize = if (progress == 0) 1 else progress / 6
                circle_progress_fill_in_arc.innerPadding = if (progress == 0) 1 else progress / 6
                horizontal_progress.normalBarSize = if (progress == 0) 1 else progress / 6
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        seek_bar_outer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circle_progress_normal.reachBarSize = if (progress == 0) 1 else progress / 6
                circle_progress_fill_in.reachBarSize = if (progress == 0) 1 else progress / 6
                horizontal_progress.reachBarSize = if (progress == 0) 1 else progress / 6
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }
}

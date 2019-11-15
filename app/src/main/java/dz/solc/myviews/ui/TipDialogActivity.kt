package dz.solc.myviews.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dz.solc.myviews.R
import dz.solc.viewtool.dialog.LoadingDialog
import dz.solc.viewtool.dialog.OftenDialog
import dz.solc.viewtool.dialog.PhotoDialog
import kotlinx.android.synthetic.main.activity_layout_tips_dialog.*

class TipDialogActivity : AppCompatActivity() {

    private lateinit var oftendialog: OftenDialog
    private lateinit var photoDialog: PhotoDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_tips_dialog)


        oftendialog = OftenDialog(this)
                .initData("温馨提示", "恭喜你中得大乐透一等奖？")
                .setOutTouchside(false)
                .setAutoClose(true)


        photoDialog = PhotoDialog(this)
                .initTitle("图像选择")
                .setOutTouchside(false)
                .setAutoClose(true)



        tvloading.setOnClickListener {

            LoadingDialog(this)
                    .setDialogDim(0.3f)
                    .setOutTouchside(true)
                    .setShowMaxTime(10)
                    .setLoadingIcon(R.drawable.loading_light)
                    .showDialog()
        }

        tv_often_dialog.setOnClickListener {
            oftendialog.showDialog()
        }


        tv_photo_dialog.setOnClickListener {
            photoDialog.showDialog()

        }
    }
}

package anton.photomap.ui.global

import android.app.Dialog
import android.os.Bundle
import anton.photomap.R
import com.arellomobile.mvp.MvpAppCompatActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast

abstract class BaseActivity : MvpAppCompatActivity() {

    abstract val resId: Int

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
        initContent()
    }

    abstract fun initContent()

    fun showMessage(message: String) {
        toast(message)
    }

    fun showProgress(show: Boolean) {
        if (show) {
            progressDialog = indeterminateProgressDialog(
                    R.string.load_data,
                    R.string.please_wait
            ).apply { setCancelable(false) }
        } else {
            progressDialog?.dismiss()
        }
    }
}
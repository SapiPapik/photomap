package anton.photomap.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date =
    SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale("eng")).parse(this)

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context).load(url).into(this)
}
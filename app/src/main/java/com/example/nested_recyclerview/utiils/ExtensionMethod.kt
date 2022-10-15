package com.example.nested_recyclerview.utiils

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.DisplayCutout
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun Calendar.toDateOnly(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MINUTE, 0)
    return this
}

//fun createPartFromString(text: String): RequestBody {
//    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), text)
//}

//fun createPartFromFile(file: File, param: String = "attachment"): MultipartBody.Part {
//    val requestBody = RequestBody.create("image/".toMediaTypeOrNull(), file)
//    return MultipartBody.Part.createFormData(param, file.name, requestBody)
//}

fun messageContainsTags(message: String): Boolean {
    return message.contains("@[")
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.onDonePress(press: () -> Unit) {
    this.apply {
        setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    press.invoke()
            }
            false
        }
    }
}

fun ContentResolver.getFileName(fileUri: Uri): String {

    var name = ""
    if (fileUri.scheme.toString() == "content") {
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
    } else if (fileUri.scheme.toString() == "file") {
        name = fileUri.lastPathSegment.toString()
    } else {
        name = "unknwn_" + fileUri.lastPathSegment.toString()
    }
    return name
}

fun EditText.isTextEntered(): Boolean {
    return !isTextNotEntered()
}

fun EditText.isTextNotEntered(): Boolean {
    return this.text.isNullOrEmpty()
}

fun String.getNameLetters(): String {
    return this.firstOrNull()?.toString()?.capitalize() ?: ""
}

fun String.getNameCapitalized(): String {
    return this?.capitalize() ?: ""
}

fun String.appendZero(): String {
    return if (this.startsWith("0")) {
        this
    } else "0$this"
}

fun Context.getColorRes(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.   gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.activated() {
    this.isActivated = true
}

fun View.deActivated() {
    this.isActivated = false
}

/** Pad this view with the insets provided by the device cutout (i.e. notch) */
@RequiresApi(Build.VERSION_CODES.P)
fun View.padWithDisplayCutout() {

    /** Helper method that applies padding from cutout's safe insets */
    fun doPadding(cutout: DisplayCutout) = setPadding(
        cutout.safeInsetLeft,
        cutout.safeInsetTop,
        cutout.safeInsetRight,
        cutout.safeInsetBottom
    )

    // Apply padding using the display cutout designated "safe area"
    rootWindowInsets?.displayCutout?.let { doPadding(it) }

    // Set a listener for window insets since view.rootWindowInsets may not be ready yet
    setOnApplyWindowInsetsListener { _, insets ->
        insets.displayCutout?.let { doPadding(it) }
        insets
    }
}

/** Same as [AlertDialog.show] but setting immersive mode in the dialog's window */
fun AlertDialog.showImmersive() {
    // Set the dialog to not focusable
    window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    )

    // Make sure that the dialog's window is in full screen
    window?.decorView?.systemUiVisibility = FLAGS_FULLSCREEN

    // Show the dialog while still in immersive mode
    show()

    // Set the dialog to focusable again
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
}

/** Combination of all flags required to put activity into immersive mode */
const val FLAGS_FULLSCREEN =
    View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

/** Milliseconds used for UI animations */
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

/**
 * Simulate a button click, including a small delay while it is being pressed to trigger the
 * appropriate animations.
 */
fun ImageButton.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
    performClick()
    isPressed = true
    invalidate()
    postDelayed({
        invalidate()
        isPressed = false
    }, delay)
}

fun getDatePreviousYear(yearsAgo: Int, dateFormat: String): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, -yearsAgo)
    val date = calendar.time
    val format = SimpleDateFormat(dateFormat, Locale.US)
    val dateOutput = format.format(date)
    return dateOutput
}

fun getDatePreviousMonth(monthAgo: Int, dateFormat: String, start: Boolean = true): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -monthAgo)
    if (start) {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
    } else {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    }
    val date = calendar.time
    val format = SimpleDateFormat(dateFormat, Locale.US)
    val dateOutput = format.format(date)
    return dateOutput
}

fun getCurrentDateWithFormate(format: String): String {
    val sdf = SimpleDateFormat(format)
    val currentDate = sdf.format(Date())
    return currentDate
}

fun getDateWithFormat(value: String, formatFrom: String, formatTo: String): String {
    val formatFrom = SimpleDateFormat(formatFrom)
    val formatTo = SimpleDateFormat(formatTo, Locale.US)
    val date = formatFrom.parse(value)
    val formattedDate = formatTo.format(date)
    return formattedDate
}

fun Drawable.tint(context: Context, @ColorRes color: Int) {
    DrawableCompat.setTint(this, ContextCompat.getColor(context, color))
}

fun <T : Any> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun Fragment.setNavigationResult(result: Any, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Fragment.setNavigationResult(result: Any, key: String = "result", id: Int) {
    findNavController().getBackStackEntry(id)?.savedStateHandle?.set(key, result)
}

fun Resources.drawableIntToString(drawable: Int): String {

    return this.getResourceEntryName(drawable)
}

fun String.stringToDrawableInt(context: Context): Int {
    return context.resources.getIdentifier(this, "drawable", context.applicationContext.packageName);
}

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

fun String.removeWhitespaces() = replace(" ", "")

//fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
fun Int.toPx(context: Context): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(), context.resources.displayMetrics).toInt()

fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

//fun saveObject(instance: Any?, type: java.lang.reflect.Type, lastModifiedDate: Long = Date().time) =
//    SecureSharedPref.edit().putObject(CacheEntry(instance, lastModifiedDate), type).apply()

//fun getCashedObject(type: java.lang.reflect.Type): Any? {
//
//    val entry: CacheEntry<Any>? = SecureSharedPref.getObject(type)
//    val cachedObject: Any? = entry?.obj
//    return if (cachedObject != null) {
//        val cachedObject: Any? =
//            ParsingHelper.getGson().toJson(cachedObject).convertToModel(type)
//        cachedObject
//    } else null
//}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
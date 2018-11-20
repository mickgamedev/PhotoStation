package ru.yandex.dunaev.mick.photostation

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("visibility")
fun View.SetVisibitily(b: Boolean){
    this.visibility = if(b) View.VISIBLE else View.GONE
}

@BindingAdapter("src")
fun ImageView.setBitmap(bitmap: Bitmap?){
    bitmap?: return
    Log.v("IMAGE","Set bitmap ${bitmap.width} x ${bitmap.height}")
    setImageBitmap(bitmap)
}
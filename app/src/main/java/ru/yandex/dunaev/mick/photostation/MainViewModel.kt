package ru.yandex.dunaev.mick.photostation

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace



class MainViewModel : ViewModel() {
    val image = ObservableField<Bitmap>()
    val progress = ObservableBoolean(false)
    var trollFace: Drawable? = null

    val photoBitmaps = mutableListOf<Bitmap>()

    fun setImage(bitmap: Bitmap) {
        Log.v("MainViewModel", "set image")
        image.set(bitmap)
        faceDetector()
    }

    private fun faceDetector() {
        val bitmap = image.get() ?: return
        progress.set(true)
        val detector = FirebaseVision.getInstance().visionFaceDetector
        detector.detectInImage(FirebaseVisionImage.fromBitmap(bitmap))
            .addOnCompleteListener {
                showFaces(it.result)
                progress.set(false)
            }
    }

    private fun showFaces(faces: MutableList<FirebaseVisionFace>?) {
        faces ?: return
        val bitmap = image.get()//?.copy(Bitmap.Config.ARGB_8888, true)
        bitmap ?: return
        val markedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(markedBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#99003399") }
        photoBitmaps.clear()
        faces.forEach {
            canvas.drawRect(it.boundingBox, paint)
            //drawTrollFace(canvas, it.boundingBox)
            with(it.boundingBox){
                val x = if(left >= 0) left else 0
                val y = if(top >= 0) top else 0
                var width = if(left >= 0) right - left else right
                var height = if(top >= 0) bottom - top else bottom
                if(x + width > bitmap.width) width = bitmap.width - x
                if(y + height > bitmap.height) height = bitmap.height - y

                Log.v("Face","bitmap: ${bitmap.width} x ${bitmap.height} rect: x:$x y:$y w:$width h:$height")
                photoBitmaps.add(Bitmap.createBitmap(bitmap,x,y,width,height))
            }

        }
        image.set(markedBitmap)
    }

    private fun drawTrollFace(canvas: Canvas, boundingBox: Rect){
        trollFace?.apply{
            setBounds(boundingBox)
            draw(canvas)
        }
    }
}
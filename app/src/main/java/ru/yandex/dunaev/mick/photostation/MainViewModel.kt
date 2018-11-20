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
        val markedBitmap = image.get()?.copy(Bitmap.Config.ARGB_8888, true)
        markedBitmap ?: return
        val canvas = Canvas(markedBitmap)
        faces.forEach {
//            canvas.drawRect(it.boundingBox, paint)
            drawTrollFace(canvas, it.boundingBox)
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
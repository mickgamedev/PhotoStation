package ru.yandex.dunaev.mick.photostation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.otaliastudios.cameraview.*
import ru.yandex.dunaev.mick.photostation.databinding.FragmentPhotoCaptureBinding

class PhotoCaptureFragment : Fragment() {

    private lateinit var binding: FragmentPhotoCaptureBinding
    var onCaptureComplete: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_photo_capture,container,false)
        val model: MainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = model
        binding.fragment = this

        binding.camera.setLifecycleOwner(this)

        binding.camera.apply{
            mapGesture(Gesture.PINCH, GestureAction.ZOOM)
            mapGesture(Gesture.SCROLL_VERTICAL, GestureAction.EXPOSURE_CORRECTION)
            Repository.restoreCameraState(this)
            addCameraListener(object: CameraListener() {
                override fun onPictureTaken(jpeg: ByteArray?) {
                    super.onPictureTaken(jpeg)
                    CameraUtils.decodeBitmap(jpeg) {
                        model.setImage(it)
                        onCaptureComplete()
                    }
                }
            })
        }

        return binding.root
    }

    fun onCaptureImage(){
        Log.v("PhotoCapture","Capture")
        binding.camera.capturePicture()
    }

    fun onSwitchCamera() = with(binding.camera){
        facing = if(facing == Facing.BACK) Facing.FRONT else Facing.BACK
    }

    fun onSwitchGrid() = with(binding.camera){
        grid = if(grid == Grid.DRAW_PHI) Grid.OFF else Grid.DRAW_PHI
    }

    override fun onDestroy() {
        super.onDestroy()
        Repository.saveCameraState(binding.camera)

    }
}



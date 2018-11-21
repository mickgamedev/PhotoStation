package ru.yandex.dunaev.mick.photostation

import android.content.Context
import androidx.core.content.edit
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.Facing
import com.otaliastudios.cameraview.Grid
import kotlinx.serialization.json.JSON

object Repository {
    fun saveCameraState(camera: CameraView){
        val settings = CameraSettings(camera.facing == Facing.BACK,camera.grid == Grid.OFF)
        val jsonData = JSON.stringify(CameraSettings.serializer(), settings)

        getSharedPreferences(camera.context).edit {
            putString("camera", jsonData)
        }
    }

    fun restoreCameraState(camera: CameraView){
        val settings = getSharedPreferences(camera.context)
        val jsonData = settings.getString("camera","")
        jsonData?: return
        if(jsonData.isEmpty()) return

        val cameraSettings = JSON.parse(CameraSettings.serializer(), jsonData)
        camera.facing = if(cameraSettings.orientation) Facing.BACK else Facing.FRONT
        camera.grid = if(cameraSettings.grid) Grid.OFF else Grid.DRAW_PHI
    }

    private fun getSharedPreferences(context: Context) = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
}
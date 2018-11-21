package ru.yandex.dunaev.mick.photostation
import kotlinx.serialization.*

@Serializable
data class CameraSettings(var orientation: Boolean, var grid: Boolean)
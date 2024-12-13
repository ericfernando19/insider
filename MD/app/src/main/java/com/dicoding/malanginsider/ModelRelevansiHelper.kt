package com.dicoding.malanginsider

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ModelRelevansiHelper(context: Context) {

    private val interpreter: Interpreter

    init {
        val model = context.assets.open("model_relevansi_jarakkota.tflite").use { inputStream ->
            val modelBytes = inputStream.readBytes()
            ByteBuffer.allocateDirect(modelBytes.size).apply {
                order(ByteOrder.nativeOrder())
                put(modelBytes)
                rewind()
            }
        }
        interpreter = Interpreter(model)
    }

    fun predictRelevansi(inputCity: String, wisataCoordinates: List<Pair<Double, Double>>): List<Float> {
        val inputDim = intArrayOf(1, wisataCoordinates.size, 2)
        interpreter.resizeInput(0, inputDim)

        val inputBuffer = ByteBuffer.allocateDirect(4 * wisataCoordinates.size * 2).apply {
            order(ByteOrder.nativeOrder())
            wisataCoordinates.forEach { coord ->
                putFloat(coord.first.toFloat())
                putFloat(coord.second.toFloat())
            }
            rewind()
        }

        val outputBuffer = Array(1) { FloatArray(wisataCoordinates.size) }

        interpreter.run(inputBuffer, outputBuffer)

        Log.d("ModelRelevansi", "Input buffer size: ${inputBuffer.capacity()}")
        Log.d("ModelRelevansi", "Output buffer size: ${outputBuffer[0].size}")

        return outputBuffer[0].toList()
    }

    fun close() {
        interpreter.close()
    }
}

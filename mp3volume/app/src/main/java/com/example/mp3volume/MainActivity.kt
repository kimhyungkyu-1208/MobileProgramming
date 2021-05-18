package com.example.mp3volume

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mp3volume.R
import com.example.mp3volume.VolumeControlView

class MainActivity : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?=null
    var vol = 0.5f
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        if(flag)
            mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    fun init(){
        val imageView = findViewById<VolumeControlView>(R.id.imageView)
        imageView.setVolumeListener(object:VolumeControlView.VolumeListener{
            override fun onChange(angle: Float) {
                vol = if (angle>0){
                    angle/360
                } else{
                    (360+angle)/360
                }
                mediaPlayer?.setVolume(vol, vol)
            }

        })

        val playBtn = findViewById<Button>(R.id.playBtn)
        playBtn.setOnClickListener{
            if(mediaPlayer==null){
                mediaPlayer = MediaPlayer.create(this,R.raw.song)
                mediaPlayer?.setVolume(vol,vol)
            }
            mediaPlayer?.start()
            flag = true
        }

        val pauseBtn = findViewById<Button>(R.id.pauseBtn)
        pauseBtn.setOnClickListener{
            mediaPlayer?.pause()
            flag = false
        }

        val stopBtn = findViewById<Button>(R.id.stopBtn)
        stopBtn.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            flag = false
        }
    }
}
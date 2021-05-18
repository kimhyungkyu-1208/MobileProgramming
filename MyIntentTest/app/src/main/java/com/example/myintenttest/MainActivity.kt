package com.example.myintenttest

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myintenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val CALL_REQUEST=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    fun callAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE권한이 허용되어야 합니다.")
                .setTitle("권한허용")
                .setPositiveButton("OK"){
                    _, _-> ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), CALL_REQUEST)

                }
        val dlg = builder.create()
        dlg.show()
    }

    fun callAction(){
        val number = Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            callAlertDlg()
        }else{
            startActivity(callIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CALL_REQUEST->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    callAction()
                }else{
                    Toast.makeText(this, "권한승인이 거부 되었습니다.", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun init(){
//        binding.msgBtn.setOnClickListener {
//
//        }




        with(binding){
            callBtn.setOnClickListener {
                callAction()
            }
            msgBtn.setOnClickListener {
                val message = Uri.parse("sms:010-1234-1234")
                val messageIntent = Intent(Intent.ACTION_SENDTO, message)
                messageIntent.putExtra("sms_body", "빨리 다음에 하자....")
                startActivity(messageIntent)
            }
            webBtn.setOnClickListener {
                val webpage = Uri.parse("http://naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(webIntent)
            }
            mapBtn.setOnClickListener {
                val location = Uri.parse("geo:37.543684,127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }


        }
    }


}
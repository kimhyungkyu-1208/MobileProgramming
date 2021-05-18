package com.example.hw1

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class djhMainActivity : AppCompatActivity() {

    var title = "권한 알림"
    var content = "권한 알림 요청"

   

    val CALL_REQUEST=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.djhactivity_main)
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
        val number = Uri.parse("tel:010-6478-6194")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
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

        val call = findViewById<TextView>(R.id.textView2)
        call.setOnClickListener {
            callAction()
        }
    }
}
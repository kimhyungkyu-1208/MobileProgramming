package com.example.a5point

import java.io.Serializable

class MyData(val word:String, val meaning:String, var isExpanded: Boolean = false):Serializable {
}
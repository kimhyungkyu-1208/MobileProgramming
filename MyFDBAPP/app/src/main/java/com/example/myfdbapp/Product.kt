package com.example.myfdbapp

data class Product(var PId:Int, var pName:String, var pQuantity:Int){
    constructor():this(0, "noInfo", 0)
}

package com.chillandcode.dessert_clicker_androidapp_understanding_lifecycle_kotlin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel() {
    var quantity:Int=1
    private var lifecycleText=""
    fun incrementCartQuantity(){
        quantity++
    }
    fun resetCartQuantity(){
        quantity=1
    }

    fun getLifeCycleText():String{
        return lifecycleText
    }
    fun addToLifeCycleText(lifeCycleName:String){
        lifecycleText+=lifeCycleName
    }

    fun resetLog() {
        lifecycleText=""
    }

}
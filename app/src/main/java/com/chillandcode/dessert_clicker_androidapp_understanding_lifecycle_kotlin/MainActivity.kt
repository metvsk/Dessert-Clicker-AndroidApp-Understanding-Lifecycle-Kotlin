package com.chillandcode.dessert_clicker_androidapp_understanding_lifecycle_kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.ActionMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chillandcode.dessert_clicker_androidapp_understanding_lifecycle_kotlin.databinding.ActivityMainBinding
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private var counter=0
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = CartViewModel()

        viewModel.addToLifeCycleText(getString(prefLifeCycleString))
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()
        binding.button.setOnClickListener {
            increaseQuantity();
            updateUI()
        }

        binding.imageView.setOnClickListener {
            resetQuantity()
            updateUI()
        }

        binding.clear.setOnClickListener {
            clearLogs()
        }

        updateLifeCycleText("onCreate")

    }

    ///START-------********************* LIFE CYCLE TESTS ****************----------
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {



        //adding the function here would result in error since the viewModel will not be initiated before creating the view
        // how ever can add a log cat entry to see the log on computer
        Log.i("TAG", "onCreateView: OUR COMPUTER LOG")
        try {
            counter++
            viewModel.addToLifeCycleText("\n Called try $counter time=> time stamp : ${getUpdatedTime()} : On create view tried to add inside try and catch worked **** \n ")
        }catch (e:Error){
            Log.i("ERROR TAG", "onCreateView: ${e.message}")
        }
        return super.onCreateView(name, context, attrs)
    }


    override fun onPause() {
        super.onPause()
        updateLifeCycleText("onPause")
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        updateLifeCycleText("onActionModeFinished")
    }

    override fun onStop() {
        super.onStop()
        updateLifeCycleText("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        updateLifeCycleText("onRestart")
    }

    override fun onAttachedToWindow() {
        updateLifeCycleText("onAttachedToWindow")
        super.onAttachedToWindow()
    }

    override fun onActionModeStarted(mode: ActionMode?) {
        updateLifeCycleText("onActionModeStarted")
        super.onActionModeStarted(mode)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateLifeCycleText("onDestroy")
        updateLifeCycleText("\n\n ********************************************************************************************\n\n")
    }

    override fun onResume() {
        super.onResume()
        updateLifeCycleText("onResume")

    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        updateLifeCycleText("onActivityReenter")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        updateLifeCycleText(    "super.onCreate(savedInstanceState, persistentState)")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateLifeCycleText("onBackPressed")
    }
    ///END--********************* LIFE CYCLE TESTS ************* ----------



    //view model and ui update functions
    private fun increaseQuantity() {
        viewModel.incrementCartQuantity()
    }

    private fun resetQuantity() {
        viewModel.resetCartQuantity()
    }

    private fun updateUI() {
        val text = "Total ${viewModel.quantity} * 250 =  ${viewModel.quantity * 250}"
        binding.textView.text = text
    }

    private fun updateLifeCycleText(logString: String) {
        viewModel.addToLifeCycleText(getUpdatedTime()+" => "+logString+"\n")
        setString(prefLifeCycleString,viewModel.getLifeCycleText())
        binding.lifeCycle.text = viewModel.getLifeCycleText()
    }

    private fun clearLogs() {
        viewModel.resetLog()
        setString(prefLifeCycleString, "")
        binding.lifeCycle.text = viewModel.getLifeCycleText()
    }

    //string prefs ***************************
    // THIS IS USED TO SAVE AND RESET STRINGS LOCALLY
    private var prefMainString: String = "APP_MAIN_PREF_STRING";
    private var prefLifeCycleString: String = "APP_LIFECYCLE_STORE_STRING";

    private fun getString(string_pref_string: String): String {
        return getSharedPreferences(prefMainString, AppCompatActivity.MODE_PRIVATE).getString(
            string_pref_string,
            ""
        )
            ?: ""
    //returns string if stored else returns empty => "" string avoiding returning null so to not deal with it after this method being called somewhere
    }

    private fun setString(string_pref_string: String, string_value: String) {
        val editor: SharedPreferences.Editor =
            this.getSharedPreferences(prefMainString, AppCompatActivity.MODE_PRIVATE).edit()
        editor.putString(string_pref_string, string_value)
        editor.apply()
    }

    @Suppress("SameParameterValue")
    private fun initStringPref(string_pref_string: String, string_value: String) {
        if (checkIfPrefsExist(string_pref_string))
            return
        else
            setString(string_pref_string, string_value)
    }

    private fun checkIfPrefsExist(bool_pref_string: String): Boolean {
        return this.getSharedPreferences(prefMainString, MODE_PRIVATE).contains(bool_pref_string);
    }

    //UNTIL THIS USED TO SAVE AND RESET STRINGS LOCALLY
    //string prefs ***************************

    private fun getUpdatedTime(): String {
        val calendar: Calendar = Calendar.getInstance()

        val isDaylightSavingActive = TimeZone.getDefault().inDaylightTime(Date())
        var offset = calendar.timeZone.rawOffset
        if (isDaylightSavingActive) {
            offset += TimeZone.getDefault().dstSavings
        }
        val passedSeconds = ((calendar.timeInMillis + offset) / 1000).toInt()
        val hours = (passedSeconds / 3600) % 24
        val minutes = (passedSeconds / 60) % 60
        val seconds = passedSeconds % 60
        return    "${if (hours < 10) "0$hours" else "$hours"} : ${if (minutes < 10) "0$minutes" else "$minutes"} : ${if (seconds < 10) "0$seconds" else "$seconds"} :: ${passedSeconds}"

    }

}
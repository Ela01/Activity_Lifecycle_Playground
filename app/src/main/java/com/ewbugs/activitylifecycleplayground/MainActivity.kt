package com.ewbugs.activitylifecycleplayground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ewbugs.activitylifecycleplayground.databinding.ActivityMainBinding
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var seconds = 0

    //period is in milliseconds
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Another way to show that you are in 'onDestroy'
        binding.buttonExit.setOnClickListener {
            //Log.d("ElaWieczorek", "In the button click listener...")
            showDialog()
        }
        val callback = onBackPressedDispatcher.addCallback(this) {
            showDialog()
        } //make sure to add the dependency

    }

        // On destroy example-- Not good to save data in onDestroy--unreliable
//        override fun onDestroy() {
//            super.onDestroy()
//            val userMessage = binding.editTextMessage.text //trying to 'save this message outside of our app launches'
//            //A specific place we can save our file without asking for the user's permission, and it
//            //would just store it within our apps file system
//            // To access this -> Device File explorer -> com.ewbugs.activitylifecycleplayground...-> sync -> files -> user message.txt
//            //very useful function: '.writetext' -> has all of the complexity under the hood for writing
//            //text to a file.
//            File(filesDir, "user message.txt").writeText(userMessage.toString())
//
//        }

    private fun showDialog(){

        AlertDialog.Builder(this)
            .setTitle("Warning!")
            .setMessage("You are about to leave the App, are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ -> //firstParameter, secondParameter ->
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            .show()

    }



        // 'Log.d' -> 'Log' for Logcat, 'd' for debug
        // You can go into the Logcat and filter by the 'tag' by clicking into the search box.
        // This is one way you can find out where you are
        //Log.d("ElaWieczorek", "I'm in onCreate")
        //.................................................
        // A second way would be to mark a break-point, click the debugger icon, and then run the app.
        //.................................................


//    override fun onResume() {
//        super.onResume()
//        fixedRateTimer(period = 1000L) {
//            runOnUiThread {
//                seconds++
//                binding.textViewTimer.text = "You have been staring at this screen for $seconds seconds!"
//            }
//        }
//    }
//    override fun onPause() {
//        super.onPause()
//        timer.cancel()
//    }


/*
        // So 'onStart' happens straight after on create.
        // When our activity becomes visible
        // When you start the app Logcat will show:
        // 'I'm in onCreate'
        // 'I'm in onStart'
        override fun onStart() {
            super.onStart()
            Log.d("ElaWieczorek", "I'm in onStart")

        }
        // onPause and onResume has to do with background and foreground.
        // onPause (of current) happens when the user moves the current foreground into the background
        // onResume (of current) happens when the user goes back to the previous activity and moves it into the foreground;
        // when our activity is in the foreground
        override fun onResume() {
            super.onResume()
            Log.d("ElaWieczorek", "I'm in onResume")
        }

        // When our activity is still visible but is in the background
        // 'onPause' is the state where the application is still running in the background but
        // another activity comes into the foreground. Similar to onStop it is no longer visible.
        // When the 'exit_button' is clicked Logcat shows:
        // In the button click listener...
        // 'I'm in onPause',
        // 'I'm in onStop',
        // 'I'm now in onDestroy'
        override fun onPause() {
            super.onPause()
            Log.d("ElaWieczorek", "I'm in onPause")
        }

    // When our activity is no longer visibile (but still running)
    // 'onStop' is called when the application is no longer visible, but not yet destroyed.
    // If we click on the 'exit_button' the Log cat will show:
    // 'I'm in onStop',
    // 'I'm in onDestroy'
    // Supposedly, (though I have not checked,) 'onStop' also happens when you click on the Home-Button.
    // 'onStop will not show (on Logcat) when you click on the task-switcher,
    // but rather when you move out of the app completely or press the Back-Button.
        override fun onStop() {
            super.onStop()
            Log.d("ElaWieczorek", "I'm in onStop")

        }

    // Supposed to show the 'msg' (Logcat) when the Back-Button is pressed(according to the tutorial) on the app.
    // Pressing the Home-Button or the Task-Switcher-Button will not show this message.
    override fun onDestroy() {
        super.onDestroy()
        Log.d("ElaWieczorek", "I'm now in onDestroy")
    }
//Logcat shows:

//I'm in onCreate
//I'm in onCreate
//In the button click listener...
//I'm now in onDestroy

//For some reason it does not show the onDestroy when I click the Back-Button??
*/
}


package com.ewbugs.activitylifecycleplayground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ewbugs.activitylifecycleplayground.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), TestFragment.TestFragmentListener {

    private lateinit var binding: ActivityMainBinding
    private val testFragment = TestFragment()

//    var seconds = 0

    //period is in milliseconds
//    lateinit var timer: Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Another way to show that you are in 'onDestroy'
        binding.buttonExit.setOnClickListener {
            //Log.d("ElaWieczorek", "In the button click listener...")
            showDialog()
        }

        binding.buttonSave.setOnClickListener { saveMessage() }
        binding.buttonShowFragment.setOnClickListener { showFragment() }
        binding.buttonRemoveFragment.setOnClickListener { removeFragment() }
        onBackPressedDispatcher.addCallback(this) { showDialog() } //make sure to add the dependency
        binding.textViewSavedMessage.text = savedInstanceState?.getString("savedMessage")
    }

    /*
    All a fragment transaction is is a thing you can do with a fragment in relation to where its host would
be.
You can add fragments, you can replace fragments, remove fragments, these all fragment transactions
and this fragment manager thing that we have access to that takes the fragment object that you give
it, it will attach it to whatever host, whatever context it needs.
Attaching to could be a container, could be an activity, and then it takes care of instantiating the
fragment in terms of the lifecycle.
So things like on create, on create view, it is the fragment manager's job to do that and the information,
the instruction to do that is your fragment transaction.
So scary sounding word fragment transaction, but all it is is an instruction to do something with a
fragment and the fragment.
Host.
     */

    private fun showFragment() {
        //for '.commit' must install dependency-> implementation 'androidx.fragment:fragment-ktx:1.5.7'
        //Below is all the code you we need to add a new fragment into the container whenever
        //the user clicks the show fragment button.
        supportFragmentManager.commit {
            add(R.id.fragment_container, testFragment)
        }


    }

    private fun removeFragment() {
        supportFragmentManager.commit {
            remove(testFragment)
        }
    }

    //function to help save data when data is destroyed in the process of rotating phone frame (portrait-landscape)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val savedTextViewMessage = binding.textViewSavedMessage.text.toString()
        outState.putString("savedMessage", savedTextViewMessage)
    }
    private fun saveMessage() {

        val userMessage = binding.editTextMessage.text
        File(filesDir, "user message.txt").writeText(userMessage.toString())
        binding.textViewSavedMessage.text = "Your message has been saved!\n\nMessage Preview:\n\n$userMessage"
        binding.editTextMessage.setText("")

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

    private fun showDialog() {

        AlertDialog.Builder(this)
            .setTitle("Warning!")
//            .setMessage("You are about to leave the App, are you sure you want to exit?")
            .setView(R.layout.dialog_warning)
            .setPositiveButton("Yes") { _, _ -> //firstParameter, secondParameter ->
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("More info") { dialog, _ ->
                Toast.makeText(
                    this,
                    "This is where the 'more info' screen could be!",
                    Toast.LENGTH_LONG
                ).show()
                dialog.dismiss()
            }

            .show()
    }

    override fun clearActivityScreen() {
        binding.editTextMessage.setText("")
        binding.textViewSavedMessage.text = ""
        removeFragment()
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


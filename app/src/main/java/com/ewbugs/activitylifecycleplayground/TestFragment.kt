package com.ewbugs.activitylifecycleplayground

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ewbugs.activitylifecycleplayground.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private var fragmentListener: TestFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListener = context as TestFragmentListener //this is called 'casting'
    }
//    ----------------------------------------------------------------------------------------------
// ***OnCREATE REMOVED***

//    You don't have any sort of view to access in 'onCreate', so the on create method in fragments isn't as useful
//    as it is in activities. There's not many cases where you would need it here apart from a few exceptions
//    for specific circumstances where you're doing things BEFORE your create your view, but this isn't very common.

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//    ------------------------------------------------------------------------------------------

//    So in Fragments 'onCreateView' is like the 'onCreate' method in Activities.
//    This is the key method where we create the view for the fragment and then 'onViewCreated' is where you
//    can then start getting to work. You've still got access to your 'saved instance state'.
//
//    So these two methods working together are the methods that you use in Fragments to get your screen set
//    up ready for the users to start interacting with.

    //    ------------------------------------------------------------------------------------------
//    The 'onCreateView' in Fragments should only have the setup for the view.
//    And in the case of view binding, that should just simply be setting up the binding variable and then
//    returning the binding root.
//    The content view in Fragments is done in the 'onViewCreated', NOT the 'onCreateView' (below)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 'onViewCreated' is the function where you interact with your view (e.g. updating text, adding click listeners, etc.)
    // This is NOT done in 'onCreateView'
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewFragmentText.text = getString(R.string.We_have_made_it_into_out_new_Fragment_)
        binding.buttonClearScreen.setOnClickListener {
            fragmentListener?.clearActivityScreen()
        }
    }
//    ------------------------------------------------------------------------------------------
//    ***OnDESTROY REMOVED***

//    Destroy View might be where you might clean some things up.
//    You might reset some variables that relate to your view.
//    If you've been holding onto variables in the fragment, you might make them null.
//    It's not too often you need to do too much with the on destroy view method.

//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//    ------------------------------------------------------------------------------------------

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

//    You define an interface in the fragment, and this
//    interface a bit like a button click listener with on click.
//    It just defines the behavior that you would want to access in your activity.

    interface TestFragmentListener {

        fun clearActivityScreen()

    }

}

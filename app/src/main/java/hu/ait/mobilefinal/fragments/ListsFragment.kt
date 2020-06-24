package hu.ait.mobilefinal.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import hu.ait.mobilefinal.HomeScreen

import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.RegisterActivity


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class ListsFragment : Fragment() {
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)

    }



}


package hu.ait.mobilefinal.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import hu.ait.mobilefinal.HomeScreen
import hu.ait.mobilefinal.LoginActivity

import hu.ait.mobilefinal.R
import kotlinx.android.synthetic.main.fragment_user.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class UserFragment : Fragment() {
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user, container, false)

        view.accountUsername.text = (activity as HomeScreen).getCurrentUser().username
        Glide.with(view).load((activity as HomeScreen).getCurrentUser().profileImageUrl).into(view.accountImage)


        view.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity as HomeScreen, LoginActivity::class.java))
            (activity as HomeScreen).finish()
        }


        return view
    }


}

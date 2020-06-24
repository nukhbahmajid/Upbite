package hu.ait.mobilefinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import hu.ait.mobilefinal.HomeScreen


import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.data.User
import hu.ait.mobilefinal.data.UserItem
import kotlinx.android.synthetic.main.fragment_new_message.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NewMessageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class NewMessageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_message, container, false)


        fetchUsers(view)


        return view
    }

    private fun fetchUsers(view : View) {
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                
                adapter.setOnItemClickListener {item, view ->

                    val userItem = item as UserItem
                    val chatLogFragment = ChatLogFragment()

                    (activity as HomeScreen).setOtherUserTo(userItem)
                    (activity as HomeScreen).makeCurrentFragment(chatLogFragment)
                }

                view.recyclerNewMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

}

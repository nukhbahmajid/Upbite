package hu.ait.mobilefinal.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import hu.ait.mobilefinal.HomeScreen

import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.data.ChatLeftItem
import hu.ait.mobilefinal.data.ChatMessage
import hu.ait.mobilefinal.data.ChatRightItem
import hu.ait.mobilefinal.data.User
import kotlinx.android.synthetic.main.fragment_chat_log.*
import kotlinx.android.synthetic.main.fragment_chat_log.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChatLogFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class ChatLogFragment : Fragment() {

    val adapter = GroupAdapter<GroupieViewHolder>()

    var otherUser : User = User()
    var currentUser : User = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_log, container, false)

        // set the current and to user property
        otherUser = (activity as HomeScreen).getOtherUser().user
        currentUser = (activity as HomeScreen).getCurrentUser()

        view.recyclerChatLog.adapter = adapter


        /*
          START - TO GO BACK TO MESSAGES
         */
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                (activity as HomeScreen).makeCurrentFragment(MessagesFragment())
            }
            true
        }
        /*
            END - TO GO BACK TO MESSAGES
         */


        listenForMessages()

        /*
        In case I want to set the title of the action bar to the username of the
        person you are sending the message to
         */

        //(activity as HomeScreen).setSupportActionBarText((activity as HomeScreen).getOtherUser().user.username)

        view.sendMsgBtn.setOnClickListener {
            sendMessage(view)

        }

        return view
    }


    fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        if(fromId == null) {
            return
        }
        val toId = (activity as HomeScreen).getOtherUser().user.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")


        ref.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if(chatMessage != null) {
                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatRightItem(chatMessage.text, currentUser!!))
                    } else {
                        adapter.add(ChatLeftItem(chatMessage.text, otherUser!!))
                    }

                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }

    fun sendMessage(view : View) {
        // retrieve the text from the edit text and make a chat message object of it.
        val text = etMessage.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        if(fromId == null) return
        val toId = (activity as HomeScreen).getOtherUser().user.uid

        // sending the message to Firebase to keep the history of the log message

        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toRef = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(ref.key.toString(), text, fromId, toId, System.currentTimeMillis()/1000)

        ref.setValue(chatMessage).addOnSuccessListener {
            view.etMessage.text.clear()
            view.recyclerChatLog.scrollToPosition(adapter.itemCount - 1)
        }

        toRef.setValue(chatMessage).addOnSuccessListener {

        }

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)
        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)

    }


}
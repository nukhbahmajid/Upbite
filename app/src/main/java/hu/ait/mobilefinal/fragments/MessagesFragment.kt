package hu.ait.mobilefinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import hu.ait.mobilefinal.HomeScreen

import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.data.ChatMessage
import hu.ait.mobilefinal.data.LatestMessageItem
import hu.ait.mobilefinal.data.User
import hu.ait.mobilefinal.data.UserItem
import kotlinx.android.synthetic.main.fragment_messages.view.*


class MessagesFragment : Fragment() {
    //private var listener: OnFragmentInteractionListener? = null

    val adapter = GroupAdapter<GroupieViewHolder>()
    val latestMessagesMap = HashMap<String, ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        (activity as HomeScreen).verifyUserIsLoggedIn()

        view.recyclerMessages.adapter = adapter
        view.recyclerMessages.addItemDecoration(DividerItemDecoration((activity as HomeScreen), DividerItemDecoration.VERTICAL))


        listenForLatestMessages()

        fetchCurrentUser()



        view.newMsgBtn.setOnClickListener {
            val newMessageFragment = NewMessageFragment()
            (activity as HomeScreen).makeCurrentFragment(newMessageFragment)
        }


        return view
    }

    fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageItem(it))
        }

        // maybe add an on click listener here

        // set on click listener will be for latest message item
        // reset other and current user, and prompt open the chat log fragment

        adapter.setOnItemClickListener { item, view ->
            val latestMessageItem = item as LatestMessageItem
            val chatLogFragment = ChatLogFragment()

            val fromId = FirebaseAuth.getInstance().uid
            val toId = latestMessageItem.chatPartnerUser

            (activity as HomeScreen).setOtherUserTo(UserItem(latestMessageItem.chatPartnerUser!!))
            (activity as HomeScreen).makeCurrentFragment(chatLogFragment)

//            val ref = FirebaseDatabase.getInstance().getReference("/users/$fromId")
//
//            ref.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//
//                }
//
//            })

        }
    }

    fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object  :ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()

                // add statement to the adapter removed from here to test the refresh recycler

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        } )

    }


    fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                if(user == null) {return}
                (activity as HomeScreen).setCurrentUser(user)

            }

        })

    }




}

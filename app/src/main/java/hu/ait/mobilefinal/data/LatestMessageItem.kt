package hu.ait.mobilefinal.data

import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hu.ait.mobilefinal.R
import kotlinx.android.synthetic.main.item_latest_message.view.*

class LatestMessageItem(val chatMessage: ChatMessage) : Item<GroupieViewHolder>() {
    var chatPartnerUser : User? = null

    override fun getLayout(): Int {
        return R.layout.item_latest_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val chatPartnerId : String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.tvUsername.text = chatPartnerUser?.username

                if(chatPartnerUser?.profileImageUrl.equals("")) {

                } else {
                    Glide.with(viewHolder.itemView).load(chatPartnerUser?.profileImageUrl).into(viewHolder.itemView.ivlatestMsg)
                }


            }

        })

        viewHolder.itemView.tvLatestMessage.text = chatMessage.text

    }
}
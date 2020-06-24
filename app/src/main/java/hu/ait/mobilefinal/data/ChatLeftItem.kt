package hu.ait.mobilefinal.data

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hu.ait.mobilefinal.R
import kotlinx.android.synthetic.main.chat_left_row.view.*

class ChatLeftItem(val textMessage : String, val user : User) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvMessage.text = textMessage
        if(!user.profileImageUrl.equals("")) {
            Glide.with(viewHolder.itemView).load(user.profileImageUrl).apply(
                RequestOptions().transforms(
                    CenterCrop()
                )).into(viewHolder.itemView.profilePic)
        }
    }

    override fun getLayout(): Int {
        return R.layout.chat_left_row
    }
}
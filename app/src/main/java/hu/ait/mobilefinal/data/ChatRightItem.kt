package hu.ait.mobilefinal.data

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hu.ait.mobilefinal.R
import kotlinx.android.synthetic.main.chat_right_row.view.*
import kotlinx.android.synthetic.main.chat_right_row.view.tvMessage

class ChatRightItem(val textMessage : String, val user : User) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvMessage.text = textMessage
        if(!user.profileImageUrl.equals("")) {
            Glide.with(viewHolder.itemView).load(user.profileImageUrl).apply(
                RequestOptions().transforms(
                    CenterCrop()
                )).into(viewHolder.itemView.profilePicTo)
        }
    }

    override fun getLayout(): Int {
        return R.layout.chat_right_row
    }
}
package hu.ait.mobilefinal.data

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hu.ait.mobilefinal.HomeScreen
import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.fragments.HomeFragment
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantItem(val restaurant : YelpRestaurants) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.restaurantName.text = restaurant.name
        viewHolder.itemView.restaurantAddress.text = restaurant.location.address
        viewHolder.itemView.restaurantCategory.text = restaurant.categories[0].title
        viewHolder.itemView.ratingBar.rating = restaurant.rating.toFloat()
        viewHolder.itemView.restaurantReviews.text = "${restaurant.numReviews} Reviews"
        viewHolder.itemView.restaurantDistance.text = restaurant.displayDistance()
        viewHolder.itemView.restaurantPrice.text = restaurant.price
        Glide.with(viewHolder.itemView).load(restaurant.imageUrl).apply(
            RequestOptions().transforms(
            CenterCrop(), RoundedCorners(20)
        )).into(viewHolder.itemView.restaurantImage)

    }

    override fun getLayout(): Int {
        return R.layout.item_restaurant
    }
}
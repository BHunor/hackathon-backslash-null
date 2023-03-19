package edu.hackathon.moviematch.ui.welcome

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.transition.Transition
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse

interface IOnItemClickListener {
        fun onItemClick(item:SearchFilmResultResponse)
}
class GridAdapter(private val context: Context, private val searchFilmResultResponses: MutableList<SearchFilmResultResponse>, private val listener:IOnItemClickListener) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val imageView: ImageView
                if (convertView == null) {
                        imageView = ImageView(context)
                        imageView.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                        imageView = convertView as ImageView
                }
                Glide.with(context)
                        .load("https://image.tmdb.org/t/p/w500/"+searchFilmResultResponses.get(position).posterPath)
                        .placeholder(R.drawable.rounded)
                        .fitCenter()
                        .into(imageView)
                imageView.background = ContextCompat.getDrawable(context, R.drawable.rounded)
                imageView.setOnClickListener {
                        val anim = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                        imageView.startAnimation(anim)
                        listener.onItemClick(searchFilmResultResponses.get(position))
                }
                return imageView
        }

        override fun getItem(position: Int): Any {
                return searchFilmResultResponses[position]
        }

        override fun getItemId(position: Int): Long {
                return position.toLong()
        }

        override fun getCount(): Int {
                return searchFilmResultResponses.size
        }
}
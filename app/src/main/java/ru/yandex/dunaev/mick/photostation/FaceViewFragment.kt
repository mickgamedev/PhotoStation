package ru.yandex.dunaev.mick.photostation


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import ru.yandex.dunaev.mick.photostation.databinding.FragmentFaceViewBinding
import androidx.viewpager.widget.ViewPager




class FaceViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFaceViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_face_view,container,false)
        val model: MainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = model
        val imageAdapter = ImageAdapter().apply {
            bitmaps = model.photoBitmaps
            context = this@FaceViewFragment.context
        }

        binding.pager.adapter = imageAdapter

        return binding.root
    }
}

class ImageAdapter: PagerAdapter(){
    lateinit var bitmaps: List<Bitmap>
    var context: Context? = null

    override fun isViewFromObject(view: View, ob: Any) = view == ( ob as ImageView)

    override fun getCount() = bitmaps.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        context?: return Any()

        return ImageView(context).apply {
            setImageBitmap(bitmaps[position])
            scaleType = ImageView.ScaleType.CENTER_CROP
            (container as ViewPager).addView(this, 0)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, ob: Any) {
        (container as ViewPager).removeView(ob as ImageView)
    }
}

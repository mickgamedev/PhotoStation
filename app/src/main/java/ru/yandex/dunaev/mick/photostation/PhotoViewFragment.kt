package ru.yandex.dunaev.mick.photostation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import ru.yandex.dunaev.mick.photostation.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPhotoViewBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_photo_view,container,false)
        val model: MainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = model
        return binding.root
    }
}

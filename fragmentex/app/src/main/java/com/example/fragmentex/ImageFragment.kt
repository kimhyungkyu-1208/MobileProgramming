package com.example.fragmentex

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fragmentex.databinding.FragmentImageBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"


class ImageFragment : Fragment() {

//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    var binding:FragmentImageBinding?=null
    val myViewModel:MyViewModel by activityViewModels()
    val imglist = arrayListOf<Int>(R.drawable.img1, R.drawable.img2, R.drawable.img3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply{
            imageView.setOnClickListener {
                if(resources.configuration.orientation== Configuration.ORIENTATION_PORTRAIT){
                    val i = Intent(activity, SecondActivity::class.java)
                    i.putExtra("imgNum", myViewModel.selectednum.value)
                    startActivity(i)
                }
            }
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    R.id.radioBtn1 ->{
                        myViewModel.setLiveData(0)
                    }
                    R.id.radioBtn2 ->{
                        myViewModel.setLiveData(1)

                    }
                    R.id.radioBtn3 ->{
                        myViewModel.setLiveData(2)

                    }
                }
                imageView.setImageResource(imglist[myViewModel.selectednum.value!!])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ImageFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ImageFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}
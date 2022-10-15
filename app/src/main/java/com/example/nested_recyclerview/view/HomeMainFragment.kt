package com.example.nested_recyclerview.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nested_recyclerview.R
import com.example.nested_recyclerview.adapter.ParentItemAdapter
import com.example.nested_recyclerview.databinding.FragmentHomeMainBinding
import com.example.nested_recyclerview.model.ChildItem
import com.example.nested_recyclerview.model.ParentItem

class HomeMainFragment : Fragment() {

    private lateinit var parentItemAdapter: ParentItemAdapter
    private lateinit var parentItemsList: ArrayList<ParentItem>
    private lateinit var childItemsList: ArrayList<ChildItem>
    private lateinit var binding: FragmentHomeMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home_main,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupAdapter()
    }

    private fun init(){
        parentItemsList = ArrayList()
        childItemsList = ArrayList()
        childItemsList.add(ChildItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYUlhC5e96MEqqTye1za7lOBLD4KvYGSZ8x-Iz3pk&s"," https://drive.google.com/file/d/1w_Bwv8IzbYvrLDupRrvTDeKrQEtwd1Ci/view"))
        childItemsList.add(ChildItem("https://filmschoolrejects.com/wp-content/uploads/2020/01/2019-rewind-animated-series.jpg"," https://drive.google.com/file/d/1w_Bwv8IzbYvrLDupRrvTDeKrQEtwd1Ci/view"))
        childItemsList.add(ChildItem("https://filmschoolrejects.com/wp-content/uploads/2019/12/decade-animatedseries.jpg"," https://drive.google.com/file/d/1w_Bwv8IzbYvrLDupRrvTDeKrQEtwd1Ci/view"))
        childItemsList.add(ChildItem("https://static-koimoi.akamaized.net/wp-content/new-galleries/2021/05/shinchan-family-guy-others-cartoon-series-that-face-a-ban-due-to-its-not-so-kiddish-content004.jpg"," https://drive.google.com/file/d/1w_Bwv8IzbYvrLDupRrvTDeKrQEtwd1Ci/view"))
        childItemsList.add(ChildItem("https://www.indiewire.com/wp-content/uploads/2017/05/d691d2e7-5227-4371-905e-5ff6986f2f6b-young-justice-league-the-animated-series-superman-images-gallery-604927.jpg?w=780"," https://drive.google.com/file/d/1w_Bwv8IzbYvrLDupRrvTDeKrQEtwd1Ci/view"))

        parentItemsList.add(ParentItem("Popular Shows", childItemsList))
        parentItemsList.add(ParentItem("Continue Watching", childItemsList))
        parentItemsList.add(ParentItem("Favorites", childItemsList))
        parentItemsList.add(ParentItem("Serials", childItemsList))
        parentItemsList.add(ParentItem("Live Tv", childItemsList))
        parentItemsList.add(ParentItem("Famous Seasons", childItemsList))
        parentItemsList.add(ParentItem("My Serials", childItemsList))

    }

    private fun setupAdapter(){
        parentItemAdapter= ParentItemAdapter(parentItemsList){
            Toast.makeText(activity,"Session ",Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter= parentItemAdapter
        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }


}
package com.example.sportsdbapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sportsdbapp.R
import com.example.sportsdbapp.util.getImage
import com.example.sportsdbapp.util.makePlaceholder
import com.example.sportsdbapp.viewmodel.SportDetailViewModel
import kotlinx.android.synthetic.main.fragment_sport_detail.*


class SportDetailFragment : Fragment() {
    private lateinit var viewModel : SportDetailViewModel
    private var sportId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sport_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            sportId = SportDetailFragmentArgs.fromBundle(it).sportId

        }

        viewModel = ViewModelProviders.of(this).get(SportDetailViewModel::class.java)
        viewModel.getRoomData(sportId)



        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.sportLiveData.observe(viewLifecycleOwner, Observer { sport ->
            sport?.let {

                sportNameText.text = it.sportName
                sportFormatText.text = it.sportFormat
                sportDescriptionText.text = it.sportDescription
                context?.let {
                    sportImageView.getImage(sport.sportImage, makePlaceholder(it))

                }

            }
        })
    }

}
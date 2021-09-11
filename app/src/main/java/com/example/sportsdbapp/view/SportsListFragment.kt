package com.example.sportsdbapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsdbapp.R
import com.example.sportsdbapp.adapter.SportRecyclerAdapter
import com.example.sportsdbapp.model.Sport
import com.example.sportsdbapp.viewmodel.SportListViewModel
import kotlinx.android.synthetic.main.fragment_sports_list.*


class SportsListFragment : Fragment() {

    private lateinit var viewModel : SportListViewModel
    private val recyclerSportAdapter = SportRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SportListViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerSportAdapter

        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            hataMesajiText.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModel.refreshFromWeb()
            swipeRefreshLayout.isRefreshing = false

        }

        observeLiveData()


        

    }

    fun observeLiveData(){
        viewModel.sports.observe(viewLifecycleOwner, Observer { sport ->
            sport?.let {

                recyclerView.visibility = View.VISIBLE
                recyclerSportAdapter.updateSportList(sport)

            }
        })

        viewModel.sportErrorMessage.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if (it){
                    hataMesajiText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    hataMesajiText.visibility = View.GONE
                }
            }
        })

        viewModel.sportLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    recyclerView.visibility = View.GONE
                    hataMesajiText.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }else {
                    progressBar.visibility = View.GONE
                }
            }
        })

    }

}
package com.dicodingeventstracker.domain.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import com.dicodingeventstracker.adapters.EventsAdapter
import com.dicodingeventstracker.adapters.FavoriteEventsAdapter
import com.dicodingeventstracker.databinding.FragmentFavoriteEventsBinding
import com.dicodingeventstracker.domain.entity.Events
import com.dicodingeventstracker.domain.past.PastEventsFragmentDirections
import com.dicodingeventstracker.domain.viewmodels.FavoriteEventsViewModel
import com.dicodingeventstracker.domain.viewmodels.PastEventsViewmodel

@AndroidEntryPoint
class FavoriteEventsFragment : Fragment() {

    private var _binding: FragmentFavoriteEventsBinding?= null
    private val binding get() = _binding!!
    private val adapter by lazy { FavoriteEventsAdapter() }
    private lateinit var viewModel: FavoriteEventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteEventsBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(this)[FavoriteEventsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListRvFavEvent()
        showListFavEvent()
    }



    private fun setupListRvFavEvent() {
        binding.rvEventsFavourite.adapter = adapter
        adapter.setOnClickListener(object :FavoriteEventsAdapter.OnEventFavoriteClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = FavoriteEventsFragmentDirections.actionFavoriteEventsFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
    }

    private fun showListFavEvent() {
        viewModel.showFavoriteEvents.observe(viewLifecycleOwner){ event->
            setStateOfView(event.isNullOrEmpty())
            adapter.setDataFavorite(event)
        }
    }

    private fun setStateOfView(isEmpty: Boolean){
        binding.apply {
            viewNoEvent.root.isVisible = isEmpty
            rvEventsFavourite.isVisible = !isEmpty
        }
    }
}
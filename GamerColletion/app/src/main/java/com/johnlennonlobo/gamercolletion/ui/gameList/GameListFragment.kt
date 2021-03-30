package com.johnlennonlobo.gamercolletion.ui.gameList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.johnlennonlobo.gamercolletion.R
import com.johnlennonlobo.gamercolletion.data.db.AppDataBase
import com.johnlennonlobo.gamercolletion.databinding.GameListFragmentBinding
import com.johnlennonlobo.gamercolletion.repository.DatabaseDataSource
import com.johnlennonlobo.gamercolletion.repository.GamerRepository

class GameListFragment : Fragment() {

    private var _binding: GameListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDao = AppDataBase.getInstance(requireContext()).gamerDAO
                val repository: GamerRepository = DatabaseDataSource(subscriberDao)
                return GameListViewModel(repository) as T
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GameListFragmentBinding.inflate(inflater, container, false)

        configureListeners()
        observerViewModel()

        return binding.root
    }

    private fun configureListeners() {
        binding.btnFloatAdd.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }
    }

    private fun observerViewModel() {
        viewModel.allGamersEvent.observe(viewLifecycleOwner) {allGamers ->
            val gameListAdapter = GameListAdapter(allGamers).apply {
              onItemClinck = {
                  val direction = GameListFragmentDirections.actionGameListFragmentToGameFragment(it)
                  findNavController().navigate(direction)
              }
            }
            with(binding.recycleGame) {
                hasFixedSize()
                adapter = gameListAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGamers()
    }
}


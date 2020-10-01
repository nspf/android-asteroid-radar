package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val database = AsteroidDatabase.getDatabase(application)
        val dao = database.asteroidDatabaseDao
        val repository = AsteroidRepository(database)

        val viewModelFactory = MainViewModelFactory(dao, application, repository)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}

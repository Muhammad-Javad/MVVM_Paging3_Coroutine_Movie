package com.javadsh98.movie.presentation.main.fragment.series

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.javadsh98.movie.R
import com.javadsh98.movie.utils.LOADING_VIEW
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_series.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SeriesFragment : Fragment(R.layout.fragment_series) {

    val viewmodel by viewModels<SeriesViewModel>()
    lateinit var adapter : SeriesAdapter

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupAdapter()
        getSeries(sharedPreferences.getString("category", "popular")!!
            , sharedPreferences.getString("language", "en-US")!!)
        setupUi()

    }

    private fun setupUi() {
        button_series_retry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupAdapter() {
        adapter = SeriesAdapter {
            val action = SeriesFragmentDirections.actionSeriesFragmentToSeriesDetailFragment(it, it.name)
            findNavController().navigate(action)
        }

        recyclerView_series.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup =object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.getItemViewType(position) == LOADING_VIEW) 2 else 1
            }

        }

        adapter.addLoadStateListener {
            val loadState = it.source.refresh
            recyclerView_series.isVisible = loadState !is LoadState.Error
            button_series_retry.isVisible = loadState is LoadState.Error
            textView_series_faild.isVisible = loadState is LoadState.Error
            progressBar_series_loading.isVisible = loadState is LoadState.Loading
            if (loadState is LoadState.Error){
                textView_series_faild.text = loadState.error.localizedMessage
            }
        }

        recyclerView_series.layoutManager = layoutManager

        recyclerView_series.adapter = adapter.withLoadStateFooter(FooterAdapter{
            adapter.retry()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.collapseActionView()
                searchView.clearFocus()
                query?.let {
                    searchingSeries(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        //setting
        val setting = menu.findItem(R.id.menu_setting)
        setting.isVisible = false

    }

    fun searchingSeries(query: String){
        lifecycleScope.launch {
            viewmodel.getSearchedSeries(query)?.let {
                it.collectLatest {
                    adapter.submitData(
                        it
                    )
                }
            }
        }
    }

    fun getSeries(category: String, language: String){
        Timber.i("getSeries")
        lifecycleScope.launch{
            viewmodel.getSeries(category, language)?.let {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

}
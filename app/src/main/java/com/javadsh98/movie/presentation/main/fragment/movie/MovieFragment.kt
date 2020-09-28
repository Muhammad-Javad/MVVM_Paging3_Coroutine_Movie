package com.javadsh98.movie.presentation.main.fragment.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.javadsh98.movie.R
import com.javadsh98.movie.presentation.setting.activity.SettingActivity
import com.javadsh98.movie.utils.DATA_VIEW
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    lateinit var adapter: MovieAdapter
    val viewmodel: MovieViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initUi()
        initAdapter(view)


    }

    override fun onResume() {
        super.onResume()
        getMovies(sharedPreferences.getString("category", "popular")!!
            ,  sharedPreferences.getString("language", "en-US")!!)
    }

    private fun initUi() {
        button_movie_retry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initAdapter(view: View) {

        adapter = MovieAdapter {
            val action =
                MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(it, it.title)
            findNavController().navigate(action)
        }.apply {
            addLoadStateListener {
                val loadstate = it.source.refresh
                view.recyclerView_movie.isVisible = loadstate is LoadState.NotLoading
                view.progressBar_movie.isVisible = loadstate is LoadState.Loading
                view.button_movie_retry.isVisible = loadstate is LoadState.Error
                view.textView_movie_faild.isVisible = loadstate is LoadState.Error
            }
        }


        recyclerView_movie.setHasFixedSize(true)
        val layoutmanager = GridLayoutManager(requireContext(), 2)
        layoutmanager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == DATA_VIEW) 1 else 2
            }

        }



        recyclerView_movie.layoutManager = layoutmanager
        recyclerView_movie.adapter = adapter.withLoadStateFooter(
            FooterAdapter {
                adapter.retry()
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)


        val searchItem = menu.findItem(R.id.menu_search)
        val search = searchItem.actionView as SearchView
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        search.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.i("$query")
                search.clearFocus()
                searchItem.collapseActionView()
                query?.let {
                    searchingMovies(it);
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                goSetting()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchingMovies(text: String) {

        lifecycleScope.launch {
            viewmodel.getSearchedMovie(text)
                .collectLatest {
                    adapter.submitData(it)
                }
        }

    }

    private fun getMovies(category: String, language: String) {
        Timber.i("getMovies")
        lifecycleScope.launch {
            viewmodel.getMovie(category, language)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    fun goSetting() {
        startActivity(Intent(activity, SettingActivity::class.java))
    }


}
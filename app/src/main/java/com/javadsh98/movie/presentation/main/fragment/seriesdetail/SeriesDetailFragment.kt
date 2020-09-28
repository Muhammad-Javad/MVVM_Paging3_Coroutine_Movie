package com.javadsh98.movie.presentation.main.fragment.seriesdetail

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.Placeholder
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.material.chip.Chip
import com.javadsh98.movie.R
import com.javadsh98.movie.data.remote.model.MovieTrailer
import com.javadsh98.movie.data.remote.model.TvSeries
import com.javadsh98.movie.data.remote.model.TvTrailer
import com.javadsh98.movie.presentation.main.fragment.list.cast.CastAdapter
import com.javadsh98.movie.presentation.main.fragment.list.review.ReviewAdapter
import com.javadsh98.movie.presentation.main.fragment.list.trailer.TrailerAdapter
import com.javadsh98.movie.presentation.main.fragment.seriesdetail.SeriesDetailFragmentArgs
import com.javadsh98.movie.presentation.main.fragment.seriesdetail.SeriesDetailViewModel
import com.javadsh98.movie.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import timber.log.Timber

@AndroidEntryPoint
class SeriesDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    val navArgs by navArgs<SeriesDetailFragmentArgs>()
    val viewmodel by viewModels<SeriesDetailViewModel>()
    val mid: Int by lazy {
        navArgs.series.id
    }
    lateinit var trailers: List<TvTrailer>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupCast(mid)
        setupReviews(mid)
        setupTrailer(mid)
        setupUi(navArgs.series)

    }

    private fun setupUi(tvSeries: TvSeries) {
        series_cover.load(tvSeries.fullBackdropPath){
            placeholder(R.drawable.ic_launcher_background)
        }
        series_poster.load(tvSeries.fullBackdropPath){
            placeholder(R.drawable.ic_launcher_background)
        }
        series_title_text.text = tvSeries.name
        language_text.text = tvSeries.original_language
        release_date_text.text = tvSeries.first_air_date
        rating_text.text = tvSeries.vote_average.toString()
        overview_text.text = tvSeries.overview

        //set chips
        viewmodel.genres.observe(viewLifecycleOwner, Observer { listOfGenres ->
            listOfGenres?.let {

                for (elem in tvSeries.genre_ids) {
                    val filteredListOfGenres = listOfGenres.filter { it.id == elem }
                    for (item in filteredListOfGenres) {
                        val chip = Chip(requireContext())
                        chip.setChipBackgroundColorResource(android.R.color.transparent)
                        chip.chipStrokeColor = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.darker_gray
                            )
                        )
                        chip.chipStrokeWidth = Utils.dptoPx(requireContext(), 1)

                        chip.text = item.name
                        chip_group.addView(chip)
                    }
                }
            }
        })
    }

    private fun setupTrailer(movieId: Int) {
        val adapter = TrailerAdapter{
            openYoutube(it as MovieTrailer)
        }
        val rec = recycler_trailer
//        rec.setHasFixedSize(true)
        rec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rec.adapter = adapter
        viewmodel.getTrailer(movieId).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.seriesSubmit(it)
                trailers = it
            }
        })


    }

    private fun openYoutube(it: MovieTrailer) {
        val i = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            data = Uri.parse("https://www.youtube.com/watch?v=${it.key}")
            Timber.d("https://www.youtube.com/watch?v=${it.key}")
        }
        if (Utils.isAppInstalled(requireContext(), "com.google.android.youtube")) {
            i.`package` = "com.google.android.youtube"
        }
        startActivity(i)
    }

    private fun setupReviews(movieId: Int) {
        val adapter = ReviewAdapter()
        val rec = recycler_review
//        rec.setHasFixedSize(true)
        rec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rec.adapter = adapter
        viewmodel.getReviews(movieId).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitSeries(it)
            }
        })

    }

    private fun setupCast(movieId: Int) {
        val adapter = CastAdapter()
        val rec = recycler_cast
//        rec.setHasFixedSize(true)
        rec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rec.adapter = adapter
        viewmodel.getCast(movieId).observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitSeries(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_share -> {
                shareInfo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareInfo() {
        if (trailers.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_SEND)
            val firstTrailer = "https://youtu.be/${trailers[0].key}"
            intent.putExtra(Intent.EXTRA_TEXT, "Check out: $firstTrailer")
            intent.type = "text/plain"
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(Intent.createChooser(intent,"Share using : "))
            }
        } else {
            Toast.makeText(activity, "Share failed", Toast.LENGTH_SHORT).show()
        }
    }


}
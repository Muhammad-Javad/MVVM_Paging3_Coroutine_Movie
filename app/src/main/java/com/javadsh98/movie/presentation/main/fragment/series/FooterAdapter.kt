package com.javadsh98.movie.presentation.main.fragment.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javadsh98.movie.R
import com.javadsh98.movie.presentation.main.fragment.movie.retry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_load.view.*

class FooterAdapter(val retry: retry) : LoadStateAdapter<FooterAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(loadState: LoadState, retry: retry) = with(itemView) {

            button_loadstate_retry.isVisible = loadState is LoadState.Error
            textView_loadstate_faild.isVisible = loadState is LoadState.Error
            progressBar_loadstate_loading.isVisible = loadState is LoadState.Loading

            if (loadState is Error)
                textView_loadstate_faild.text = loadState.localizedMessage

            button_loadstate_retry.setOnClickListener { retry.invoke() }

        }

        companion object {
            fun create(parent: ViewGroup): LoadStateViewHolder {
                return LoadStateViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_load, parent, false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent)
    }

}
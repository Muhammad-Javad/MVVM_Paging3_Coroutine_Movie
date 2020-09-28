package com.javadsh98.movie.presentation.ext

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class SuperViewModel : ViewModel(){

    val viewmodelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewmodelJob)

    override fun onCleared() {
        super.onCleared()
        viewmodelJob.cancel()
    }

}
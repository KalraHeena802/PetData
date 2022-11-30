package com.example.androidmvvmtask.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.androidmvvmtask.adapter.PetAdapter
import com.example.androidmvvmtask.constant.AppConstant
import com.example.androidmvvmtask.extension.getData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PetViewModel@Inject constructor(@ApplicationContext  context: Context):ViewModel() {

     val petAdapter by lazy {
        PetAdapter()
    }

    init {
        context.getData(AppConstant.PET_LIST_NAME) {
            petAdapter.submitList(it?.pets)
        }
    }
}
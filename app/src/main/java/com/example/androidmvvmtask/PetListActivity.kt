package com.example.androidmvvmtask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmtask.constant.AppConstant
import com.example.androidmvvmtask.databinding.ActivityPetListBinding
import com.example.androidmvvmtask.extension.getContentTimeHour
import com.example.androidmvvmtask.extension.isValidTime
import com.example.androidmvvmtask.extension.showErrorDialog
import com.example.androidmvvmtask.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetListActivity : AppCompatActivity() {


    lateinit var viewModel: PetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPetListBinding>(this, R.layout.activity_pet_list)
        viewModel = ViewModelProvider(this).get(PetViewModel::class.java)
        binding.petViewModel = viewModel
        title = "Pet List"

        binding.recyclerView.apply {
            adapter = viewModel.petAdapter
        }


        viewModel.petAdapter.callBackListener = { petX ->

            getContentTimeHour(AppConstant.CONFIG_NAME) {
                val time = it?.settings?.workHours
                isValidTime(time) { validTime ->
                    if (validTime) {
                        val intent = Intent(this, PetDetailActivity::class.java)
                        intent.putExtra(AppConstant.CONTENT_URL, petX.content_url)
                        startActivity(intent)
                    } else {
                        showErrorDialog(time)
                    }
                }

            }
        }


    }


}
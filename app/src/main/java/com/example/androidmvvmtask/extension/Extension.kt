package com.example.androidmvvmtask.extension

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.androidmvvmtask.databinding.DialogErrorBinding
import com.example.androidmvvmtask.databinding.PetItemBinding
import com.example.androidmvvmtask.enum.Days
import com.example.androidmvvmtask.model.ContentTiming
import com.example.androidmvvmtask.model.Pet
import com.example.androidmvvmtask.model.PetX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

fun Activity.getDataList(fileName: String, result: (List<PetX>?) -> Unit) {
    try {
        val jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
        val listPersonType = object : TypeToken<List<PetX>>() {}.type

        val petList: List<PetX> = Gson().fromJson(jsonString, listPersonType)
        result(petList)
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        result(null)
    }


}

fun Context.getData(fileName: String, result: (Pet?) -> Unit) {
    try {
        val jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
        val data = Gson().fromJson(jsonString, Pet::class.java)
        result(data)
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        result(null)
    }
}

fun Context.getContentTimeHour(fileName: String, result: (ContentTiming?) -> Unit) {
    try {
        val jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
        val data = Gson().fromJson(jsonString, ContentTiming::class.java)
        result(data)
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        result(null)
    }
}

fun Context.isValidTime(workingHour: String?, result: (Boolean) -> Unit) {
    val timeList: List<String>? = workingHour?.split(" ")
    val dayList: List<String>? = timeList?.get(0)?.split("-")

    if (getValidTime(timeList)) {
        getValidDay(dayList) {
            if (it) {
                result.invoke(true)
            } else {
                result.invoke(false)
            }
        }

    } else {
        result.invoke(false)
    }


}

private fun getValidTime(timeList: List<String>?): Boolean {
    val calender = Calendar.getInstance()
    val currentDate: Date = calender.time

    val startTime = timeList?.get(1)
    val endTime = timeList?.get(3)

    val tf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val timeFormattedDate: String = tf.format(currentDate)

    val strDate = tf.parse(startTime)
    val endDate = tf.parse(endTime)
    val comDate = tf.parse(timeFormattedDate)

    return if (comDate?.compareTo(strDate)!! > 0 && comDate.compareTo(endDate) < 0) {
        true
    } else {
        false
    }
}

private fun getValidDay(dayList: List<String>?, result: (Boolean) -> Unit) {

    val firstDay = dayList?.get(0)//M
    val lastDay = dayList?.get(1)//F

    val firstIndex = Days.values().indexOfFirst {
        it.value == firstDay
    }
    val lastIndex = Days.values().indexOfLast {
        it.value == lastDay
    }
    val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    if (dayOfWeek >= firstIndex && dayOfWeek <= lastIndex.plus(1)) {
        result.invoke(true)
    } else {
        result.invoke(false)

    }

}

fun Context.showErrorDialog(workingHour: String?) {
    val timeList: List<String>? = workingHour?.split(" ")
    val dayList: List<String>? = timeList?.get(0)?.split("-")
    val startTime = timeList?.get(1)
    val endTime = timeList?.get(3)

    val firstDay = dayList?.get(0)//M
    val lastDay = dayList?.get(1)//F

    val firstIndex = Days.values().indexOfFirst {
        it.value == firstDay
    }
    val lastIndex = Days.values().indexOfLast {
        it.value == lastDay
    }

    val startDate = Days.values().get(firstIndex).fullName
    val endDate = Days.values().get(lastIndex).fullName

    val binding = DialogErrorBinding.inflate(LayoutInflater.from(this))
    val builder = AlertDialog.Builder(this)
    builder.setView(binding.root)
    val dialog = builder.create()
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

    binding.tvDescription.text = "The app is blocked for now.\nYou can see the detail from $startDate to $endDate between $startTime - $endTime."
    dialog.show()
    binding.btnOk.setOnClickListener {
        dialog.dismiss()
    }


}
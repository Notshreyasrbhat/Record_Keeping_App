package com.project.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.recordkeeper.databinding.FragmentCyclingBinding
import com.project.recordkeeper.editRecord.EditRecordActivity
import com.project.recordkeeper.editRecord.SCREEN_DATA

class CyclingFragment: Fragment() {

    private lateinit var binding : FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCyclingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        binding.textViewLongestRideValue.text=cyclingPreferences.getString("Longest Ride ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",null)
        binding.textViewLongestRideDate.text=cyclingPreferences.getString("Longest Ride ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",null)
        binding.textViewBestSpeedValue.text=cyclingPreferences.getString("Best Speed  ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",null)
        binding.textViewBestSpeedDate.text=cyclingPreferences.getString("Best Speed ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",null)
        binding.textViewBiggestClimbValue.text=cyclingPreferences.getString("Biggest Climb ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}",null)
        binding.textViewBiggestClimbDate.text=cyclingPreferences.getString("Biggest Climb ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}",null)
    }

    private fun setupClickListener() {
        binding.containerLongestRide.setOnClickListener { launchEditCyclingRecordScreen("Longest Ride","Distance") }
        binding.containerBestSpeed.setOnClickListener { launchEditCyclingRecordScreen("Best Speed","Speed") }
        binding.containerBiggestClimb.setOnClickListener { launchEditCyclingRecordScreen("Biggest Climb","Height") }
    }

    private fun launchEditCyclingRecordScreen(record: String,recordFieldHint :String) {

        val intent= Intent(context, EditRecordActivity::class.java)
        intent.putExtra(SCREEN_DATA, EditRecordActivity.ScreenData(record,FILENAME,recordFieldHint))
        startActivity(intent)
    }
    companion object {
        const val FILENAME ="cycling"
    }

}
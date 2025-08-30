package com.project.recordkeeper.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.recordkeeper.databinding.FragmentRunningBinding
import com.project.recordkeeper.editRecord.EditRecordActivity
import com.project.recordkeeper.editRecord.SCREEN_DATA

// Fragment showing and managing running records
class RunningFragment : Fragment() {

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners() // attach click actions for record editing
    }

    override fun onResume() {
        super.onResume()
        displayRecords() // refresh records whenever fragment is visible
    }

    // Load and display saved records from SharedPreferences
    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        binding.textView5kmValue.text = runningPreferences.getString("5km ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", null)
        binding.textView5kmDate.text = runningPreferences.getString("5km ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", null)

        binding.textView10kmValue.text = runningPreferences.getString("10km ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", null)
        binding.textView10kmDate.text = runningPreferences.getString("10km ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", null)

        binding.textViewHalfMarathonValue.text = runningPreferences.getString("Half Marathon ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", null)
        binding.textViewHalfMarathonDate.text = runningPreferences.getString("Half Marathon ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", null)

        binding.textViewMarathonValue.text = runningPreferences.getString("Marathon ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", null)
        binding.textViewMarathonDate.text = runningPreferences.getString("Marathon${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", null)
    }

    // Set listeners for each record container
    private fun setupClickListeners() {
        binding.container5km.setOnClickListener { launchRunningScreen("5km") }
        binding.container10km.setOnClickListener { launchRunningScreen("10km") }
        binding.containerHalfMarathon.setOnClickListener { launchRunningScreen("Half Marathon") }
        binding.containerMarathon.setOnClickListener { launchRunningScreen("Marathon") }
    }

    // Launch screen to edit specific distance record
    private fun launchRunningScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(SCREEN_DATA, EditRecordActivity.ScreenData(distance, FILENAME, "Time"))
        startActivity(intent)
    }

    companion object {
        const val FILENAME = "running"
    }
}

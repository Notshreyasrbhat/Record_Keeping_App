package com.project.recordkeeper.editRecord

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.project.recordkeeper.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val SCREEN_DATA = "screen_data"

// Activity for adding, editing, or deleting a single record
class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding

    // Extract screen setup info (distance, pref file, hint) from intent
    private val screenData : ScreenData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(SCREEN_DATA, ScreenData::class.java) as ScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(SCREEN_DATA) as ScreenData
        }
    }

    // SharedPreferences file based on record type (running / cycling)
    private val recordPreferences by lazy { getSharedPreferences(screenData.sharedPreferencesName, MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()       // configure UI elements
        displayRecord() // load saved values if present
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Setup UI labels and button actions
    private fun setupUi() {
        title = "${screenData.record} Record"
        binding.textInputRecord.hint = screenData.recordFieldHint

        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }
        binding.buttonDelete.setOnClickListener {
            clearRecord()
            finish()
        }
    }

    // Populate input fields with saved record data
    private fun displayRecord() {
        binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} $SHARED_PREFERENCE_RECORD_KEY", null))
        binding.editTextDate.setText(recordPreferences.getString("${screenData.record}$SHARED_PREFERENCE_DATE_KEY", null))
    }

    // Save updated record + date to SharedPreferences
    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()

        recordPreferences.edit {
            putString("${screenData.record} $SHARED_PREFERENCE_RECORD_KEY", record)
            putString("${screenData.record} $SHARED_PREFERENCE_DATE_KEY", date)
        }
    }

    // Remove record + date for this distance
    private fun clearRecord() {
        recordPreferences.edit {
            remove("${screenData.record} $SHARED_PREFERENCE_RECORD_KEY")
            remove("${screenData.record} $SHARED_PREFERENCE_DATE_KEY")
        }
    }

    // Bundle of metadata passed when launching this activity
    data class ScreenData(
        val record: String,
        val sharedPreferencesName: String,
        val recordFieldHint: String
    ) : Serializable

    companion object {
        const val SHARED_PREFERENCE_RECORD_KEY = "record"
        const val SHARED_PREFERENCE_DATE_KEY = "date"
    }
}

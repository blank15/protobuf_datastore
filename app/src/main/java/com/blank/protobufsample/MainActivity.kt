package com.blank.protobufsample

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.blank.protobufsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val Context.preferencesStore: DataStore<Preference> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = PrefSerializer
    )
    private lateinit var viewModel: PrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this,PrefViewModelFactory(
            PrefRepository(preferencesStore)
        ))[PrefViewModel::class.java]

        viewModel.getDataPref()
        observeValue()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.saveName(edSaveName.text.toString())
            }
        }
    }

    private fun observeValue() {
        viewModel.namePref.observe(this) {
            binding.tvName.text = it
        }
    }


    companion object {
        private const val DATA_STORE_FILE_NAME = "prefs.pb"
    }
}
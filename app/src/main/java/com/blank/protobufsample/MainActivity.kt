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

        val bookService = BookService(GrpcService())

        viewModel = ViewModelProvider(this,PrefViewModelFactory(
            PrefRepository(preferencesStore,bookService)
        ))[PrefViewModel::class.java]

        viewModel.getDataPref()
        observeValue()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.saveName(edSaveName.text.toString())
                viewModel.getBook()
            }

            btnGetBook.setOnClickListener {
                viewModel.getBook()
            }
        }
    }

    private fun observeValue() {
        viewModel.namePref.observe(this) {
            binding.tvName.text = it
        }
        viewModel.listBook.observe(this){
            binding.tvBook.text = it[0].title
        }
    }


    companion object {
        private const val DATA_STORE_FILE_NAME = "prefs.pb"
    }
}
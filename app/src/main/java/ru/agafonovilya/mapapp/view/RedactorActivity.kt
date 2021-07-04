package ru.agafonovilya.mapapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.agafonovilya.mapapp.App
import ru.agafonovilya.mapapp.databinding.ActivityRedactorBinding
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker
import ru.agafonovilya.mapapp.viewModel.MarkerViewModel
import ru.agafonovilya.mapapp.viewModel.MainViewModelFactory

class RedactorActivity: AppCompatActivity() {
    private val viewModel: MarkerViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private lateinit var binding: ActivityRedactorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRedactorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myMarker = intent.extras?.getSerializable("myMarker") as MyMarker

        binding.markerTitle.text.append(myMarker.title)
        binding.markerDescription.text.append(myMarker.description)
        binding.markerLatitude.text = myMarker.latitude.toString()
        binding.markerLongitude.text = myMarker.longitude.toString()

        binding.removeBtn.setOnClickListener {
            viewModel.removeMarker(myMarker)
            onBackPressed()
        }
        binding.closeBtn.setOnClickListener {
            with(myMarker) {
                title = binding.markerTitle.text.toString()
                description = binding.markerDescription.text.toString()
            }
            viewModel.updateMarker(myMarker)

            onBackPressed()
        }
    }


}
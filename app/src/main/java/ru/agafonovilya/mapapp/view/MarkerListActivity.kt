package ru.agafonovilya.mapapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.agafonovilya.mapapp.App
import ru.agafonovilya.mapapp.databinding.ActivityMarkersListBinding
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker
import ru.agafonovilya.mapapp.view.adapter.MarkerListAdapter
import ru.agafonovilya.mapapp.viewModel.MarkerViewModel
import ru.agafonovilya.mapapp.viewModel.MainViewModelFactory

class MarkerListActivity : AppCompatActivity() {

    private val viewModel: MarkerViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarkerListAdapter
    private lateinit var binding: ActivityMarkersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarkersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        viewModel.subscribeToLiveData().observe(this) { onStateChange(it) }
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MarkerListAdapter(openMarkerRedactor())
        recyclerView.adapter = adapter
    }

    private fun onStateChange(allMarkers: List<MyMarker>) {
        adapter.markersList = allMarkers
    }

    private fun openMarkerRedactor(): (MyMarker) -> Unit = {
        val intent = Intent(this, RedactorActivity::class.java).apply {
            putExtra("myMarker", it)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
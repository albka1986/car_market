package ua.com.ponomarenko.carmarket.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ua.com.ponomarenko.carmarket.R
import ua.com.ponomarenko.carmarket.internal.toast
import ua.com.ponomarenko.carmarket.ui.adapters.CustomSpinnerAdapter
import ua.com.ponomarenko.carmarket.ui.base.ScopedActivity

class MainActivity : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: MainViewModel
    private val mainViewModelFactory: MainViewModelFactory by instance()

    private var currentManufacturer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val manufacturers = viewModel.manufacturers.await()
        manufacturers.observe(this@MainActivity, Observer {
            if (it == null) return@Observer
            initManufacturerSpinner(it)
        })
    }

    private fun loadModels(manufacturer: String?) = launch(Dispatchers.Main) {
        val types = viewModel.getTypesAsync(manufacturer).await()
        types.observe(this@MainActivity, Observer {
            initTypeSpinner(it)
        })
    }

    private fun initManufacturerSpinner(manufactures: Map<String, String>?) {
        manufacturer_spinner.adapter = CustomSpinnerAdapter(this, manufactures)
        manufacturer_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                toast("Manufacture: nothing selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val key = parent?.getItemAtPosition(position) as String?
                loadModels(key)
                currentManufacturer = key
            }
        }
    }

    private fun initTypeSpinner(types: Map<String, String>?) {
        type_spinner.adapter = CustomSpinnerAdapter(this, types).apply {


        }
        type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                toast("Type: nothing selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val keyType = parent?.getItemAtPosition(position) as String?
                loadYears(currentManufacturer, keyType)
            }
        }
    }

    private fun loadYears(manufacturer: String?, type: String?) = launch(Dispatchers.Main) {
        val builtYears = viewModel.getYearsAsync(manufacturer, type).await()
        builtYears.observe(this@MainActivity, Observer {
            initBuiltYeatsSpinner(it)
        })
    }

    private fun initBuiltYeatsSpinner(builtYears: Map<String, String>?) {
        year_spinner.adapter = CustomSpinnerAdapter(this, builtYears)
    }



}

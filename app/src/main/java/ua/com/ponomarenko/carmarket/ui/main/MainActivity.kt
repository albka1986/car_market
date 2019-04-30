package ua.com.ponomarenko.carmarket.ui.main

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

const val KEY_MANUFACTURER = "Manufacturer"
const val KEY_TYPE = "Type"
const val KEY_YEAR = "BuiltYear"

class MainActivity : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: MainViewModel
    private val mainViewModelFactory: MainViewModelFactory by instance()

    private var currentManufacturer: String? = null
    private var selectedManufacturerPosition: Int? = null
    private var selectedTypePosition: Int? = null
    private var selectedYearPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        bindUI()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MANUFACTURER, manufacturer_spinner.selectedItemPosition)
        outState.putInt(KEY_TYPE, type_spinner.selectedItemPosition)
        outState.putInt(KEY_YEAR, year_spinner.selectedItemPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            selectedManufacturerPosition = it.getInt(KEY_MANUFACTURER)
            selectedTypePosition = it.getInt(KEY_TYPE)
            selectedYearPosition = it.getInt(KEY_YEAR)
        }
    }


    private fun bindUI() = launch(Dispatchers.Main) {
        val manufacturers = viewModel.manufacturers.await()
        initManufacturerSpinner(manufacturers.value)
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
        selectedManufacturerPosition?.let { manufacturer_spinner.setSelection(it) }
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
        selectedTypePosition?.let { type_spinner.setSelection(it) }
    }

    private fun loadYears(manufacturer: String?, type: String?) = launch(Dispatchers.Main) {
        val builtYears = viewModel.getYearsAsync(manufacturer, type).await()
        builtYears.observe(this@MainActivity, Observer {
            initBuiltYeatsSpinner(it)
        })
    }

    private fun initBuiltYeatsSpinner(builtYears: Map<String, String>?) {
        year_spinner.adapter = CustomSpinnerAdapter(this, builtYears)
        selectedYearPosition?.let { year_spinner.setSelection(it) }
        clearSavedData()
    }

    private fun clearSavedData() {
        selectedManufacturerPosition = null
        selectedTypePosition = null
        selectedYearPosition = null
    }


}

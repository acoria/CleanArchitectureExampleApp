package com.acoria.cleanarchtictureexampleapp.nature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.acoria.cleanarchtictureexampleapp.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class NatureActivity : AppCompatActivity() {

    lateinit var viewModel: NatureViewModel

    private val spinner: CircularProgressDrawable by lazy {
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        circularProgressDrawable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = createViewModel()

        viewModel.viewState.observe(this, Observer { render(it) })
        viewModel.viewEffects.observe(this, Observer { onEffect(it) })

        btn_search.setOnClickListener { viewModel.onEvent(NatureViewEvent.SearchPlantEvent(txt_search.text.toString())) }
        btn_add_favorite.setOnClickListener { viewModel.onEvent(NatureViewEvent.AddPlantToFavoritesEvent) }

    }

    private fun onEffect(viewEffect: NatureViewEffect) {
        if(viewEffect == null) return

        Timber.d("##viewEffect: $viewEffect")
        when(viewEffect){
            is NatureViewEffect.AddedToFavoritesEffect -> {
                Toast.makeText(this, "added to favorites", Toast.LENGTH_SHORT).show()
            }
            is NatureViewEffect.ShowSnackbar -> {
                //TODO: show snackbar
            }
            is NatureViewEffect.ShowToast -> {
                Toast.makeText(this, viewEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun render(viewState: NatureViewState) {
        if(viewState == null) return

        Timber.d("##viewState: $viewState")

        //TODO: maybe better not provide a viewState?
        if(viewState.searchedPlantName != "") {
            txt_search_result.text =
                "${viewState.searchedPlantName} \n \n Maximum height: ${viewState.searchedPlantMaxHeight}"

            //TODO: how to distinguish between drawable and url?
            Glide.with(this)
                .load(getDrawable(viewState.searchedImage!!.toInt()))
                .placeholder(spinner)
                .into(img_search_result)
        }
    }

    private fun createViewModel(): NatureViewModel {
        //TODO: use a factory
        return NatureViewModel(PlantRepository(mapOf(PLANT_NAME_SUNFLOWER to R.drawable.sunflower, PLANT_NAME_PALMTREE to R.drawable.palmtree)))
    }
}

package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.ViewModelFactory
import com.acoria.cleanarchtictureexampleapp.nature.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class BuildNatureActivity : AppCompatActivity() {

//    var viewModel by viewModels<BuildNatureViewModel>
    private var viewModel = ViewModelFactory(ServiceLocator.createPlantRepository()).create(BuildNatureViewModel::class.java)

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


        viewModel.viewState.observe(this, Observer { render(it) })
        viewModel.viewEffects.observe(this, Observer { onEffect(it) })

        btn_search.setOnClickListener {
            viewModel.onEvent(
                NatureViewEvent.SearchPlantEvent(
                    txt_search.text.toString()
                )
            )
        }
        btn_add_favorite.setOnClickListener { viewModel.onEvent(NatureViewEvent.AddPlantToFavoritesEvent) }

    }

    private fun onEffect(viewEffect: NatureViewEffect) {
        Timber.d("##viewEffect: $viewEffect")

        when (viewEffect) {
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
        Timber.d("##viewState: $viewState")

        txt_search_result.text = if (viewState.searchedPlantName != "") {
            "${viewState.searchedPlantName} \n \n Maximum height: ${viewState.searchedPlantMaxHeight}m"
        } else {
            "No result found.."
        }

        viewState.searchedImage
            .takeIf { it.isNotBlank() }
            ?.let {
                //TODO: how to distinguish between drawable and url?
                Glide.with(this)
                    .load(getDrawable(viewState.searchedImage.toInt()))
//                    .placeholder(spinner) shows the placeholder when the image is removed
                    .into(img_search_result)
            } ?: Glide.with(this).clear(img_search_result)
    }
}

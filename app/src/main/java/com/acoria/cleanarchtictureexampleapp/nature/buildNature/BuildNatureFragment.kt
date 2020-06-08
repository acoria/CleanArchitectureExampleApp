package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.getViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_build_nature.*
import timber.log.Timber

class BuildNatureFragment : Fragment() {

    private val viewModel by viewModels<BuildNatureViewModel> { getViewModelFactory() }

    private val spinner: CircularProgressDrawable by lazy {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        circularProgressDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_build_nature, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer { render(it) })
        viewModel.viewEffects.observe(viewLifecycleOwner, Observer { onEffect(it) })

        btn_search.setOnClickListener {
            triggerSearchEvent(txt_search.text)
        }
        btn_add_favorite.setOnClickListener { viewModel.onEvent(NatureViewEvent.AddPlantToFavoritesEvent) }

        txt_search.setOnEditorActionListener(TextView.OnEditorActionListener { searchTextView, _, event ->
            var eventHandled = false
            if(event.keyCode == KeyEvent.KEYCODE_ENTER){
                triggerSearchEvent(searchTextView.text)
                eventHandled = true
            }
            eventHandled
        })
    }


    private fun triggerSearchEvent(searchText: CharSequence) {
        viewModel.onEvent(
            NatureViewEvent.SearchPlantEvent(
                searchText.toString()
            )
        )
    }

    private fun onEffect(viewEffect: NatureViewEffect) {
        Timber.d("##viewEffect: $viewEffect")

        when (viewEffect) {
            is NatureViewEffect.AddedToFavoritesEffect -> {
                Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_SHORT).show()
            }
            is NatureViewEffect.ShowSnackbar -> {
                //TODO: show snackbar
            }
            is NatureViewEffect.ShowToast -> {
                Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
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
                    .load(getDrawable(requireContext(), viewState.searchedImage.toInt()))
//                    .placeholder(spinner) shows the placeholder when the image is removed
                    .into(img_search_result)
            } ?: Glide.with(this).clear(img_search_result)
    }
}
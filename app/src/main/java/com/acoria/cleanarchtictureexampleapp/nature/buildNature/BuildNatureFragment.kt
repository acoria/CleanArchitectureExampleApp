package com.acoria.cleanarchtictureexampleapp.nature.buildNature

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acoria.cleanarchtictureexampleapp.R
import com.acoria.cleanarchtictureexampleapp.getViewModelFactory
import com.acoria.cleanarchtictureexampleapp.littleHelper.growShrink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_build_nature.*
import timber.log.Timber

class BuildNatureFragment : Fragment() {

    private lateinit var favoriteRecyclerViewAdapter: FavoritesAdapter
    private val viewModel by viewModels<BuildNatureViewModel> { getViewModelFactory() }
    private lateinit var viewLinearLayoutManager: RecyclerView.LayoutManager

//    private val spinner: CircularProgressDrawable by lazy {
//        val circularProgressDrawable = CircularProgressDrawable(requireContext())
//        circularProgressDrawable.strokeWidth = 5f
//        circularProgressDrawable.centerRadius = 30f
//        circularProgressDrawable.start()
//        circularProgressDrawable
//    }

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
        btn_add_favorite.setOnClickListener {
            img_search_result.growShrink()
            viewModel.onEvent(NatureStateFlow.Event.AddPlantToFavoritesEvent)
        }

        setTextSearchActionListener()

        viewLinearLayoutManager = LinearLayoutManager(this.context)
        favoriteRecyclerViewAdapter =
            FavoritesAdapter { viewModel.onEvent(NatureStateFlow.Event.DeletePlantFromFavoritesEvent(it)) }
        recyclerview_favorites.apply {
            layoutManager = viewLinearLayoutManager
            adapter = favoriteRecyclerViewAdapter
        }
    }

    private fun setTextSearchActionListener() {
        txt_search.setOnEditorActionListener(TextView.OnEditorActionListener { searchTextView, _, event ->
            var eventHandled = false
            if (event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == ACTION_DOWN) {
                triggerSearchEvent(searchTextView.text)
                eventHandled = true
            }
            eventHandled
        })
    }

    private fun removeTextSearchActionListener() {
        txt_search.setOnEditorActionListener(null)
    }


    private fun triggerSearchEvent(searchText: CharSequence) {
        viewModel.onEvent(
            NatureStateFlow.Event.SearchPlantEvent(
                searchText.toString()
            )
        )
    }

    private fun onEffect(viewEffect: NatureStateFlow.Effect) {
        Timber.d("##viewEffect: $viewEffect")

        when (viewEffect) {
            is NatureStateFlow.Effect.AddedToFavoritesEffect -> {
                Toast.makeText(requireContext(), "added to favorites", Toast.LENGTH_SHORT).show()
            }
            is NatureStateFlow.Effect.ShowSnackbar -> {
                //TODO: show snackbar
            }
            is NatureStateFlow.Effect.DeletedFromFavoritesEffect -> {
                Toast.makeText(
                    requireContext(),
                    "${viewEffect.plant.name} deleted",
                    Toast.LENGTH_LONG
                ).show()
            }
            is NatureStateFlow.Effect.ShowToast -> {
                Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun render(viewState: NatureStateFlow.ViewState) {
        Timber.d("##viewState: $viewState")

        txt_search_result.text = if (viewState.searchedPlantName != "") {
            "${viewState.searchedPlantName} \n \n Maximum height: ${viewState.searchedPlantMaxHeight}m"
        } else {
            "No result found.."
        }

//        (txt_search as EditText).setText(viewState.searchBoxText)

        txt_plant_request_counter.text = viewState.userCounter

        //TODO: how to distinguish between drawable and url? & dont refresh for every viewState
        viewState.searchedImage
            .takeIf { it.isNotBlank() }
            ?.let {
                Glide.with(this)
                    .load(getDrawable(requireContext(), viewState.searchedImage.toInt()))
//                    .placeholder(spinner) shows the placeholder when the image is removed
                    .into(img_search_result)
            } ?: Glide.with(this).clear(img_search_result)

        favoriteRecyclerViewAdapter.submitList(viewState.favoritesAdapterList.toList())
    }
}
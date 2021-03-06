package com.android.educo.views.main.ui.catalogue.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.educo.R
import com.android.educo.utils.Constants
import com.android.educo.utils.Resource
import com.android.educo.utils.hide
import com.android.educo.utils.show
import com.android.educo.views.BaseFragment
import com.android.educo.views.main.ui.catalogue.adapter.CatalogueDocsClickListener
import com.android.educo.views.main.ui.catalogue.adapter.CatalogueDocsRecyclerAdapter
import com.android.educo.views.main.ui.catalogue.tabs.viewModels.CatalogueViewModel
import kotlinx.android.synthetic.main.fragment_catalogue_audio.*
import kotlinx.android.synthetic.main.fragment_catalogue_docs.*


class CatalogueAudioFragment : BaseFragment() {

    private val viewModel : CatalogueViewModel by viewModels()
    private lateinit var adapter: CatalogueDocsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CatalogueDocsRecyclerAdapter(requireContext(), CatalogueDocsClickListener {

        })
        audio_recycler_view.adapter = adapter
        viewModel.getCatalogue(Constants.COLLECTION_AUDIO)
        viewModel.catalogue.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    catalogueAudioProgress.show()
                }
                is Resource.Success -> {
                    val data = it.data
                    showToast(it.message)
                    adapter.submitList(data)
                    catalogueAudioProgress.hide()
                }
                is Resource.Failure -> {
                    catalogueAudioProgress.hide()
                }
            }
        })
    }

}
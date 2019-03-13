package online.z0lk1n.android.handnotes.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.ui.note.NoteFragment
import online.z0lk1n.android.handnotes.util.ScreenConfiguration

class MainFragment : Fragment() {

    lateinit var adapter: NotesRVAdapter
    lateinit var viewModel: MainViewModel

    private val navController by lazy {
        NavHostFragment.findNavController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = NotesRVAdapter {
            navController.navigate(R.id.toNoteFragment, NoteFragment.createBundle(it))
        }

        rv_notes.layoutManager = GridLayoutManager(
            this.requireContext(),
            ScreenConfiguration(this.requireContext()).calculateNumberOfColumns()
        )
        rv_notes.itemAnimator = DefaultItemAnimator()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer<MainViewState> { state ->
            state?.let {
                adapter.notes = it.notes
            }
        })

        activity?.let {
            it as ToolbarTuning

            it.setHomeVisibility(false)
            it.setToolbarTitle(getString(R.string.app_name))
            it.setToolbarColor(R.color.colorPrimary)
        }
    }

    override fun onResume() {
        super.onResume()

        activity?.let {
            it.fab.show()
            it.fab.setOnClickListener {
                navController.navigate(R.id.toNoteFragment)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        activity?.let {
            it.fab.setOnClickListener(null)
            it.fab.hide()
        }
    }
}
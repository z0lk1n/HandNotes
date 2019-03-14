package online.z0lk1n.android.handnotes.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.util.ScreenConfiguration

class MainFragment : BaseFragment<List<Note>?, MainViewState>() {

    lateinit var adapter: NotesRVAdapter
    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

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
        adapter = NotesRVAdapter {
            val noteBundle = Bundle()
            noteBundle.putString(getString(R.string.note_id), it.id)
            navController.navigate(R.id.toNoteFragment, noteBundle)
        }

        rv_notes.layoutManager = GridLayoutManager(
            this.requireContext(),
            ScreenConfiguration(this.requireContext()).calculateNumberOfColumns()
        )
        rv_notes.itemAnimator = DefaultItemAnimator()
        rv_notes.adapter = adapter

        activity?.let {
            it as ToolbarTuning

            it.setHomeVisibility(false)
            it.setToolbarTitle(getString(R.string.app_name))
            it.setToolbarColor(R.color.colorPrimary)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let { adapter.notes = it }
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
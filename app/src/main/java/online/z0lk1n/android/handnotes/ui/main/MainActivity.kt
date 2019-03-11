package online.z0lk1n.android.handnotes.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.ui.note.NoteActivity
import online.z0lk1n.android.handnotes.util.ScreenConfiguration

class MainActivity : AppCompatActivity() {

    private val adapter = NotesRVAdapter {
        NoteActivity.start(this, it)
    }
    private val screenConf = ScreenConfiguration(this)
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, screenConf.calculateNumberOfColumns())
        rv_notes.itemAnimator = DefaultItemAnimator()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer<MainViewState> { state ->
            state?.let {
                adapter.notes = it.notes
            }
        })

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }
}

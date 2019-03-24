package online.z0lk1n.android.handnotes.ui.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.toolbar.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.common.getColorResId
import online.z0lk1n.android.handnotes.common.onChange
import online.z0lk1n.android.handnotes.common.toStringFormat
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class NoteFragment : BaseFragment<Note?, NoteViewState>() {

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }

    private var note: Note? = null
    override val model: NoteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val noteId = arguments?.getString(getString(R.string.note_id))

        activity?.let {
            it as MainActivity

            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            if (noteId == null) {
                it.supportActionBar?.title = getString(R.string.new_note_title)
            } else {
                model.loadNote(noteId)
            }
        }

        et_title.onChange(SAVE_DELAY) { saveNote() }
        et_body.onChange(SAVE_DELAY) { saveNote() }
    }

    override fun renderData(data: Note?) {
        note = data
        initView()
    }

    private fun initView() {
        note?.run {
            (activity as MainActivity).run {
                supportActionBar?.title = lastChanged.toStringFormat(DATE_FORMAT)
                toolbar.setBackgroundColor(color.getColorResId(this))
            }

            et_title.setText(title)
            et_body.setText(text)
        }
    }

    private fun saveNote() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString()
        )

        note?.let { model.save(note!!) }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    private fun hideKeyboard() {
        activity?.run {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
        }
    }
}
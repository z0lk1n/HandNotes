package online.z0lk1n.android.handnotes.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_note.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.common.getColorResId
import online.z0lk1n.android.handnotes.common.onChange
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.ui.main.ToolbarTuning
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : BaseFragment<Note?, NoteViewState>() {

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }

    private var note: Note? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
    }

    private fun init() {
        val noteId = arguments?.getString(getString(R.string.note_id))

        activity?.let {
            it as ToolbarTuning

            it.setHomeVisibility(true)

            noteId?.let { id ->
                viewModel.loadNote(id)
            } ?: it.setToolbarTitle(getString(R.string.new_note_title))
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
            activity?.let {
                it as ToolbarTuning

                val title = note?.let { n ->
                    SimpleDateFormat(NoteFragment.DATE_FORMAT, Locale.getDefault())
                        .format(n.lastChanged)
                } ?: getString(R.string.new_note_title)

                it.setToolbarTitle(title)
                it.setToolbarColor(color.getColorResId(it))
            }

            et_title.setText(title)
            et_body.setText(text)
        }
    }

    private fun saveNote() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 2) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString()
        )

        note?.let { viewModel.save(note!!) }
    }
}

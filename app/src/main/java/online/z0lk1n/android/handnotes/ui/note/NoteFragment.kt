package online.z0lk1n.android.handnotes.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_note.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.ui.main.ToolbarTuning
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment : BaseFragment<Note?, NoteViewState>() {

    private var note: Note? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
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

        et_title.onChange()
        et_body.onChange()
    }

    override fun renderData(data: Note?) {
        note = data

        activity?.let {
            it as ToolbarTuning

            val title = note?.let { n ->
                SimpleDateFormat(NoteFragment.DATE_FORMAT, Locale.getDefault()).format(n.lastChanged)
            } ?: getString(R.string.new_note_title)

            it.setToolbarTitle(title)
        }

        initView()
    }

    private fun initView() {
        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)
            val background = when (it.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }
            activity?.let { t ->
                t as ToolbarTuning
                t.setToolbarColor(background)
            }
        }
    }

    private fun EditText.onChange() {
        this.addTextChangedListener(object : TextWatcher {
            private var timer = Timer()

            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        saveNote()
                    }
                }, SAVE_DELAY)

                saveNote()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
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

        note?.let { viewModel.save(note!!) }
    }
}

package online.z0lk1n.android.handnotes.ui.note

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.toolbar.*
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.common.getColorResId
import online.z0lk1n.android.handnotes.common.toStringFormat
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class NoteFragment : BaseFragment<NoteViewState.Data, NoteViewState>() {

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }

    override val model: NoteViewModel by viewModel()
    private var note: Note? = null
    private var color = Note.Color.WHITE
    private val navController by lazy {
        NavHostFragment.findNavController(this)
    }

    private val textChangeWatcher = object : TextWatcher {
        private var timer = Timer()

        override fun afterTextChanged(s: Editable?) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        saveNote()
                    }
                }
            }, SAVE_DELAY)

            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
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
    }

    private fun init() {
        setHasOptionsMenu(true)
        val noteId = arguments?.getString(getString(R.string.note_id))

        activity?.let {
            it as MainActivity

            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            if (noteId == null) {
                it.supportActionBar?.title = getString(R.string.new_note_title)
                setEditListener()
            } else {
                model.loadNote(noteId)
            }
        }

        color_picker.onColorClickListener = {
            color = it
            setToolbarColor(it)
            saveNote()
        }
    }

    private fun setToolbarColor(color: Note.Color) {
        (activity as MainActivity).run {
            toolbar.setBackgroundColor(color.getColorResId(this))
            color_picker.setBackgroundColor(color.getColorResId(this))
        }
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) navController.navigate(R.id.toMainFragment)

        this.note = data.note
        data.note?.let { color = it.color }
        initView()
    }

    private fun initView() {
        note?.run {
            (activity as MainActivity).run {
                supportActionBar?.title = lastChanged.toStringFormat(DATE_FORMAT)
                toolbar.setBackgroundColor(color.getColorResId(this))
            }
            removeEditListener()
            et_title.setText(title)
            et_body.setText(text)
            setEditListener()
        }
    }

    private fun saveNote() {
        if (et_title.text.isNullOrBlank() || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString()
        )

        note?.let { model.save(note!!) }
    }

    override fun onPause() {
        if (color_picker.isOpen) {
            color_picker.close()
        }
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.palette -> togglePalette().let { true }
            R.id.delete -> deleteNote().let { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun togglePalette() {
        if (color_picker.isOpen) {
            color_picker.close()
        } else {
            color_picker.open()
        }
    }

    //todo 25.03.19 add dialog
    private fun deleteNote() {
        model.deleteNote()
    }

    private fun setEditListener() {
        et_title.addTextChangedListener(textChangeWatcher)
        et_body.addTextChangedListener(textChangeWatcher)
    }

    private fun removeEditListener() {
        et_title.removeTextChangedListener(textChangeWatcher)
        et_body.removeTextChangedListener(textChangeWatcher)
    }
}

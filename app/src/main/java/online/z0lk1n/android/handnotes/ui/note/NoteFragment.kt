package online.z0lk1n.android.handnotes.ui.note

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.common.getColorResId
import online.z0lk1n.android.handnotes.common.toStringFormat
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class NoteFragment : BaseFragment<NoteData>() {

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
    }

    override val model: NoteViewModel by viewModel()
    private var note: Note? = null
    private var color = Note.Color.WHITE
    private var isNoteDeleted: Boolean = false
    private var snackbar: Snackbar? = null
    private val navController by lazy {
        NavHostFragment.findNavController(this)
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
            } else {
                showLoader()
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

    override fun renderData(data: NoteData) {
        if (data.isDeleted) {
            isNoteDeleted = true
            navController.navigate(R.id.toMainFragment)
        }

        this.note = data.note
        data.note?.let { color = it.color }
        initView()
    }

    private fun initView() {
        note?.run {
            hideLoader()

            (activity as MainActivity).run {
                supportActionBar?.title = lastChanged.toStringFormat(DATE_FORMAT)
                toolbar.setBackgroundColor(color.getColorResId(this))
            }

            et_title.setText(title)
            et_body.setText(text)

            et_title.text?.let { et_title.setSelection(it.length) }
            et_body.text?.let { et_body.setSelection(it.length) }
        }
    }

    private fun saveNote() {
        if (isNoteDeleted ||
            (et_title.text.isNullOrBlank()
                    && et_body.text.isNullOrBlank())
        ) return

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
        saveNote()
        hideKeyboard()
        closeSnackbar()

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

    private fun deleteNote() {
        snackbar = Snackbar.make(
            ll_note,
            R.string.note_text_delete,
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(R.string.note_btn_delete) { model.deleteNote() }
        }
        snackbar?.show()
    }

    private fun closeSnackbar() {
        snackbar?.dismiss()
    }

    private fun showLoader() {
        pb_note.visibility = View.VISIBLE
        til_title.visibility = View.GONE
        et_body.visibility = View.GONE
    }

    private fun hideLoader() {
        pb_note.visibility = View.GONE
        til_title.visibility = View.VISIBLE
        et_body.visibility = View.VISIBLE
    }
}

package online.z0lk1n.android.handnotes.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.*
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import online.z0lk1n.android.handnotes.R
import online.z0lk1n.android.handnotes.data.entity.Note
import online.z0lk1n.android.handnotes.ui.base.BaseFragment
import online.z0lk1n.android.handnotes.util.ScreenConfiguration
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainFragment : BaseFragment<List<Note>?>(),
    LogoutDialog.LogoutListener {

    private lateinit var adapter: NotesRVAdapter
    override val model: MainViewModel by viewModel()

    private val navController by lazy {
        NavHostFragment.findNavController(this)
    }

    private val screenConfiguration: ScreenConfiguration by inject()

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
        setHasOptionsMenu(true)
        (activity as MainActivity).setSupportActionBar(toolbar)

        showLoader()

        adapter = NotesRVAdapter {
            val noteBundle = Bundle()
            noteBundle.putString(getString(R.string.note_id), it.id)
            navController.navigate(R.id.toNoteFragment, noteBundle)
        }

        rv_notes.layoutManager = GridLayoutManager(
            this.requireContext(),
            screenConfiguration.calculateNumberOfColumns()
        )
        rv_notes.itemAnimator = DefaultItemAnimator()
        rv_notes.adapter = adapter

        fab.setOnClickListener {
            navController.navigate(R.id.toNoteFragment)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            hideLoader()
            adapter.notes = it
        }
    }

    override fun onLogout() {
        activity?.run {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    navController.popBackStack()
                    recreate()
                }
        }
    }

    private fun showLogoutDialog() {
        fragmentManager?.let {
            it.findFragmentByTag(LogoutDialog.TAG)
                ?: LogoutDialog.createInstance().show(it, LogoutDialog.TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> super.onOptionsItemSelected(item)
        }

    @SuppressLint("RestrictedApi")
    private fun showLoader() {
        pb_main.visibility = View.VISIBLE
        rv_notes.visibility = View.GONE
        fab.visibility = View.GONE
    }

    @SuppressLint("RestrictedApi")
    private fun hideLoader() {
        pb_main.visibility = View.GONE
        rv_notes.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }
}
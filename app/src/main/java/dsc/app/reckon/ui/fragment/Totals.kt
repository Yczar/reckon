package dsc.app.reckon.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration

import dsc.app.reckon.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Totals : Fragment() {

    lateinit var decoration: DividerItemDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        /*decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)*/

        return inflater.inflate(R.layout.fragment_totals, container, false)
    }
}
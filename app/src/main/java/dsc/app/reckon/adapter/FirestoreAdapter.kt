package dsc.app.reckon.adapter

import android.content.ContentValues.TAG
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(private var query: Query?) :
        RecyclerView.Adapter<VH>(),
        EventListener<QuerySnapshot> {

    private var registration : ListenerRegistration? = null
    private val snapshots  = ArrayList<DocumentSnapshot>()

    override fun onEvent(docSnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null){
            Log.w(TAG, "onEvent:error", e)
            onError(e)
            return
        }

        if (docSnapshot == null) return

        //Dispatch the event
        Log.d(TAG, "onEvent:numChanges: ${docSnapshot.documentChanges.size}")
        for (change in docSnapshot.documentChanges){
            when(change.type){
                DocumentChange.Type.ADDED -> onDocumentAdded(change)
                DocumentChange.Type.MODIFIED -> onDocumentModified(change)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
            }
        }
        onDataChanged()
    }

    fun startListening(){
        if(query != null && registration == null){
            registration = query!!.addSnapshotListener(this)
        }
    }

    fun stopListening(){
        registration?.remove()
        registration = null

        snapshots.clear()
        notifyDataSetChanged()
    }

    fun setQuery(query : Query){
        stopListening() //Stop listening

        //Clear existing data
        snapshots.clear()
        notifyDataSetChanged()

        //Listen to new query
        this.query = query
        startListening()
    }

    open fun onError(e: FirebaseFirestoreException){
        Log.w(TAG, "onError", e)
    }
    open fun onDataChanged(){}

    override fun getItemCount(): Int {
        return snapshots.size
    }

    protected fun getSnapshot(index : Int): DocumentSnapshot{
        return snapshots[index]
    }

    private fun onDocumentAdded(change: DocumentChange){
        snapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    private fun  onDocumentModified(change : DocumentChange){
        if(change.oldIndex == change.newIndex){
            //Item changed but remained in same position
            snapshots[change.oldIndex] = change.document
            notifyItemChanged(change.oldIndex)
        }else{
            //Item changes and changed position
            snapshots.removeAt(change.oldIndex)
            snapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    private fun onDocumentRemoved(change: DocumentChange){
        snapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }
}
package com.example.drinkables.utils.customAdapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

const val FIRST_VIEW_TYPE = 0
const val MISSING_REQUIRED_TYPE_EXCEPTION = "Can't find required type"

class CompositeDelegateAdapter<T> private constructor(
    itemCallback: DiffUtil.ItemCallback<T>,
    private val delegateAdapters: SparseArray<IDelegateAdapter<ViewHolder, T>>,
) :
    ListAdapter<T, ViewHolder>(itemCallback) {

    override fun getItemViewType(position: Int): Int {
        for (i in FIRST_VIEW_TYPE until delegateAdapters.size()) {
            if (delegateAdapters.valueAt(i).isForViewType(currentList, position)) {
                return delegateAdapters.keyAt(i)
            }
        }
        throw NullPointerException(MISSING_REQUIRED_TYPE_EXCEPTION)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))
            ?.onBindViewHolder(holder, currentList, position)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        delegateAdapters.get(holder.itemViewType)
            .onRecycled(holder)
    }

    class Builder<T>(private val itemCallback: DiffUtil.ItemCallback<T>) {
        private val delegateAdapters: SparseArray<IDelegateAdapter<ViewHolder, T>> = SparseArray()

        private var count = 0

        public fun addAdapter(delegateAdapter: IDelegateAdapter<ViewHolder, T>): Builder<T> {
            delegateAdapters.put(count++, delegateAdapter)
            return this
        }

        fun build(): CompositeDelegateAdapter<T> =
            CompositeDelegateAdapter(itemCallback, delegateAdapters)
    }
}
package com.atiurin.espressopageobject.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.ArrayList

internal fun <T : VH, VH : RecyclerView.ViewHolder> itemsMatching(
    recyclerView: RecyclerView, viewHolderMatcher: Matcher<VH>, max: Int
): List<MatchedItem> {
    val adapter = recyclerView.adapter
    val viewHolderCache = SparseArray<VH>()
    val matchedItems = ArrayList<MatchedItem>()
    if (adapter == null) return matchedItems
    for (position in 0 until adapter.itemCount) {
        val itemType = adapter.getItemViewType(position)
        var cachedViewHolder: VH? = viewHolderCache.get(itemType)
        // Create a view holder per type if not exists
        if (cachedViewHolder == null) {
            cachedViewHolder = adapter.createViewHolder(recyclerView, itemType) as VH?
            viewHolderCache.put(itemType, cachedViewHolder)
        }
        // Bind data to ViewHolder and apply matcher to view descendants.
        adapter.bindViewHolder((cachedViewHolder as T?)!!, position)
        if (viewHolderMatcher.matches(cachedViewHolder)) {
            matchedItems.add(
                MatchedItem(
                    position,
                    HumanReadables.getViewHierarchyErrorMessage(
                        cachedViewHolder!!.itemView, null,
                        "\n\n*** Matched ViewHolder item at position: $position ***", null
                    )
                )
            )
            if (matchedItems.size == max) {
                break
            }
        }
    }
    return matchedItems
}

/**
 * Wrapper for matched items in recycler view which contains position and description of matched
 * view.
 */
internal class MatchedItem(val position: Int, val description: String) {

    override fun toString(): String {
        return description
    }
}

/**
 * Creates matcher for view holder with given item view matcher.
 *
 * @param itemViewMatcher a item view matcher which is used to match item.
 * @return a matcher which matches a view holder containing item matching itemViewMatcher.
 */
internal fun <VH : RecyclerView.ViewHolder> viewHolderMatcher(
    itemViewMatcher: Matcher<View>
): Matcher<VH> {
    return object : TypeSafeMatcher<VH>() {
        override fun matchesSafely(item: VH): Boolean {
            return itemViewMatcher.matches(item.itemView)
        }

        override fun describeTo(description: Description) {
            description.appendText("holder with view: ")
            itemViewMatcher.describeTo(description)
        }
    }
}

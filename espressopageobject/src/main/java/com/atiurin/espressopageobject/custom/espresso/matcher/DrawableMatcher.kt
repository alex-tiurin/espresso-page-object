package com.atiurin.espressopageobject.custom.espresso.matcher

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class DrawableMatcher(private val expectedId: Int = 0) : TypeSafeMatcher<View>() {
    var resourceName: String? = null

    override fun matchesSafely(targetView: View): Boolean {
        if (targetView !is ImageView) {
            return false
        }
        val imageView: ImageView = targetView as ImageView
        if (expectedId < 0) {
            return imageView.getDrawable() != null
        }
        val resources: Resources = targetView.context.resources
        val expectedDrawable: Drawable = resources.getDrawable(expectedId)
        resourceName = resources.getResourceEntryName(expectedId)
        if (expectedDrawable == null) {
            return false
        }
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val expectedBitmap = (expectedDrawable as BitmapDrawable).bitmap
        return bitmap.sameAs(expectedBitmap)
    }


    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}

/**
 * Matches view bitmap with resource object
 * @param resourceId the object id against which the matcher is evaluated
 * @return `true` if bitmaps are the same, otherwise `false`.
 */
fun withDrawable(resourceId: Int): Matcher<View> {
    return DrawableMatcher(resourceId)
}

/**
 * Matches view has any drawable or not
 * @return `true` if view has any drawable, otherwise `false`.
 */
fun hasDrawable(): Matcher<View> {
    return DrawableMatcher(-1)
}
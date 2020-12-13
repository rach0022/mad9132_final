package ravi.anatolie.finalproject
// created by Ravi and Anatolie on December 11th, 2020
import android.text.InputFilter
import android.text.Spanned

// this will make sure that user input in the edit fields are filtered to a proper structure
class InputFilterMinMax(min: Int, max: Int) : InputFilter {
    // region Properties of InputFilterMinMax Class
    private var min = 0
    private var max = 0
    // endregion

    // region Methods
    // region Init Method
    init {
        this.min = min
        this.max = max
    }
    // endregion


    override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            destination: Spanned,
            destinationStart: Int,
            destinationEnd: Int
    ): CharSequence? {
        try {
            val input = (destination.subSequence(0, destinationStart).toString() + source +
                    destination.subSequence(destinationEnd, destination.length)).toInt()
            if (isInRange(min, max, input)) {
                return null
            }
        } catch (ex: NumberFormatException) {
            // just ignore the exception
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }

    // endregion
}
package ru.sadv1r.vk.parser

/**
 * Функция для деления [последовательности][Sequence] на части нужного размера
 *
 * В Kotlin [планируется добавление](https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/window-sliding.md)
 * данной функциональности [в новых версиях](https://github.com/JetBrains/kotlin/compare/rr/cy/window-sliding)
 *
 * Created on 25/09/2016.
 *
 * @author sadv1r
 *
 * @version 1.0
 */

fun <T> Sequence<T>.batch(n: Int): Sequence<List<T>> {
    return BatchingSequence(this, n)
}

private class BatchingSequence<out T>(val source: Sequence<T>, val batchSize: Int) : Sequence<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        val iterate = if (batchSize > 0) source.iterator() else emptyList<T>().iterator()
        override fun computeNext() {
            if (iterate.hasNext()) setNext(iterate.asSequence().take(batchSize).toList())
            else done()
        }
    }
}
package ru.sadv1r.vk.api.core

/**
 * Функция для деления [последовательности][kotlin.sequences.Sequence] Sequence на части нужного размера.
 *
 * В Kotlin [планируется](https://github.com/JetBrains/kotlin/compare/rr/cy/window-sliding) добавление
 * данной функциональности.
 * [Предложение](https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/window-sliding.md) о добавлении.
 *
 * @author [sadv1r](http://sadv1r.ru)
 */

fun <T> Sequence<T>.batch(n: Int): Sequence<List<T>> {
    return ru.sadv1r.vk.api.core.BatchingSequence(this, n)
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
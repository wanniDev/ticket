package io.ticketaka.api.common.infrastructure.tsid

import com.github.f4b6a3.tsid.TsidFactory
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.IntSupplier

class TsidFactoryConcurrencyTest {
    private fun newFactory(nodeBits: Int): TsidFactory {
        return TsidFactory.builder().withRandomFunction(
            IntSupplier {
                ThreadLocalRandom.current().nextInt()
            },
        )
            .withNodeBits(nodeBits)
            .build()
    }

    @Test
    fun `sharing one tsidFactory instance will not occur collision`() {
        // given
        val nodeBits = 8
        val threadCount = 16
        val iterationCount = 100000

        val clashes = AtomicInteger()
        val endLatch = CountDownLatch(threadCount)
        val tsidMap: ConcurrentMap<Long, Int> = ConcurrentHashMap()

        val factory: TsidFactory = newFactory(nodeBits)

        // when
        for (i in 0 until threadCount) {
            val threadId = i

            Thread {
                for (j in 0 until iterationCount) {
                    val tsid = factory.create().toLong()
                    if (Objects.nonNull(tsidMap.put(tsid, (threadId * iterationCount) + j))) {
                        clashes.incrementAndGet()
                        break
                    }
                }
                endLatch.countDown()
            }.start()
        }
        endLatch.await()

        // then
        val isThreadSafe = clashes.get() == 0
        assertTrue(isThreadSafe)
    }
}

package io.ticketaka.api.common.infrastructure.tsid

import com.github.f4b6a3.tsid.TsidFactory
import kotlin.random.Random
import kotlin.random.asJavaRandom

// 다수의 스레드에서 tsid를 생성하더라도 중복되지 않는지 확인하는 테스트
// 동시성 테스트로서 시간이 오래걸릴 수 있으므로 main 함수에서 실행하도록 구현
class TsidUniquenessTest(
    val threadCount: Int,
    val requestCount: Int,
    val hashSet: HashSet<Long>,
    val verbose: Boolean
) {
    fun start() {
        val threads = arrayOfNulls<Thread>(this.threadCount)

        for (i in 0 until this.threadCount) {
            threads[i] = Thread(UniquenessTestThread(i, requestCount, hashSet, verbose))
            threads[i]!!.start()
        }

        for (thread in threads) {
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}

class UniquenessTestThread(private val id: Int, private val requestCount: Int, private val hashSet: HashSet<Long>, private val verbose: Boolean) : Runnable {
    private val factory: TsidFactory = TsidFactory.builder().withNode(id).withRandom(Random.asJavaRandom()).build()

    override fun run() {
        var progress = 0
        val max: Int = requestCount

        for (i in 0 until max) {
            // tsid 생성
            val tsid = factory.create().toLong()

            if (verbose && (i % (max / 100) == 0)) {
                // 스레드별로 작업 진척도를 보여주는 코드
                progress = ((i * 1.0 / max) * 100).toInt()
                println(String.format("[Thread %06d] %s %s %s%%", id, tsid, i, progress))
            }
            synchronized(hashSet) {
                // 중복 체크
                if (!hashSet.add(tsid)) {
                    System.err.println(
                        String.format(
                            "[Thread %06d] %s %s %s%% [DUPLICATE]",
                            id,
                            tsid,
                            i,
                            progress
                        )
                    )
                }
            }
        }

        if (verbose) {
            // 완료
            println(String.format("[Thread %06d] Done.", id))
        }
    }
}

fun main() {
    val threadCount = 1024 // 2^10 (node bit length)
    val requestCount = 4096 // 2^12 (counter bit length)
    val verbose = true

    val test = TsidUniquenessTest(threadCount, requestCount, HashSet(), verbose)
    test.start()
}
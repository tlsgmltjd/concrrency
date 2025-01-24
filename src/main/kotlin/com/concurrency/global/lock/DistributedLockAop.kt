package com.concurrency.global.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component


@Aspect
@Component
class DistributedLockAop (
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction
) {

    @Around("@annotation(com.concurrency.global.lock.DistributedLock)")
    @Throws(Throwable::class)
    fun lock(joinPoint: ProceedingJoinPoint): Any {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock: DistributedLock = method.getAnnotation(DistributedLock::class.java)

//        val key = REDISSON_LOCK_PREFIX + DistributedLockKeyGenerator.generate(
//            signature.parameterNames,
//            joinPoint.args, distributedLock.key
//        )
        val key = REDISSON_LOCK_PREFIX + distributedLock.key
        val rLock = redissonClient.getLock(key)

        println(">>>>>>>>>> Lock 획득: $key >>>>>>>>>>")

        try {
            if (!rLock.tryLock(distributedLock.waitTime, distributedLock.leaseTime, distributedLock.timeUnit)) {
                throw RuntimeException("LOCK_ACQUISITION_FAILED")
            }

            return aopForTransaction.proceed(joinPoint)

        } catch (e: InterruptedException) {
            throw RuntimeException("LOCK_INTERRUPTED")
        } finally {
            rLock.unlock()
            println("<<<<<<<<<< Lock 반환: $key <<<<<<<<<<")
        }
    }

    companion object {
        private const val REDISSON_LOCK_PREFIX = "LOCK:"
    }
}

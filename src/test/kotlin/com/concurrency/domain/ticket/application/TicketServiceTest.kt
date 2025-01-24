package com.concurrency.domain.ticket.application

import com.concurrency.domain.ticket.persistence.Ticket
import com.concurrency.domain.ticket.persistence.TicketRepository
import com.concurrency.domain.ticket.persistence.TicketUserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@SpringBootTest
class TicketServiceTest(
 @Autowired
 private val ticketService: TicketService,
 @Autowired
 private val ticketRepository: TicketRepository,
 @Autowired
 private val ticketUserRepository: TicketUserRepository,
) {

     @Test
     fun noLock_concurrencyTest() {
        val threadCount = 300
        val executorService = Executors.newFixedThreadPool(threadCount)
        val futures = mutableListOf<CompletableFuture<Void>>()

        repeat(threadCount) { index ->
         val future = CompletableFuture.runAsync({
          ticketService.issueNolock(1, 1)
           .doOnError { e -> println("Error in thread $index: ${e.message}") }
           .onErrorResume { Mono.empty() }
           .block()
         }, executorService)
         futures.add(future)
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()
        executorService.shutdown()

        val ticketCount = ticketRepository.findById(1).map(Ticket::count).block()
        val ticketUserCount = ticketUserRepository.count().block()

        // 기대 값: count = 300, userCount = 300
        // 실제 값: count = 30, userCount = 300 (동시성 문제 발생)
        println("result: count = $ticketCount, userCount = $ticketUserCount")

     }
}

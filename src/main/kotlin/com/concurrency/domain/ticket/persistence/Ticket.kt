package com.concurrency.domain.ticket.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tbl_ticket")
data class Ticket(
    @Id
    val id: Long = 0,
    val name: String,
    var count: Int,
    @Column("limit_count")
    val limitCount: Int
) {
    fun addCount() {
        if (count >= limitCount) {
            throw RuntimeException("Count has reached the limit!")
        }
        count++
    }
}

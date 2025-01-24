package com.concurrency.domain.ticket.dto

import java.time.LocalDateTime

data class TicketIssueRes(
    val ticketId: Long,
    val ticketName: String,
    val count: Int,
    val limitCount: Int,
    val date: LocalDateTime
)

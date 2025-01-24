package com.concurrency.domain.ticket.persistence

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface TicketRepository : R2dbcRepository<Ticket, Long>

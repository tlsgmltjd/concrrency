package com.concurrency.domain.ticket.persistence

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface TicketUserRepository : R2dbcRepository<TicketUser, Long>
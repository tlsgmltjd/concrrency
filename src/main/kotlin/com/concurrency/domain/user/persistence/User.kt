package com.concurrency.domain.user.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tbl_user")
class User(
    @Id
    val id: Long,

    val name: String,

    val password: String
)
package br.dev.santi.domain.pedagogic

import java.time.LocalDate
import java.time.Period

class Content (val id: Int, val name: String ){
    companion object {
        private var currentId = 0

        operator fun invoke( name: String): Content {
            return Content( ++currentId, name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
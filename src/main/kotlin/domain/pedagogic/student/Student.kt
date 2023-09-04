package domain.pedagogic.student

import java.time.LocalDate
import java.time.Period

class Student private constructor(val id: Int, val name: String, val birthDate: LocalDate){
    companion object {
        internal const val MIN_AGE = 16
        private const val UNDERAGE_EXCEPTION = "Underage student"
        private var currentId = 0

        operator fun invoke( name: String,  birthDate: LocalDate): Student {
            val age = Period.between(birthDate, LocalDate.now()).years
            if (age >= MIN_AGE){
                return Student( ++currentId, name, birthDate)
            } else {
                throw StudentUnderageException(UNDERAGE_EXCEPTION)
            }
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
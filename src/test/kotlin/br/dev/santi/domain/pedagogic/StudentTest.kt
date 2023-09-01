package br.dev.santi.domain.pedagogic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.Year

internal class StudentTest{
    companion object Constants{
        private const val NAME = "Elisha"
        //Java Language Birthday: May 23, 1995
        private const val BIRTH_DAY = 23
        private const val BIRTH_MONTH = 5
        private const val BIRTH_YEAR = 1995
        private val BIRTH_DATE = LocalDate.of(BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY)
    }

    @Test
    fun createTest() {
        assertDoesNotThrow(){
            Student(name = NAME, birthDate = BIRTH_DATE)
        }
    }
    @Test
    fun createUnderageTest() {
        val underAgeYear = Year.now().value - Student.MIN_AGE + 1
        val underAgeBirthDate = LocalDate.of(underAgeYear, BIRTH_MONTH, BIRTH_DAY)
        assertThrows<StudentUnderageException>{
            Student( name = NAME, birthDate = underAgeBirthDate)
        }
    }

}
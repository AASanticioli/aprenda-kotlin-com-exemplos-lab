package br.dev.santi.domain.pedagogic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate


class FormationTest {

    companion object Constants{
        private const val FORMATION_NAME = "Formation 1"
        private const val STUDENT_NAME = "Student"
        private val BIRTH_DATE = LocalDate.of(1979, 8, 31)
    }

    private fun makeTestStudent(studentTestNameSuffix: Int = 1): Student {
        return Student( name = "$STUDENT_NAME $studentTestNameSuffix", birthDate = BIRTH_DATE)

    }
    @Test
    fun enrolTest() {
        val student = makeTestStudent()
        val formation = Formation(FORMATION_NAME, ContentLevel.BEGINNER)
        formation.enrol(student)
        assertEquals(1, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isStudentEnrolled(student))
    }

    @Test
    fun enrolPreventDuplicatedTest() {
        val student = makeTestStudent()
        val formation = Formation(FORMATION_NAME, ContentLevel.BEGINNER)
        formation.enrol(student)
        formation.enrol(student)
        assertEquals(1, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isStudentEnrolled(student))
    }

    @Test
    fun enrolMaxCapacityTest() {
        val formation = Formation(FORMATION_NAME, ContentLevel.BEGINNER)
        var i:Int = 0
        while (i < 300){
            formation.enrol(makeTestStudent(studentTestNameSuffix=++i))
        }
        assertThrows<FormationFullException>{
            formation.enrol(makeTestStudent(studentTestNameSuffix=++i))
        }
        assertEquals(Formation.MAX_STUDENTS_CAPACITY, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isFull())
    }
}
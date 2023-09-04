package domain.pedagogic.formation

import domain.pedagogic.student.Student
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate


class FormationTest {

    companion object Constants{
        private const val FORMATION_NAME = "Formation 1"
        private const val CONTENT_ID = 1
        private const val CONTENT_NAME = "Content"
        private const val STUDENT_ID = 1
        private const val STUDENT_NAME = "Student"
        private val BIRTH_DATE = LocalDate.of(1979, 8, 31)
    }


    @Test
    fun enrolTest() {
        val student = Student( name = "$STUDENT_NAME", birthDate = BIRTH_DATE)
        val formation = Formation(FORMATION_NAME, FormationLevel.BEGINNER)
        formation.enrol(student)
        assertEquals(1, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isStudentEnrolled(student))
    }


    @Test
    fun enrolPreventDuplicatedTest() {
        val student = Student( name = "$STUDENT_NAME", birthDate = BIRTH_DATE)
        val formation = Formation(FORMATION_NAME, FormationLevel.BEGINNER)
        formation.enrol(student)
        formation.enrol(student)
        assertEquals(1, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isStudentEnrolled(student))
    }

    @Test
    fun formationStudentNotFoundExceptionTest() {
        val formation = Formation(FORMATION_NAME, FormationLevel.BEGINNER)
        assertThrows<FormationStudentNotFoundException>{
            formation.getStudentById(STUDENT_ID)
        }
    }

    @Test
    fun formationContentNotFoundExceptionTest() {
        val formation = Formation(FORMATION_NAME, FormationLevel.BEGINNER)
        assertThrows<FormationContentNotFoundException>{
            formation.getContentById(CONTENT_ID)
        }
    }


    @Test
    fun formationFullExceptionTest() {
        val formation = Formation(FORMATION_NAME, FormationLevel.BEGINNER)
        var i:Int = 0

        while (i < 300){
            formation.enrol(Student( name = "$STUDENT_NAME ${++i}", birthDate = BIRTH_DATE))
        }
        assertThrows<FormationFullException>{
            formation.enrol(Student( name = "$STUDENT_NAME ${++i}", birthDate = BIRTH_DATE))
        }
        assertEquals(Formation.MAX_STUDENTS_CAPACITY, formation.getStudentsEnrolledQuantity())
        assertTrue(formation.isFull())
    }
}
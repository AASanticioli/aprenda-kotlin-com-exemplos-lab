package application

import domain.pedagogic.formation.Formation
import domain.pedagogic.formation.FormationLevel
import domain.pedagogic.formation.FormationNotFoundException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class ChallengeTest {
    companion object Constants{
        private const val FORMATION_NAME = "Formação Android Developer"
        private val FORMATION_LEVEL = FormationLevel.INTERMEDIATE
    }


    private val challenge: Challenge = Challenge()
    private val formation:Formation = Formation(FORMATION_NAME, FORMATION_LEVEL)


    @Test
    fun addFormationTest() {
        assertFalse(challenge.hasAnyFormation())
        challenge.addFormation(formation)
        assertTrue(challenge.hasAnyFormation())
        assertEquals(1, challenge.getFormations().size)
    }

    @Test
    fun removeFormationTest() {
        challenge.addFormation(formation)
        assertTrue(challenge.hasAnyFormation())
        assertEquals(1, challenge.getFormations().size)
        challenge.removeFormation(formation)
        assertFalse(challenge.hasAnyFormation())
        assertEquals(0, challenge.getFormations().size)
    }

    @Test
    fun hasAnyFormationTest() {
        assertFalse(challenge.hasAnyFormation())
        challenge.addFormation(formation)
        assertTrue(challenge.hasAnyFormation())
    }

    @Test
    fun getFormationsTest() {
        assertTrue(challenge.getFormations().isEmpty())
    }

    @Test
    fun getFormationByIdTest() {
        challenge.addFormation(formation)
        val formationLocal = challenge.getFormationById(formation.id)
        assertEquals(formation, formationLocal)
    }

    @Test
    fun formationNotFoundExceptionTest(){
        assertThrows<FormationNotFoundException>{
            challenge.getFormationById(formationId = formation.id)
        }
    }
}
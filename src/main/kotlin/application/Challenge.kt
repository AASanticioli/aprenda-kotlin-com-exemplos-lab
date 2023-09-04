package application

import adaper.inn.ui.console.Menu
import domain.pedagogic.formation.Formation
import domain.pedagogic.formation.FormationNotFoundException

class Challenge(){
    private val formations: MutableSet<Formation> = mutableSetOf()


    fun addFormation(formation: Formation){
        formations.add(formation)
    }


    fun removeFormation(formation: Formation){
        formations.remove(formation)
    }


    fun hasAnyFormation() :Boolean{
        return formations.isNotEmpty()
    }


    fun getFormations(): Set<Formation>{
        return formations
    }


    @Throws
    fun getFormationById(formationId: Int): Formation {
        val formation: Formation = formations.filter { formation -> formation.id == formationId }.getOrElse(0) {
            throw FormationNotFoundException("There's no formation with id $it")
        }
        return formation
    }


    companion object {
        fun run(){
            Menu(Challenge()).run()
        }
    }
}
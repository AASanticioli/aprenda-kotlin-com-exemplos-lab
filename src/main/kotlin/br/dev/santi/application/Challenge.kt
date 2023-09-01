package br.dev.santi.application

import br.dev.santi.adapter.inn.ui.console.Menu
import br.dev.santi.domain.pedagogic.Formation

class Challenge(){
    companion object {
        private val formations: MutableSet<Formation> = mutableSetOf()
        fun run(){
            Menu(formations).run()
        }
    }
}
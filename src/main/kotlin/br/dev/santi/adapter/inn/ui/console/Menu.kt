package br.dev.santi.adapter.inn.ui.console

import br.dev.santi.adapter.inn.ui.console.AnsiEscape.clearConsole
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makeBlueText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makeCyanText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makePurpleText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makeRedText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makeWhiteText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.makeYellowText
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.resetAllAnsiEscapes
import br.dev.santi.adapter.inn.ui.console.AnsiEscape.startForegroundGreen
import br.dev.santi.domain.pedagogic.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Menu(private val formations: MutableSet<Formation>) {
    companion object Constants{
        private const val EXIT_KEY = "q"
        private const val DATE_FORMAT = "dd/MM/yyyy"
        private const val RETURNING_TO_MAIN_MENU = "Returning to main menu..."
        private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    }

    fun run(){
        println("Hello and Welcome.\n")
        loop@ while(true){
            printMenu()
            val userCommand = readln()
            val mainCommand: Int = userCommand.toIntOrNull() ?: 0
            when (mainCommand){
                1 -> showAllFormationsOnce()
                2 -> addFormation()
                3 -> showFormationDetails()
                4 -> addFormationContent()
                5 -> addFormationStudent()
                6 -> removeFormationContent()
                7 -> removeFormationStudent()
                9 -> break@loop
            }
        }
        clearConsole()
        println("Bye \u270B \n\n")
    }


    private fun printMenu() {
        clearConsole()
        resetAllAnsiEscapes()
        println("┌─────────────────────────────────────────────┐")
        println("│░░░░░ Inform the number of the command ░░░░░░│")
        println("├─────────────────────────────────────────────┤")
        println("│ ${makeYellowText("1")} ... ${makeBlueText("Show All Formations")}                   │")
        println("│                                             │")
        println("│ ${makeYellowText("2")} ... ${makeBlueText("Add Formation")}                         │")
        println("│                                             │")
        println("│ ${makeYellowText("3")} ... ${makeBlueText("Show Formation Details")}                │")
        println("│                                             │")
        println("│ ${makeYellowText("4")} ... ${makeBlueText("Add Contents to an Formation")}          │")
        println("│                                             │")
        println("│ ${makeYellowText("5")} ... ${makeBlueText("Add Students to an Formation")}          │")
        println("│                                             │")
        println("│ ${makeYellowText("6")} ... ${makeBlueText("Remove some Students of an Formation")}  │")
        println("│                                             │")
        println("│ ${makeYellowText("7")} ... ${makeBlueText("Remove some Contents of an Formation")}  │")
        println("│                                             │")
        println("│ ${makeRedText("9")} ... ${makeBlueText("Exit")}                                  │")
        println("│                                             │")
        println("└─────────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
    }


    private fun showAllFormationsOnce(){
        showAllFormations()
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }


    private fun addFormation() {
        clearConsole()
        println("░░░░░░░░░░░░░░░░░░░░░░░░░ Formation ░░░░░░░░░░░░░░░░░░░░░░░░░░░░")
        println(makeWhiteText("\nType the formation data."))
        println(makePurpleText("Leave blank and press <ENTER> to return to main menu.\n\n"))

        print("New formation name: ${startForegroundGreen()}")
        val newFormationNameTypedByUser: String = readln().trim()
        if (newFormationNameTypedByUser.isEmpty()){
            println("The name must be informed.")
            println(RETURNING_TO_MAIN_MENU)
            return
        }
        val formationName = if (newFormationNameTypedByUser.length>90)   newFormationNameTypedByUser.substring(1..90) else newFormationNameTypedByUser
        val formationLevel = getFormationLevel()
        if (formationLevel == -1){
            println(RETURNING_TO_MAIN_MENU)
            return
        }
        val formation = Formation(name = formationName, level = ContentLevel.entries[formationLevel - 1])
        addFormationContents(formation)
        addFormationStudents(formation)
        formations.add(formation)
        showAllFormations()
    }


    private fun showFormationDetails(){
        clearConsole()
        if (formations.isNotEmpty()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation you want to show"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId  = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0){
                showAllFormationData(formationId)
                println("\n${makeWhiteText("Press any key to return to main menu")}")
                readln()
            }
        }  else {
            println(makeYellowText("There are no formations registered"))
        }
    }


    private fun addFormationStudent() {
        clearConsole()
        if (formations.isNotEmpty()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId  = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0){
                try {
                    val formation = getFormationById(formationId)
                    addFormationStudents(formation = formation, confirmWithUser = false)
                } catch(e: FormationNotFoundException ){
                    println(makeYellowText("There's no formation with id $formationId"))
                }
            }
        }  else {
            println(makeYellowText("There are mo formations registered"))
        }
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }



    private fun addFormationContent() {
        clearConsole()
        if (formations.isNotEmpty()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId  = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0){
                try {
                    val formation = getFormationById(formationId)
                    addFormationContents(formation = formation, confirmWithUser = false)
                } catch(e: FormationNotFoundException ){
                    println(makeYellowText("There's no formation with id $formationId"))
                }
            }
        }  else {
            println(makeYellowText("There are mo formations registered"))
        }
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }


    private fun removeFormationStudent() {
        TODO("Not yet implemented")
    }


    private fun removeFormationContent() {
        TODO("Not yet implemented")
    }


    @Throws
    private fun getFormationById(formationId:Int):Formation{
        val formation: Formation = formations.filter { formation -> formation.id == formationId }.getOrElse(0) {
            throw FormationNotFoundException("There's no formation with id $it")
        }
        return formation
    }


    private fun showAllFormationData(formationId:Int){
        val formation: Formation
        try {
            formation = getFormationById(formationId)
            showFormation(formation = formation, detailed = true)
        } catch(e: FormationNotFoundException ){
            println(makeYellowText("There's no formation with id $formationId"))
        }
    }


    private fun showAllFormations(){
        clearConsole()
        if (formations.isNotEmpty()) {
            println("Showing all formations...")
            formations.forEach{
                showFormation(it)
            }
        }  else {
            println(makeYellowText("No formations registered"))
        }
    }


    private fun makeContentText(text: String, maxSize: Int): String{
        val blank = "                                                                  "
        val truncatedText = "$text$blank".substring(0..maxSize)
        return  makeCyanText(truncatedText)
    }


    private fun showFormation(formation: Formation, detailed: Boolean = false){
        val maxTitleSize = 55
        val idPadSize = 3
        val formationId = makeContentText(formation.id.toString().padStart(idPadSize, '0'), maxTitleSize)
        val formationName = makeContentText(formation.name, maxTitleSize)
        val formationDifficultLevelName = "${formation.level.name.substring(0..0)}${formation.level.name.substring(1..< formation.level.name.length).lowercase()}"
        val formationDifficultLevel = makeContentText(formationDifficultLevelName, maxTitleSize)
        resetAllAnsiEscapes()
        println("╔═══════════════════════════════════════════════════════════════════════╗")
        println("║ ID ......... $formationId ║")
        println("║ Name ....... $formationName ║")
        println("║ Difficult .. $formationDifficultLevel ║")
        if (detailed){
            println("╠═══════════════════════════════════════════════════════════════════════╣")
            println("║  ┌─────────────────────────────────────────────────────────────────┐  ║")
            println("║  │ ░░░░░░░░░░░░░░░░░░░░░░░░░ Contents ░░░░░░░░░░░░░░░░░░░░░░░░░░░░ │  ║")
            println("║  ├─────────────────────────────────────────────────────────────────┤  ║")
            formation.getContents().forEach { printContent(it) }
            println("║  └─────────────────────────────────────────────────────────────────┘  ║")
            println("║                                                                       ║")
            println("║  ┌─────────────────────────────────────────────────────────────────┐  ║")
            println("║  │ ░░░░░░░░░░░░░░░░░░░░░░░░░ Students ░░░░░░░░░░░░░░░░░░░░░░░░░░░░ │  ║")
            println("║  ├─────────────────────────────────────────────────────────────────┤  ║")
            formation.getStudents().forEach { printStudent(it) }
            println("║  └─────────────────────────────────────────────────────────────────┘  ║")
        }
        println("╚═══════════════════════════════════════════════════════════════════════╝")
        println("\n")
    }


    private fun printContent(content: Content){
        val contentMaxSize = 60
        val idPadSize = 3
        val text = makeContentText("(id: ${content.id.toString().padStart(idPadSize, '0')}) ${content.name}", contentMaxSize)
        println("║  │ ● $text │  ║")
    }


    private fun printStudent(student: Student){
        val contentMaxSize = 60
        val idPadSize = 7
        val text = makeContentText("(id: ${student.id.toString().padStart(idPadSize, '0')}) ${student.name}", contentMaxSize)
        println("║  │ ▪ $text │  ║")
    }


    private fun addFormationStudents(formation: Formation, confirmWithUser:Boolean = true){
        val userWantToAddStudents = if (confirmWithUser) askUserWantStudents() else true
        if (userWantToAddStudents){
            println(makePurpleText("Leave blank and press <ENTER> to finish.\n"))
            loop@ do {
                print("Student Name: ${startForegroundGreen()}")
                val studentNameTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (studentNameTypedByUser.isEmpty()){
                    break@loop
                }
                print("Student birth date ($DATE_FORMAT): ${startForegroundGreen()}")
                val studentBirthDateTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (studentBirthDateTypedByUser.isEmpty()){
                    println("Invalid Student Birth Date. Please, try again.")
                    continue@loop
                }
                if (studentBirthDateTypedByUser == EXIT_KEY){
                    break@loop
                }
                val studentBirthDate = try {
                    LocalDate.parse(studentBirthDateTypedByUser, formatter)
                } catch (e: DateTimeParseException){null}
                if (studentBirthDate == null){
                    println("Invalid Student Birth Date. Please, try again.")
                    continue@loop
                }
                val student = try {
                    Student(studentNameTypedByUser, studentBirthDate)
                } catch (e: StudentUnderageException){println("Invalid Student - Underage");  continue@loop}
                formation.enrol(student)
            } while(studentNameTypedByUser != EXIT_KEY)
        }
    }


    private fun addFormationContents(formation: Formation, confirmWithUser:Boolean = true){
        val userWantToAddContents = if (confirmWithUser) askUserWantContents() else true
        if (userWantToAddContents){
            println(makePurpleText("Leave blank and press <ENTER> to finish.\n"))
            var contentTypedByUser: String
            loop@ do {
                print("New content name: ${startForegroundGreen()}")
                contentTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (contentTypedByUser.isEmpty()){
                    break@loop
                }
                if (contentTypedByUser != EXIT_KEY){
                    val content = Content(contentTypedByUser)
                    formation.addContent(content)
                }
            } while(contentTypedByUser != EXIT_KEY)
        }
    }


    private fun askUserWantStudents(): Boolean{
        resetAllAnsiEscapes()
        println("┌──────────────────────────────────────────┐")
        println("│░░░░ Do you want to add students now? ░░░░│")
        println("├──────────────────────────────────────────┤")
        println("│ Y ........... Yes                        │")
        println("│ Other key ... No                         │")
        println("└──────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
        val addStudentsUserAnswer: String = readln().trim().uppercase()
        return "Y" == addStudentsUserAnswer || "YES" == addStudentsUserAnswer
    }


    private fun askUserWantContents(): Boolean{
        resetAllAnsiEscapes()
        println("┌──────────────────────────────────────────┐")
        println("│░░░░ Do you want to add Contents now? ░░░░│")
        println("├──────────────────────────────────────────┤")
        println("│ Y ........... Yes                        │")
        println("│ Other key ... No                         │")
        println("└──────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
        val addContentsUserAnswer: String = readln().trim().uppercase()
        return "Y" == addContentsUserAnswer || "YES" == addContentsUserAnswer
    }


    private fun getFormationLevel():Int{
        resetAllAnsiEscapes()
        println("┌──────────────────────────────────────────┐")
        println("│░░░░░░░ Formation Difficult Level ░░░░░░░░│")
        println("├──────────────────────────────────────────┤")
        println("│ ${makeYellowText("1")} ... Beginner                           │")
        println("│ ${makeYellowText("2")} ... Intermediate                       │")
        println("│ ${makeYellowText("3")} ... Advanced                           │")
        println("└──────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
        val newFormationLevelTypedByUser: String = readln()
        val newFormationLevel  = newFormationLevelTypedByUser.toIntOrNull() ?: return -1
        if (newFormationLevel !in 1..3){
            println("Invalid Formation Difficult Value")
            return getFormationLevel()
        }
        return newFormationLevel
    }
}
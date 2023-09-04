package adaper.inn.ui.console

import adaper.inn.ui.console.AnsiEscape.clearConsole
import adaper.inn.ui.console.AnsiEscape.makeBlueText
import adaper.inn.ui.console.AnsiEscape.makeCyanText
import adaper.inn.ui.console.AnsiEscape.makePurpleText
import adaper.inn.ui.console.AnsiEscape.makeRedText
import adaper.inn.ui.console.AnsiEscape.makeWhiteText
import adaper.inn.ui.console.AnsiEscape.makeYellowText
import adaper.inn.ui.console.AnsiEscape.resetAllAnsiEscapes
import adaper.inn.ui.console.AnsiEscape.startForegroundGreen
import application.Challenge
import domain.pedagogic.content.Content
import domain.pedagogic.content.ContentLevel
import domain.pedagogic.formation.*
import domain.pedagogic.student.Student
import domain.pedagogic.student.StudentUnderageException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Menu(private val challenge: Challenge) {
    companion object Constants {
        private const val EXIT_KEY = "q"
        private const val DATE_FORMAT = "dd/MM/yyyy"
        private const val RETURNING_TO_MAIN_MENU = "Returning to main menu..."
        private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
    }


    fun run() {
        println("Hello and Welcome.\n")
        loop@ while (true) {
            printMenu()
            val userCommand = readln()
            val mainCommand: Int = userCommand.toIntOrNull() ?: 0
            when (mainCommand) {
                1 -> showAllFormationsOnce()
                2 -> addFormation()
                3 -> showFormationDetails()
                4 -> addFormationContent()
                5 -> addFormationStudent()
                6 -> removeFormationContent()
                7 -> removeFormationStudent()
                8 -> removeFormation()
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
        println("│ ${makeYellowText("2")} ... ${makeBlueText("Add a Formation")}                       │")
        println("│                                             │")
        println("│ ${makeYellowText("3")} ... ${makeBlueText("Show Formation Details")}                │")
        println("│                                             │")
        println("│ ${makeYellowText("4")} ... ${makeBlueText("Add Contents to a Formation")}           │")
        println("│                                             │")
        println("│ ${makeYellowText("5")} ... ${makeBlueText("Add Students to a Formation")}           │")
        println("│                                             │")
        println("│ ${makeYellowText("6")} ... ${makeBlueText("Remove some Contents of a Formation")}   │")
        println("│                                             │")
        println("│ ${makeYellowText("7")} ... ${makeBlueText("Remove some Students of a Formation")}   │")
        println("│                                             │")
        println("│ ${makeYellowText("8")} ... ${makeBlueText("Remove a Formation")}                    │")
        println("│                                             │")
        println("│ ${makeRedText("9")} ... ${makeBlueText("Exit")}                                  │")
        println("│                                             │")
        println("└─────────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
    }


    private fun showAllFormationsOnce() {
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
        if (newFormationNameTypedByUser.isEmpty()) {
            println("The name must be informed.")
            println(RETURNING_TO_MAIN_MENU)
            return
        }
        val formationName =
            if (newFormationNameTypedByUser.length > 90) newFormationNameTypedByUser.substring(1..90) else newFormationNameTypedByUser
        val formationLevel = getFormationLevel()
        if (formationLevel == -1) {
            println(RETURNING_TO_MAIN_MENU)
            return
        }
        val formation = Formation(name = formationName, level = FormationLevel.entries[formationLevel - 1])
        addFormationContents(formation)
        addFormationStudents(formation)
        challenge.addFormation(formation)
        showAllFormations()
    }


    private fun showFormationDetails() {
        clearConsole()
        if (challenge.hasAnyFormation()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation you want to show"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0) {
                clearConsole()
                showAllFormationData(formationId)
                println("\n${makeWhiteText("Press any key to return to main menu")}")
                readln()
            }
        } else {
            println(makeYellowText("There are no formations registered"))
        }
    }


    private fun addFormationContent() {
        clearConsole()
        if (challenge.hasAnyFormation()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0) {
                try {
                    val formation = challenge.getFormationById(formationId)
                    addFormationContents(formation = formation, confirmWithUser = false)
                } catch (e: FormationNotFoundException) {
                    println(makeYellowText("There's no formation with id $formationId"))
                }
            }
        } else {
            println(makeYellowText("There are no formations registered"))
        }
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }


    private fun addFormationStudent() {
        clearConsole()
        if (challenge.hasAnyFormation()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            val formationId = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0) {
                try {
                    val formation = challenge.getFormationById(formationId)
                    addFormationStudents(formation = formation, confirmWithUser = false)
                } catch (e: FormationNotFoundException) {
                    println(makeYellowText("There's no formation with id $formationId"))
                }
            }
        } else {
            println(makeYellowText("There are no formations registered"))
        }
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }


    private fun removeFormationContent() {
        selectFormationBeforeCallFunction(::removeFormationContent)
    }


    private fun removeFormationStudent() {
        selectFormationBeforeCallFunction(::removeFormationStudent)
    }


    private fun removeFormation() {
        selectFormationBeforeCallFunction(::removeFormation)
    }


    private fun removeFormation(formation: Formation) {
        if (askUserToConfirmFormationDeletion()) {
            challenge.removeFormation(formation)
            println("Formation ${formation.name} deleted with success.")
        }
    }


    private fun selectFormationBeforeCallFunction(functionToCallWithFormationAsArgument: (Formation) -> Unit) {
        clearConsole()
        if (challenge.hasAnyFormation()) {
            showAllFormations()
            println(makeWhiteText("Please, type the ID of the formation"))
            print("Selected Formation ID: ${startForegroundGreen()}")
            val formationIdTypedByUser = readln().trim()
            resetAllAnsiEscapes()
            val formationId = formationIdTypedByUser.toIntOrNull() ?: 0
            if (formationId > 0) {
                try {
                    val formation = challenge.getFormationById(formationId)
                    functionToCallWithFormationAsArgument(formation)
                } catch (e: FormationNotFoundException) {
                    println(makeYellowText("There's no formation with id $formationId"))
                }
            }
        } else {
            println(makeYellowText("There are no formations registered"))
        }
        println("\n${makeWhiteText("Press any key to return to main menu")}")
        readln()
    }


    private fun removeFormationStudent(formation: Formation) {
        if (formation.haveStudents()) {
            showAllStudents(formation)
            println(makeWhiteText("Please, type the ID of the student"))
            print("Selected Student ID: ${startForegroundGreen()}")
            val studentIdTypedByUser = readln().trim()
            resetAllAnsiEscapes()
            val studentId = studentIdTypedByUser.toIntOrNull() ?: 0
            if (studentId > 0) {
                try {
                    val student = formation.getStudentById(studentId)
                    formation.removeStudent(student)
                } catch (e: FormationStudentNotFoundException) {
                    println(makeYellowText("There's no student with id $studentId"))
                }
            }
        } else {
            println(makeYellowText("There are no students registered"))
        }
    }


    private fun removeFormationContent(formation: Formation) {
        if (formation.haveContents()) {
            showAllContents(formation)
            println(makeWhiteText("Please, type the ID of the content"))
            print("Selected Content ID: ${startForegroundGreen()}")
            val contentIdTypedByUser = readln().trim()
            resetAllAnsiEscapes()
            val contentId = contentIdTypedByUser.toIntOrNull() ?: 0
            if (contentId > 0) {
                try {
                    val content = formation.getContentById(contentId)
                    formation.removeContent(content)
                } catch (e: FormationContentNotFoundException) {
                    println(makeYellowText("There's no content with id $contentId"))
                }
            }
        } else {
            println(makeYellowText("There are no contents registered"))
        }
    }


    private fun showAllFormationData(formationId: Int) {
        val formation: Formation
        try {
            formation = challenge.getFormationById(formationId)
            showFormation(formation = formation, detailed = true)
        } catch (e: FormationNotFoundException) {
            println(makeYellowText("There's no formation with id $formationId"))
        }
    }


    private fun showAllFormations() {
        clearConsole()
        if (challenge.hasAnyFormation()) {
            println("Showing all formations...")
            challenge.getFormations().forEach {
                showFormation(it)
            }
        } else {
            println(makeYellowText("No formations registered"))
        }
    }


    private fun makeContentText(text: String, maxSize: Int): String {
        val blank = "                                                                  "
        val truncatedText = "$text$blank".substring(0..maxSize)
        return makeCyanText(truncatedText)
    }


    private fun showAllStudents(formation: Formation) {
        println(" ┌─────────────────────────────────────────────────────────────────┐ ")
        println(" │ ░░░░░░░░░░░░░░░░░░░░░░░░░ Students ░░░░░░░░░░░░░░░░░░░░░░░░░░░░ │ ")
        println(" ├─────────────────────────────────────────────────────────────────┤ ")
        formation.getStudents().forEach { printStudent(it, withBorders = false) }
        println(" └─────────────────────────────────────────────────────────────────┘ ")
    }


    private fun showAllContents(formation: Formation) {
        println(" ┌─────────────────────────────────────────────────────────────────┐ ")
        println(" │ ░░░░░░░░░░░░░░░░░░░░░░░░░ Contents ░░░░░░░░░░░░░░░░░░░░░░░░░░░░ │ ")
        println(" ├─────────────────────────────────────────────────────────────────┤ ")
        formation.getContents().forEach { printContent(it, withBorders = false) }
        println(" └─────────────────────────────────────────────────────────────────┘ ")
    }


    private fun showFormation(formation: Formation, detailed: Boolean = false) {
        val maxTitleSize = 55
        val idPadSize = 3
        val formationId = makeContentText(formation.id.toString().padStart(idPadSize, '0'), maxTitleSize)
        val formationName = makeContentText(formation.name, maxTitleSize)
        val formationDifficultLevelName = "${formation.level.name.substring(0..0)}${
            formation.level.name.substring(1..<formation.level.name.length).lowercase()
        }"
        val formationDifficultLevel = makeContentText(formationDifficultLevelName, maxTitleSize)
        resetAllAnsiEscapes()
        println("╔═══════════════════════════════════════════════════════════════════════╗")
        println("║ ID ......... $formationId ║")
        println("║ Name ....... $formationName ║")
        println("║ Difficult .. $formationDifficultLevel ║")
        if (detailed) {
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


    private fun printContent(content: Content, withBorders: Boolean = true) {
        val contentMaxSize = 60
        val idPadSize = 3
        val contentDifficultLevelName = "${content.level.name.substring(0..0)}${
            content.level.name.substring(1..<content.level.name.length).lowercase()
        }"
        val id = "id: ${content.id.toString().padStart(idPadSize, '0')}"
        val textLine1 = makeContentText("$id - level: $contentDifficultLevelName", contentMaxSize)
        val textLine2 = makeContentText("${content.name}", contentMaxSize)
        if (withBorders) println("║  │ ● $textLine1 │  ║") else println(" │ ● $textLine1 │ ")
        if (withBorders) println("║  │   $textLine2 │  ║") else println(" │   $textLine2 │ ")
    }


    private fun printStudent(student: Student, withBorders: Boolean = true) {
        val contentMaxSize = 60
        val idPadSize = 7
        val text =
            makeContentText("(id: ${student.id.toString().padStart(idPadSize, '0')}) ${student.name}", contentMaxSize)
        if (withBorders) println("║  │ ▪ $text │  ║") else println(" │ ▪ $text │ ")
    }


    private fun addFormationStudents(formation: Formation, confirmWithUser: Boolean = true) {
        val userWantToAddStudents = if (confirmWithUser) askUserWantStudents() else true
        if (userWantToAddStudents) {
            println(makePurpleText("Leave blank and press <ENTER> to finish.\n"))
            loop@ do {
                print("Student Name: ${startForegroundGreen()}")
                val studentNameTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (studentNameTypedByUser.isEmpty()) {
                    break@loop
                }
                print("Student birth date ($DATE_FORMAT): ${startForegroundGreen()}")
                val studentBirthDateTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (studentBirthDateTypedByUser.isEmpty()) {
                    println("Invalid Student Birth Date. Please, try again.")
                    continue@loop
                }
                if (studentBirthDateTypedByUser == EXIT_KEY) {
                    break@loop
                }
                val studentBirthDate = try {
                    LocalDate.parse(studentBirthDateTypedByUser, formatter)
                } catch (e: DateTimeParseException) {
                    null
                }
                if (studentBirthDate == null) {
                    println("Invalid Student Birth Date. Please, try again.")
                    continue@loop
                }
                val student = try {
                    Student(studentNameTypedByUser, studentBirthDate)
                } catch (e: StudentUnderageException) {
                    println("Invalid Student - Underage"); continue@loop
                }
                formation.enrol(student)
            } while (studentNameTypedByUser != EXIT_KEY)
        }
    }


    private fun addFormationContents(formation: Formation, confirmWithUser: Boolean = true) {
        val userWantToAddContents = if (confirmWithUser) askUserWantContents() else true
        if (userWantToAddContents) {
            println(makePurpleText("Leave blank and press <ENTER> to finish.\n"))
            var contentTypedByUser: String
            loop@ while (true) {
                print("New content name: ${startForegroundGreen()}")
                contentTypedByUser = readln().trim()
                resetAllAnsiEscapes()
                if (contentTypedByUser.isEmpty()) {
                    break@loop
                }


                val contentLevel = getContentLevel()
                if (contentLevel == -1) {
                    println(RETURNING_TO_MAIN_MENU)
                    return
                }
                val content = Content(name = contentTypedByUser, level = ContentLevel.entries[contentLevel - 1])
                formation.addContent(content)
            }
        }
    }


    private fun askUserWantStudents(): Boolean {
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


    private fun askUserWantContents(): Boolean {
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


    private fun askUserToConfirmFormationDeletion(): Boolean {
        resetAllAnsiEscapes()
        println("┌───────────────────────────────────────────────────────┐")
        println("│░░░░ Do you confirm the deletion of the formation? ░░░░│")
        println("├───────────────────────────────────────────────────────┤")
        println("│   Y ............. Yes                                 │")
        println("│   Other key ..... No                                  │")
        println("└───────────────────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
        val addContentsUserAnswer: String = readln().trim().uppercase()
        return "Y" == addContentsUserAnswer || "YES" == addContentsUserAnswer
    }


    private fun getFormationLevel(): Int {
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
        val newFormationLevel = newFormationLevelTypedByUser.toIntOrNull() ?: return -1
        if (newFormationLevel !in 1..3) {
            println("Invalid Formation Difficult Value")
            return getFormationLevel()
        }
        return newFormationLevel
    }

    private fun getContentLevel(): Int {
        resetAllAnsiEscapes()
        println("┌──────────────────────────────────────────┐")
        println("│░░░░░░░░ Content Difficult Level ░░░░░░░░░│")
        println("├──────────────────────────────────────────┤")
        println("│ ${makeYellowText("1")} ... Beginner                           │")
        println("│ ${makeYellowText("2")} ... Intermediate                       │")
        println("│ ${makeYellowText("3")} ... Advanced                           │")
        println("└──────────────────────────────────────────┘")
        print("Selected: ${startForegroundGreen()}")
        val newContentLevelTypedByUser: String = readln()
        resetAllAnsiEscapes()
        val newContentLevel = newContentLevelTypedByUser.toIntOrNull() ?: return -1
        if (newContentLevel !in 1..3) {
            println("Invalid Content Difficult Value")
            return getContentLevel()
        }
        return newContentLevel
    }
}
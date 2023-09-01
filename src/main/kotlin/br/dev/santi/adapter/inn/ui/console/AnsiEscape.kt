package br.dev.santi.adapter.inn.ui.console

import java.time.format.DateTimeFormatter

object AnsiEscape {
    /** ANSI escape codes **/
    private const val ANSI_ESC = "\u001B"
    private const val ANSI_RESET = "${ANSI_ESC}[0m"
    private const val ANSI_FOREGROUND_BLACK = "${ANSI_ESC}[30m";
    private const val ANSI_FOREGROUND_RED  = "${ANSI_ESC}[31m"
    private const val ANSI_FOREGROUND_GREEN = "${ANSI_ESC}[32m"
    private const val ANSI_FOREGROUND_YELLOW = "${ANSI_ESC}[33m"
    private const val ANSI_FOREGROUND_BLUE = "${ANSI_ESC}[34m"
    private const val ANSI_FOREGROUND_PURPLE = "${ANSI_ESC}[35m"
    private const val ANSI_FOREGROUND_CYAN = "${ANSI_ESC}[36m"
    private const val ANSI_FOREGROUND_WHITE = "${ANSI_ESC}[37m";
    private const val ANSI_BACKGROUND_GRAY = "${ANSI_ESC}[48;5;250m";

    private const val ANSI_MOVE_CURSOR_TO_HOME_POSITION= "${ANSI_ESC}[H"
    private const val ANSI_MOVE_CURSOR_LINE_UP = "${ANSI_ESC}[1A"
    private const val ANSI_MOVE_CURSOR_COLUMN_RIGHT = "${ANSI_ESC}[1C"
    private const val ANSI_CLEARS_ENTIRE_SCREEN = "${ANSI_ESC}[2J"
    private const val ANSI_ITALIC = "${ANSI_ESC}[3m"

    private const val UNICODE_KEYBOARD_CODE = "\u2328"

    private const val EXIT_KEY = "q"
    private const val DATE_FORMAT = "dd/MM/yyyy"
    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    private fun makeText(text: String, color:String):String{
        return "$color$text${ANSI_RESET}"
    }
    fun makeBlueText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_BLUE)
    }
    fun makePurpleText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_PURPLE)
    }

    fun makeYellowText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_YELLOW)
    }

    fun makeRedText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_RED)
    }

    fun makeCyanText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_CYAN)
    }
    fun makeWhiteText(text:String):String{
        return makeText(text, ANSI_FOREGROUND_WHITE)
    }

    fun startForegroundGreen():String{
        return ANSI_FOREGROUND_GREEN
    }

    fun makeKeyboard():String{
        return UNICODE_KEYBOARD_CODE
    }

    fun resetAllAnsiEscapes(){
        print(ANSI_RESET)
    }

    fun clearConsole(){
        print("$ANSI_MOVE_CURSOR_TO_HOME_POSITION$ANSI_CLEARS_ENTIRE_SCREEN$ANSI_RESET")
    }
}
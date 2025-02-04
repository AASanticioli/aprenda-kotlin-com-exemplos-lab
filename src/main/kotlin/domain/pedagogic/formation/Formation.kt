package domain.pedagogic.formation

import domain.pedagogic.content.Content
import domain.pedagogic.student.Student

class Formation private constructor(val id: Int, val name: String, val level: FormationLevel, private val contents: MutableSet<Content>, private val students: MutableSet<Student>){
    companion object{
        internal const val MAX_STUDENTS_CAPACITY = 300
        internal const val FULL_CAPACITY_EXCEPTION_MESSAGE = "Formation is full. No more students can be enrolled."
        private var currentId = 0
        operator fun invoke(name: String,  level: FormationLevel): Formation {
            val contents: MutableSet<Content> = mutableSetOf()
            val students: MutableSet<Student> = mutableSetOf()
            return Formation(++currentId, name = name, level = level, contents = contents, students = students )
        }
    }
    @Throws
    fun enrol(vararg student: Student){
        if (isFull()){
            throw FormationFullException(FULL_CAPACITY_EXCEPTION_MESSAGE)
        }
        students.addAll(listOf(*student))
    }

    fun getStudentsEnrolledQuantity(): Int{
        return students.size
    }

    fun isStudentEnrolled(student: Student): Boolean{
        return students.any { it == student }
    }

    fun isFull(): Boolean{
        return MAX_STUDENTS_CAPACITY == getStudentsEnrolledQuantity()
    }

    fun getContents(): Set<Content>{
        return contents
    }

    fun getStudents(): Set<Student>{
        return students
    }

    fun haveStudents(): Boolean{
        return students.isNotEmpty()
    }

    fun haveContents(): Boolean{
        return contents.isNotEmpty()
    }


    fun addContent(content: Content){
        contents.add(content)
    }

    @Throws
    fun getStudentById(studentId: Int): Student {
        val student: Student = students.filter { student -> student.id == studentId }.getOrElse(index = 0) {
            throw FormationStudentNotFoundException("There's no Student with id $it")
        }
        return student
    }

    fun removeStudent(student: Student) {
        students.remove(student)
        println("Student '${student.name}' removed with success.")
    }

    @Throws
    fun getContentById(contentId: Int): Content {
        val content: Content = contents.filter { student -> student.id == contentId }.getOrElse(index = 0) {
            throw FormationContentNotFoundException("There's no Content with id $it")
        }
        return content
    }

    fun removeContent(content: Content) {
        contents.remove(content)
        println("Content '${content.name}' removed with success.")
    }
}
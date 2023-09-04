package domain.pedagogic.content

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ContentTest {
    companion object Constants{
        private const val CONTENT_ID_1 = 1
        private const val CONTENT_NAME_1 = "Content 1"
        private val CONTENT_LEVEL_1 = ContentLevel.BEGINNER

        private const val CONTENT_ID_2 = 2
        private const val CONTENT_NAME_2 = "Content 2"
        private val CONTENT_LEVEL_2 = ContentLevel.INTERMEDIATE

        private const val CONTENT_ID_3 = 1
        private const val CONTENT_NAME_3 = "Content 3"
        private val CONTENT_LEVEL_3 = ContentLevel.ADVANCED
    }

    @Test
    fun createTest() {
        val content1 = Content(name = CONTENT_NAME_1, level = CONTENT_LEVEL_1)
        val content1B = Content(id= CONTENT_ID_1, name = CONTENT_NAME_1, level = CONTENT_LEVEL_1)
        val content2 = Content(name = CONTENT_NAME_2, level = CONTENT_LEVEL_2)
        assertNotEquals(content1, content2)
        assertEquals(content1, content1B)
    }

    @Test
    fun equalsTest() {
        val content1 = Content(id = CONTENT_ID_1, name = CONTENT_NAME_1, level = CONTENT_LEVEL_1)
        val content2 = Content(id = CONTENT_ID_2, name = CONTENT_NAME_2, level = CONTENT_LEVEL_2)
        val content3 = Content(id = CONTENT_ID_3, name = CONTENT_NAME_3, level = CONTENT_LEVEL_3)
        assertNotEquals(content1, content2)
        assertEquals(content1, content3)
    }

    @Test
    fun hashCodeTest() {
        val content1 = Content(id = CONTENT_ID_1, name = CONTENT_NAME_1, level = CONTENT_LEVEL_1)
        assertEquals(CONTENT_ID_1, content1.hashCode())
    }

}
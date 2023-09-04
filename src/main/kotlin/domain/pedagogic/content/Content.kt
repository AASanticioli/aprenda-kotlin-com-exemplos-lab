package domain.pedagogic.content

class Content (val id: Int, val name: String, val level: ContentLevel ){
    companion object {
        private var currentId = 0

        operator fun invoke( name: String, level: ContentLevel): Content {
            return Content( ++currentId, name, level)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Content

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
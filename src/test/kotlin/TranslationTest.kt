import com.hakob.flashcards.service.WordService
import com.hakob.flashcards.service.sentenceIsOverOnNextForwardWord
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TranslationTest
{
    val text: String = "version control logs, should. show that test code is checked. in each time product code"
    val wordService: WordService = WordService(mutableMapOf<Int, String>())

    @Test
    fun `should return target sentence`() {
        // given
        wordService.map = text.split(" ").mapIndexed { index, string -> index to string }.toMap().toMutableMap()
        val expected = "show that test code is checked."
        val wordId: Int = 5

        // when
        val result = wordService.getTargetSentence(wordId)

        // then
        result shouldBe expected
    }

    @Test
    fun `should return true if the word followed by terminator sign is reached`() {
        // given
        val list: List<String> = listOf("This", "is", "first.",
            "This", "is", "second.", "This", "is", "third.")
        val listIterator = list.listIterator()

        // move to last word of second sentence
        while (listIterator.hasNext() && listIterator.nextIndex() != list.indexOf("second.")) {
            println("WordUnderIdnex: ${listIterator.next()}")
        }

        // when
        val result = listIterator.sentenceIsOverOnNextForwardWord<String>()

        // then
        result shouldBe true
    }
}
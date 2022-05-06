import com.hakob.flashcards.service.WordService
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
}
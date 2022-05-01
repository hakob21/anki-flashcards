import com.hakob.flashcards.utils.TranslateUtils
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.Test

class AppTest {
//    val translateUtils: TranslateUtils = mockk<TranslateUtils>()
    val translateUtils: TranslateUtils = TranslateUtils()

    @Test
    fun `should return sentence (context) in a list`() {
        every { translateUtils.getTranslatedWord("hello") } returns "привет"
        // given
        val wordToTranslate = "hello"
        val expected = "привет"

        // when
        val actual = translateUtils.getTranslatedWord(wordToTranslate)

        // then
        actual shouldBe expected
    }

    @Test
    fun `should return list of words from the expected sentence`() {
        // given
        val listOfWordsFromText = listOf("Word", "word", "word", "word.", "Word", "word.", "Word", "word", "word")

        // when
        val actual = translateUtils.getWordsFromSentence(indexOfWord = 5, listOfWordsFromText = listOfWordsFromText)

        // then
        actual shouldBe "Word word."
    }
}
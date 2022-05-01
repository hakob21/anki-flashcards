import com.hakob.flashcards.testPack.HelloApplication
import com.hakob.flashcards.testPack.TranslateUtils
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

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
        actual shouldContainExactly listOf("Word", "word.")
    }
}
import com.hakob.flashcards.testPack.HelloApplication
import com.hakob.flashcards.testPack.TranslateUtils
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AppTest {
    @Autowired
    private lateinit var translateUtils: TranslateUtils

    @Test
    fun `should return sentence (context) in a list`() {
        // given
        val wordToTranslate = "hello"
        val expected = "привет"

        // when
        val actual = translateUtils.getTranslatedWord(wordToTranslate)

        // then
        actual shouldBe expected
    }

    @Test
    fun `adsadshould return sentence (context) in a list`() {
        println("a")
    }
}
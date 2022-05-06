import com.hakob.flashcards.service.WordService
import com.hakob.flashcards.service.movePointerToNextIfSentenceIsNotOver
import com.hakob.flashcards.service.movePointerToPreviousIfSentenceIsNotOver
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.spyk
import org.junit.jupiter.api.Test
import kotlin.math.exp

class TranslationTest
{
    val text: String = "version control logs, should. show that test code is checked. in each time product code"
    val wordService: WordService = WordService()

    @Test
    fun `should return correct target sentence for wordId`() = forAll(
        row(0, "version control logs, should."),
        row(2, "version control logs, should."),
        row(3, "version control logs, should."),
        row(4, "show that test code is checked."),
        row(5, "show that test code is checked."),
        row(9, "show that test code is checked."),
        row(14, "in each time product code"),
    ) {
        wordId, expected ->
        // given
        wordService.list = text.split(" ")

        // when
        val result = wordService.getTargetSentence(wordId)

        // then
        result shouldBe expected
    }

    @Test
    fun `should return true if the word followed by terminator sign is reached`() = forAll(
        row(0, false),
        row(1, true),
    ) {
        wordId, expected ->
        // given
        val list = listOf<String>("This", "is", "test")
        val listIterator = list.listIterator(wordId)

        // when
        val result = listIterator.movePointerToPreviousIfSentenceIsNotOver<String>()

        // then
        result shouldBe expected
    }

    @Test
    fun `should return true if the word by terminator sign is reached`() = forAll(
        row(1, "This is test".split(" "), true),
        row(2, "This is test.".split(" "), false),
        row(3, "This is test".split(" "), false),
        row(2, "This is test!".split(" "), false),
    ) {
        wordId, list, expected ->
        // given
        val listIterator = list.listIterator(wordId)

        // when
        val result = listIterator.movePointerToNextIfSentenceIsNotOver<String>()

        // then
        result shouldBe expected
    }
}
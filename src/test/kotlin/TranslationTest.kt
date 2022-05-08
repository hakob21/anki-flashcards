import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.WordService
import com.hakob.flashcards.service.movePointerToNextIfSentenceIsNotOver
import com.hakob.flashcards.service.movePointerToPreviousIfSentenceIsNotOver
import com.hakob.flashcards.utils.FileUtils
import com.hakob.flashcards.utils.TranslateUtils
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class TranslationTest
{
    val text: String = "version control logs, should. show that test code is checked. in each time product code"
    val wordService: WordService = WordService()
    val mainService: MainService = MainService(
        mockk<TranslateUtils>(),
        mockk<FileUtils>(),
        wordService
    )

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
        wordService.listOfWords = text.split(" ")

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

    @Test
    fun `should return target paragraph`() {
        // given
        wordService.document = Jsoup.parse(
            javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFile2.html").readText()
        )
        val text = "You want to allow untrusted users to supply HTML for output on your website (e.g. as comment submission). You need to clean this HTML to avoid cross-site scripting (XSS) attacks. Use the jsoup HTML Cleaner with a configuration specified by a Safelist. Link Link A cross-site scripting attack against your site can really ruin your day, not to mention your users'. Many sites avoid XSS attacks by not allowing HTML in user submitted content: they enforce plain text only, or use an alternative markup syntax like wiki-text or Markdown. These are seldom optimal solutions for the user, as they lower expressiveness, and force the user to learn a new syntax. A better solution may be to use a rich text WYSIWYG editor (like CKEditor or TinyMCE). These output HTML, and allow the user to work visually. However, their validation is done on the client side: you need to apply a server-side validation to clean up the input and ensure the HTML is safe to place on your site. Otherwise, an attacker can avoid the client-side Javascript validation and inject unsafe HMTL directly into your site This is a context sentence which contains the key word and the word 'key' here should be translated It does not use regular expressions, which are inappropriate for this task. jsoup provides a range of Safelist configurations to suit most requirements; they can be modified if necessary, but take care. The cleaner is useful not only for avoiding XSS, but also in limiting the range of elements the user can provide: you may be OK with textual a, strong elements, but not structural div or table elements."
        wordService.listOfWords = text.split(" ")

//        val expected =

        // when
        val result = wordService.getTargetParagraphOrFallbackToSentenceInParagraph(191)

        // then
//        result shouldBe
        println(result)

    }
}
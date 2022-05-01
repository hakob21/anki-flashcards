import com.hakob.flashcards.testPack.HelloApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class AppTest {
    @Autowired
    private lateinit var helloApplication: HelloApplication

    @Test
    fun `should return sentence (context) in a list`() {
        println("a")
    }

    @Test
    fun `adsadshould return sentence (context) in a list`() {
        println("a")
    }
}
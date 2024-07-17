import com.atipera.app.infrastructure.client.GitHubFeignClient
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GitHubFeignClientTest {
    private val clientTest = mockk<GitHubFeignClient>()
    @Test
    fun `test getUserRepositories`(){
    every { clientTest.getUserRepositories("user") } returns listOf()
    }
}
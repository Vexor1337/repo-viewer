import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = [Test::class])
@WireMockTest(httpPort = 8081)
class GitHubClientIntegrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {

        resetAllRequests()

        stubFor(get(urlPathEqualTo("/users/testuser/repos"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""[
                    {"name": "repo1", "owner": {"login": "testuser"}}
                ]""")))

        stubFor(get(urlPathEqualTo("/repo-viewer/getRepositoriesList?username=testuser"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""[
                    {"name": "main", "commit": {"sha": "abc123"}}
                ]""")))
    }

    @Test
    fun `test getGitHubRepositoriesWithBranchesByUsername`() {
        val response = webTestClient.get()
            .uri("http://localhost:8081/repo-viewer/getRepositoriesList?username=testuser")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1)
            .jsonPath("$[0].name").isEqualTo("repo1")
            .jsonPath("$[0].branches.length()").isEqualTo(1)
            .jsonPath("$[0].branches[0].branchName").isEqualTo("main")
            .jsonPath("$[0].branches[0].sha").isEqualTo("abc123")
    }
}
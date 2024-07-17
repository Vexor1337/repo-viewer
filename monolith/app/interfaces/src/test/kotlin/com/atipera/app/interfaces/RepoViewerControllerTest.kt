import arrow.core.Either
import com.atipera.app.interfaces.RepoViewerController
import com.atipera.app.ports.input.BranchProjection
import com.atipera.app.ports.input.RepositoryProjection
import com.atipera.app.ports.input.service.RepoViewerService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus


class RepoViewerControllerTest {


    private val repoViewerService = mockk<RepoViewerService>()
    private val controller = RepoViewerController(repoViewerService)




    @Test
    fun `should return list of repositories when username is valid`() {

        val username = "testuser"
        val repositories = listOf(
            RepositoryProjection("repo1", "owner1", listOf(BranchProjection("branch1", "sha1"))),
            RepositoryProjection("repo2", "owner2", listOf(BranchProjection("branch2", "sha2")))
        )
        every { repoViewerService.getRepositoriesListWithBranchesByUsername(username) } returns Either.Right(
            repositories
        )
        val response = controller.getRepositoriesByUsername(username)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(repositories, response.body!!.data)
    }
}

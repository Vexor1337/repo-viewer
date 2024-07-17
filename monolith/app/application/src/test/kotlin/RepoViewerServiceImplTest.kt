import arrow.core.Either
import arrow.core.right
import com.atipera.app.application.RepoViewerServiceImpl
import com.atipera.app.ports.input.BranchProjection
import com.atipera.app.ports.input.RepositoryProjection
import com.atipera.app.ports.input.service.RepoViewerService
import com.atipera.app.ports.output.BranchDto
import com.atipera.app.ports.output.GitHubClient
import com.atipera.app.ports.output.RepositoryWithBranchesDto
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepoViewerServiceImplTest {
    private val gitHubClient = mockk<GitHubClient>()
    private val repoViewerService = RepoViewerServiceImpl(gitHubClient)

    @Test
    fun `getRepositoriesListWithBranchesByUsername should return a list of repositories`() {
        val username = "user"
        val repositoriesDto = listOf(
            RepositoryWithBranchesDto("repo1", "owner1", listOf(BranchDto("branch1", "sha1"))),
            RepositoryWithBranchesDto("repo2", "owner2", listOf(BranchDto("branch2", "sha2")))
        )
        val expectedResponse = Either.Right(
            listOf(
                RepositoryProjection("repo1", "owner1", listOf(BranchProjection("branch1", "sha1"))),
                RepositoryProjection("repo2", "owner2", listOf(BranchProjection("branch2", "sha2")))
            )
        )
        every { gitHubClient.getGitHubRepositoriesWithBranchesByUsername(username) } returns Either.Right(
            repositoriesDto
        )
        val response = repoViewerService.getRepositoriesListWithBranchesByUsername(username)

        assertEquals(expectedResponse, response)
    }

}
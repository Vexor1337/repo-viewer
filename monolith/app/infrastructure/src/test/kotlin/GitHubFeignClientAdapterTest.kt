import arrow.core.Either
import arrow.core.getOrElse
import com.atipera.app.commons.GitHubApiUnauthorizedException
import com.atipera.app.commons.UserNotFoundException
import com.atipera.app.infrastructure.GitHubFeignClientAdapter
import com.atipera.app.infrastructure.client.GitHubFeignClient
import com.atipera.app.infrastructure.responses.Commit
import com.atipera.app.infrastructure.responses.GetRepositoryBranchesResponse
import com.atipera.app.infrastructure.responses.GetUserRepositoriesResponse
import com.atipera.app.infrastructure.responses.Owner
import com.atipera.app.ports.output.BranchDto
import com.atipera.app.ports.output.RepositoryWithBranchesDto
import feign.FeignException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GitHubFeignClientAdapterTest {
    private val client = mockk<GitHubFeignClient>()
    private val adapter = GitHubFeignClientAdapter(client)
    private val username = "testUser"
    private val getUserRepositoryApiResponse = listOf(
        GetUserRepositoriesResponse("repo1", Owner(username)),
        GetUserRepositoriesResponse("repo2", Owner(username))
    )
    private val getRepositoryBranchesApiResponseMock =
        listOf(
            GetRepositoryBranchesResponse("branch1", Commit("commit1")),
            GetRepositoryBranchesResponse("branch2", Commit("commit2"))
        )

    private val getGitHubRepositoriesListExpectedResponse = Either.Right(
        listOf(
            RepositoryWithBranchesDto(
                name = "repo1",
                owner = "testUser",
                branches = listOf(
                    BranchDto(branchName = "branch1", sha = "commit1"),
                    BranchDto(branchName = "branch2", sha = "commit2")
                )
            ),
            RepositoryWithBranchesDto(
                name = "repo2",
                owner = "testUser",
                branches = listOf(
                    BranchDto(branchName = "branch1", sha = "commit1"),
                    BranchDto(branchName = "branch2", sha = "commit2")
                )
            )
        )

    )

    @Test
    fun `test getGitHubRepositoriesWithBranchesByUsername`() {
        every { client.getUserRepositories(username) } returns getUserRepositoryApiResponse
        every { client.getRepositoryBranches(username, "repo1") } returns getRepositoryBranchesApiResponseMock
        every { client.getRepositoryBranches(username, "repo2") } returns getRepositoryBranchesApiResponseMock

        val response = adapter.getGitHubRepositoriesWithBranchesByUsername(username)

        assertEquals(getGitHubRepositoriesListExpectedResponse, response)
    }

    @Test
    fun `getGitHubRepositoriesWithBranchesByUsername should return Unauthorized`() {


        val feignUnauthorized = mockk<FeignException.Unauthorized>()

        every { client.getUserRepositories(username) } throws feignUnauthorized


        val result = adapter.getGitHubRepositoriesWithBranchesByUsername(username).getOrElse { it }
        assertEquals(GitHubApiUnauthorizedException::class,result::class )
    }
    @Test
    fun `getGitHubRepositoriesWithBranchesByUsername should return UserNotFoundException`() {


        val feignNotFound = mockk<FeignException.NotFound>()

        every { client.getUserRepositories(username) } throws feignNotFound


        val result = adapter.getGitHubRepositoriesWithBranchesByUsername(username).getOrElse { it }
        assertEquals(UserNotFoundException::class,result::class )
    }

}
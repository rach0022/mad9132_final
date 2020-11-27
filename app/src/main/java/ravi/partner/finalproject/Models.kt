package ravi.partner.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

// region Returned JSON
/*  {
    "total_count": 1,
    "incomplete_results": false,
    "items": [
        {
          "login": "rach0022",
          "id": 54949430,
          "node_id": "MDQ6VXNlcjU0OTQ5NDMw",
          "avatar_url": "https://avatars2.githubusercontent.com/u/54949430?v=4",
          "gravatar_id": "",
          "url": "https://api.github.com/users/rach0022",
          "html_url": "https://github.com/rach0022",
          "followers_url": "https://api.github.com/users/rach0022/followers",
          "following_url": "https://api.github.com/users/rach0022/following{/other_user}",
          "gists_url": "https://api.github.com/users/rach0022/gists{/gist_id}",
          "starred_url": "https://api.github.com/users/rach0022/starred{/owner}{/repo}",
          "subscriptions_url": "https://api.github.com/users/rach0022/subscriptions",
          "organizations_url": "https://api.github.com/users/rach0022/orgs",
          "repos_url": "https://api.github.com/users/rach0022/repos",
          "events_url": "https://api.github.com/users/rach0022/events{/privacy}",
          "received_events_url": "https://api.github.com/users/rach0022/received_events",
          "type": "User",
          "site_admin": false,
          "score": 1.0
        }
    ]
}
*/
// endregion
// region REST API Interface
// an interface to preform the fetch
interface RestAPI{
    @GET("users?")
    fun getUserData(@Query("q") searchName: String):
            Call<ResponseDataClass>
}
// endregion

data class ResponseDataClass(
        val incomplete_results: Boolean = false,
        val items: ArrayList<Users>,
        val total_count: Int = 0
) : Serializable

// a Data class is a solution to a very common problem
// a class that only holds data (kinda like a struct)
data class Users(
    val avatar_url: String = "",
    val events_url: String = "",
    val followers_url: String = "",
    val following_url: String = "",
    val gists_url: String = "",
    val gravatar_id: String = "",
    val html_url: String = "",
    val id: Int = 0,
    val login: String = "",
    val node_id: String = "",
    val organizations_url: String = "",
    val received_events_url: String = "",
    val repos_url: String = "",
    val score: Double = 0.0,
    val site_admin: Boolean = false,
    val starred_url: String = "",
    val subscriptions_url: String = "",
    val type: String = "",
    val url: String = ""
) : Serializable
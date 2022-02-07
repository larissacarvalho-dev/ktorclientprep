package com.appkptor.data.api

import com.appkptor.data.remote.HttpRoutes
import com.appkptor.data.remote.PostRequest
import com.appkptor.data.remote.PostResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class PostServiceImpl(
    private val client: HttpClient
) : PostService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            client.get { url(HttpRoutes.POSTS) }
        } catch (e: RedirectResponseException) {
            println("Error Redirect: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            println("Error Client: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            println("Error Server: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            println("Errorn: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse> {
                url(HttpRoutes.POSTS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        } catch (e: RedirectResponseException) {
            println("Error Redirect: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            println("Error Client: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            println("Error Server: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Errorn: ${e.message}")
            null
        }
    }
}
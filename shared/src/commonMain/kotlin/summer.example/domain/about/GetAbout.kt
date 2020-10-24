package summer.example.domain.about

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import summer.example.entity.About

class GetAbout(
    private val httpClient: HttpClient,
    private val json: Json
) {
    suspend operator fun invoke(): About {
        val response = httpClient.get<HttpStatement>(
            urlString = "https://gist.githubusercontent.com/a-dminator/d463890e92c6b3a1e5bbd70411b82c50/raw/c617164655a3db6268b33000b8d694336d736120/about.json"
        ).execute()
        val responseText = response.readText()
        return json.decodeFromString(About.serializer(), responseText)
    }
}
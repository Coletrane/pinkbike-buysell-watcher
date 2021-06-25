package com.aws.blog.jvmlangs.kotlin

import java.io.*
import com.fasterxml.jackson.module.kotlin.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.time.LocalDateTime

data class BuysellWatchRequest(val url: String)
data class BuysellWatchResponse(val newItemUrls: List<String>)

class Main {
    val mapper = jacksonObjectMapper()

    fun handler(input: InputStream, output: OutputStream): Unit {
        val inputObj = mapper.readValue<BuysellWatchRequest>(input)

        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("--headless")
        val driver = ChromeDriver(chromeOptions)

        val buysellUrl = inputObj.url
        driver.get(buysellUrl)
    }
}

@DynamoDbBean
data class BuysellWatchMetadata(
    @get:DynamoDbPartitionKey var id: String? = null,
    val url: String,
    val firstAdded: LocalDateTime,
    val lastUpdated: LocalDateTime
)
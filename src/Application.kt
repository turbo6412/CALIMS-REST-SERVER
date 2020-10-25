package org.csuf.cspc411

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import org.csuf.cspc411.Dao.Database
import org.csuf.cspc411.Dao.claim.ClaimDao
import org.csuf.cspc411.Dao.claim.ClaimDataClass
import java.util.*


fun main(args: Array<String>): Unit {
    // Register PersonStore callback functions

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // extension
    // @annotation
    // routing constructor takes only one parameter which is a lambda function
    // DSL - Domain Specific Language
    routing{

        get("/ClaimService/getAll"){
            val claimList = ClaimDao().getAll()
            println("The amount of claims : ${claimList.size}")
            // JSON serialize the array
            val respJsonStr = Gson().toJson(claimList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

        post ("/ClaimService/add"){
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)    // for later processing

            //deserialize client data to add into Claims db as JSON
            val gsonString = Gson().fromJson(str, ClaimDataClass::class.java)
            val claimObj = ClaimDataClass(UUID.randomUUID(), gsonString.title, gsonString.date, isSolved = false)
            val addToClaim = ClaimDao().addClaim(claimObj)


            println("HTTP message using POST method with /post ${contType} ${str}")
            call.respondText("POST request was successfully processed. ", status= HttpStatusCode.OK,
                contentType = ContentType.Text.Plain)
        }


    }

}


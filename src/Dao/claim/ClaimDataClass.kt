package org.csuf.cspc411.Dao.claim

import java.util.*

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
//initializing the properties for a claim
data class ClaimDataClass (var id: UUID = UUID.randomUUID(), var title: String?, var date: String?, var isSolved: Boolean = false)

fun main() {

}
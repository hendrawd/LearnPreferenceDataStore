package io.github.hendrawd.learnpreferencedatastore

import java.security.SecureRandom

class DeviceIdTest {

    fun testDeviceId() {
        val random = SecureRandom()
        val randomLong = random.nextLong()
        val newAndroidIdValue = java.lang.Long.toHexString(randomLong)
    val newAndroidIdValueKotlin = randomLong.toString()
    }
}
package com.bridge.androidtechnicaltest.data.remoteDataSource.network

object SampleData {
    fun getRandomPupilLocation(): PupilLocation = dummyPupilLocations.random()
    fun getRandomPupilClass(): String = dummyPupilClass.random()
    fun getRandomGuardianPhoneNumber(): String = dummyGuardianPhoneNumber.random()
    fun getRandomGuardianAddress(): String = dummyGuardianAddress.random()

    private val dummyPupilLocations = arrayListOf(
        PupilLocation(6.5158, 3.3898),
        PupilLocation(6.5168, 3.3891),
        PupilLocation(6.5152, 3.3890),
        PupilLocation(6.5153, 3.3895),
        PupilLocation(6.5150, 3.3892),
        PupilLocation(6.5159, 3.3889),
        PupilLocation(6.5149, 3.3887),
        PupilLocation(6.5155, 3.3897),
        PupilLocation(6.5154, 3.3894),
        PupilLocation(6.5151, 3.3899),
    )

    private val dummyPupilClass = arrayListOf(
        "Grade 1 - Section A",
        "Grade 1 - Section B",
        "Grade 2 - Section A",
        "Grade 2 - Section B",
        "Grade 3 - Section A",
        "Grade 3 - Section B",
        "Grade 4 - Section A",
        "Grade 4 - Section B",
        "Grade 5 - Section A",
        "Grade 5 - Section B",
        "Grade 6 - Section A",
        "Grade 6 - Section B",
        "Grade 7 - Section A",
        "Grade 7 - Section B",
        "Grade 8 - Section A",
        "Grade 8 - Section B",
        "Grade 9 - Section A",
        "Grade 9 - Section B",
        "Grade 10 - Section A",
        "Grade 10 - Section B",
    )

    private val dummyGuardianPhoneNumber = arrayListOf(
        "2349075771869",
        "2348100641875",
        "2348064875296",
        "2349075771869",
        "2348064875296",
        "2348100641875",
    )

    private val dummyGuardianAddress = arrayListOf(
        "No 4, Ilupeju street, Lagos, Nigeria",
        "No 123, New Globe Office, Ikeja, Lagos Nigeria",
        "No 17, Along Unilag, Yaba, Lagos Nigeria",
        "No 4, Ilupeju street, Lagos, Nigeria",
        "No 123, New Globe Office, Ikeja, Lagos Nigeria",
        "No 17, Along Unilag, Yaba, Lagos Nigeria",
    )

    fun getRandomPupilAge(): Int = (3..14).random()
}

data class PupilLocation(val lat: Double, val long: Double)
package com.bridge.androidtechnicaltest.data.remoteDataSource.network

import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction

object SampleData {
    fun getRandomPupilLocation(): PupilLocation = dummyPupilLocations.random()
    fun getRandomPupilClass(): String = dummyPupilClass.random()
    fun getRandomGuardianPhoneNumber(): String = dummyGuardianPhoneNumber.random()
    fun getRandomGuardianAddress(): String = dummyGuardianAddress.random()
    fun getRandomProfileImage(): String = sampleProfilePictures.random()

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

    private val sampleProfilePictures = arrayListOf(
        "https://media.gettyimages.com/id/200204846-001/photo/boy-smiling-looking-away.jpg?s=612x612&w=gi&k=20&c=cNj9vQnJTOvEDb3sh3LgoqDb5ZabtG6HD9l6e0Bn1t0=",
        "https://www.shutterstock.com/image-photo/portrait-real-happy-african-black-600nw-509908525.jpg",
        "https://img.freepik.com/premium-photo/close-up-portrait-african-black-boy-portrait-inside-school-classroom-high-quality-photo_21730-15832.jpg",
        "https://images.unsplash.com/photo-1536337005238-94b997371b40?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YWZyaWNhJTIwc2Nob29sfGVufDB8fDB8fHww",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-l4WoYwhchpgvhKOQwku3DHa3-sJXe9WUGIYWVoGZ_lG0e4dvGISaz0Js4gHp1LHFsvw&usqp=CAU",
        "https://media.gettyimages.com/id/143922541/photo/african-students-at-school.jpg?s=2048x2048&w=gi&k=20&c=r3r8d0XoX7Ti3pL-LHFQtEwXQ6cWh1h_4kNlaWTKIoA="
    )

    fun getRandomPupilAge(): Int = (3..14).random()

    fun getEmptyPupilModel(): PupilModel {
        val location = getRandomPupilLocation()
        return PupilModel(
            "",
            getRandomProfileImage(),
            location.lat,
            location.long,
            "",
            0,
            0,
            getRandomPupilClass(),
            getRandomGuardianPhoneNumber(),
            getRandomGuardianAddress(),
            false,
            RequiredModificationAction.NONE_REQUIRED
        )
    }
}

data class PupilLocation(val lat: Double, val long: Double)
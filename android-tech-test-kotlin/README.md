<h1 align="center">Android Technical Test Submission (Adebayo Oloyede)</h1>

<p align="center">
  <a href="https://www.android.com/"><img alt="Platform" src="https://img.shields.io/badge/Platform-Android-white"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-yellow.svg?style=flat"/></a>
  <a href="https://github.com/abdulwahabhassan/peng/actions"><img alt="Build Status" src="https://github.com/abdulwahabhassan/peng/workflows/Build/badge.svg"/></a> 
</p>

## Preview

|                                                                |                                                    |                                                               |                                                             |
|----------------------------------------------------------------|----------------------------------------------------|---------------------------------------------------------------|-------------------------------------------------------------|
| ![Pupils List](screenshots/pupils_list.jpeg)                   | ![Pupil Details](screenshots/details_page.jpeg)    | ![Delete Record](screenshots/about_to_delete.jpeg)            | ![Create Pupil](screenshots/create_pupil_empty_fields.jpeg) |
| ----------------------------------------------                 | -------------------------------------              | ----------------------------------------                      | -------------------------------------------------           |
| ![Create Pupil Entered Details](screenshots/create_pupil.jpeg) | ![Pupil Details](screenshots/capture_picture.jpeg) | ![Delete Record](screenshots/create_pupil_capture_image.jpeg) | ![Create Pupil](screenshots/processing.jpeg)                |

## Technologies Used
- [Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) to handle all asynchronous network operations and long-running background processes.

- ViewModel - Manages UI-related data-holders to help the UI-components (Fragments and activity) survive configuration changes.

- Navigation Component - Used to handle UI navigations. I made use of single activity architecture with reuseable fragments. Navigation components was used to handle the navigation between the fragments.

- DataBinding - Used to generate binding claesses for XML layouts and also used to bind states to the User interfaces.

- StateFlow and SharedFlow - These are the observable data holder classes used on the application

- Room Database - This was used to handle local caching of the remote data on the application thereby enabling the application to work seamlessly offline.

- DaggerHilt - This is the Dependency injection framework used on the application.

- Paging3 - To handle the lazy loading of data from the server.

- Coil (Coroutine Image Loader) - Used for loading of images on the app.

- [Retrofit2 & OkHttp3]((https://github.com/square/retrofit)) - Used as the REST API clients
- Gson - Used to serialize and deserialize JSON objects
- DSoftCam - This is a camera library developed by my humbleself. It is built untop of [CameraX](https://developer.android.com/media/camera/camerax), [Google Vision]() and [Google MLKit](https://developer.android.com/ai/gemini-nano/ml-kit-genai). This was used to seamlessly handle the capturing of the pupils' profile image.

## App Architecture
This app is built on the MVVM architecture, Clean Architecture and the Repository pattern.

<p align="center">
<a href="https://developer.android.com/topic/architecture/"><img alt="Webpage" src="https://koenig-media.raywenderlich.com/uploads/2020/05/final-architecture-650x488.png"/></a> 
</p>

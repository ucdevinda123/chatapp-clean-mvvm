# Chat App MVVM + Clean Architecture
This  Android application built using MVVM + Clean Architecture architecture approach and is written 100% in Kotlin.
App uses FireBaseRealTime Database
# Architecture behind the app:

Presentation Layer :   UI (Activity,Fragment) and ViewModel
Domain Layer :  UseCases and Abstract repository interface
DI : All the dependency management
Data : RealTime database reference DTO and Repository Implementation
Common : Resource Class


### 1.)Used Libraries and Architecture
- **Architecture**
    * [MVVM  + Clean Architecture](https://developer.android.com/jetpack/guide?gclid=Cj0KCQjwxdSHBhCdARIsAG6zhlVhsDIRhgPzGSshbH7BPyXgzTI9zPLZgxXP5V5ol3KFyCp-gFKZf4oaAgYOEALw_wcB&gclsrc=aw.ds)
    * [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
    * [Jetpack Architecture Guide](https://developer.android.com/jetpack/guide)

- **Dependency Injection**
    * [Hilt](https://dagger.dev/hilt/)

- **Firebase Real Time DB**
    * [FireBase Real Time DB](https://firebase.google.com/products/realtime-database?gclid=CjwKCAjwh5qLBhALEiwAioodsymDBhEOrEu_NVfkcxn0NQamZCjPKKe8wuoGLvk-bmdH2bcH5B-2CRoCrMcQAvD_BwE&gclsrc=aw.ds)


- **Async Communication and data caching**
    * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjwxdSHBhCdARIsAG6zhlVAkTBk3eW_R4YZYvyGqNlX3PFEtQWBY0yjmGj74Flk5ZW6UDnu1V4aAsLeEALw_wcB&gclsrc=aw.ds)
 
- **Navigation**
    * [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)

### 2.)Screens
     Registration : User can enter mobile and name to register then will navigate to login page
     Login : Already joined just need to enter mobile number and log in
     Friends : List Of contacts who already joined
     Chat Room : Click item and navigate to chat room and start chatting




### 3.)Check List

- **Ui Implementation** - Done
- **SOLID implementation** - Done MVVM with Clean Architecture
- **Latest Android Studio** - Done Android Studio Arctic Fox |2020.3.1
- **Development History** - I guess will be able to us local history
- **Unit Test** - No unit tests or integration tests



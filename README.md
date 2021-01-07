# INCONTROL - Spring MVC style controllers for Ktor framework
[![Version](https://img.shields.io/badge/version-indev-green)]()
[![JitPack](https://jitpack.io/v/SkoSC/ktor-incontrol.svg)](https://jitpack.io/#SkoSC/ktor-incontrol)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

## Advantages over default routing
1. You can easily use dependency injection
2. Simpler code without using complicated extensions
3. Cleaner object-oriented code style

## Getting Started
Define your controller
```kotlin
class MyController : Controller {
    
    override val route = "/{user}"
    
    // handler function is required to be public and suspended
    suspend fun handle(@Path user: String) = "Hello $user"
}
```
Add your controller to router
```kotlin
routing {
    get(MyController())
}
```
That's all, you are ready to go

## Handler Functions
### Parameters
There are 4 types of handler parameters supported at he monent
1. Path - parameters are marked with @Path annotation
2. Query - parameters are marked with @Path annotations
3. Body - parameter is either named 'body' or marked with @Body
4. Dependency (non request parameters) - parameters marked with @Dependency

Body and Dependency parameters can be any non-nullable and non-optional type, while
Path and Query can be nullable and/or nullable but support only Int, Double, Boolean and String parameters.

## Advanced Features
### DI container integration
You can inject any object into handler method.
To do this, implement DIContainerWrapper interface and set it in your installation.
```kotlin
install(InControl) {
    diContainer = MyContainer()
}
```
After that you can add @Dependency parameter to handler method, like so:
```kotlin
suspend fun handler(@Dependency myService: MyService) = myService.loadData() 
```

## Roadmap To 1.0
✔️MVC Controllers  
✔️Routing Integration  
✔️Dependency Injection Support  
✔️Optional Values Support  
✔️MVC Controllers  
✔️Extensive @Dependency Annotation Support  
✔️Full Support For Non String Parameters  
❌   Stable Architecture  
❌   High Performance    
❌   Readable Error Coverage    
❌   Test Coverage  
❌   Documentation  

## Future
🚀   Custom Parameter Adapters  
🚀   Out Of Box Integration With Kodein And Koin  
🚀   Ability To 'Hack' Library Internals  
🚀   Autowired Controllers Support

## Contribute
### KDock header format
```kotlin
/**
 * <Your description>
 *
 * @author <your name>
 * @since <current dev version>
 */
```
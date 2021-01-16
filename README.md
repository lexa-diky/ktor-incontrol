# INCONTROL - MVC style controllers for Ktor framework

[![Version](https://img.shields.io/badge/version-indev-green)]()
[![Tests](https://github.com/SkoSC/ktor-incontrol/workflows/Tests/badge.svg)]()
[![JitPack](https://jitpack.io/v/SkoSC/ktor-incontrol.svg)](https://jitpack.io/#SkoSC/ktor-incontrol)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

## Installation

### Gradle

1. Add JitPack repository:

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

2. Add dependency

```groovy
// Core Module for controllers only
implementation 'com.github.SkoSC.k:ktor-incontrol:<latest version>'
// Kodein Integration + Auto Routing
implementation 'com.github.SkoSC.k:ktor-incontrol-kodein:<latest version>'
```

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

Body and Dependency parameters can be any non-nullable and non-optional type, while Path and Query can be nullable
and/or nullable but support only Int, Double, Boolean and String parameters.

## Advanced Features

### DI Container Integration

You can inject any object into handler method. To do this, implement DIContainerWrapper interface and set it in your
installation.

```kotlin
install(InControl) {
    diContainer = MyContainer()
}
```

After that you can add @Dependency parameter to handler method, like so:

```kotlin
suspend fun handler(@Dependency myService: MyService) = myService.loadData() 
```

### Kodein Integration

First, enable integration feature in your installation

```kotlin
install(InControl) {
    enableKodeinIntegration()
}
```

Then declare your kodein dependencies: [Kodein Docs](https://docs.kodein.org/kodein-di/7.2/framework/ktor.html)
Don't forget to import `jxInjectorModule` to enable JSR330 support

```kotlin
di {
    import(jxInjectorModule)
}
```

After that declare your controller and dependencies

```kotlin
class SampleKodeinController @Inject constructor(private val classDependency: ClassDependency) : Controller {

    suspend fun handle(@Dependency parameterDependency: ParameterDependency, @Dependency call: ApplicationCall) = ...
}
```

Last step is to add your `Controller` to routing

```kotlin
routing {
    ...
    get<MySampleKodeinController>()
}
```

That's all you should be ready to go

## Auto Routing

Enable auto routing

```kotlin
install(InControl) {
    enableKodeinIntegration()
    enableAutoRoutedControllers()
}
```

Then mark your controller with `@AutoRouting` annotation

```kotlin
@AutoRouting("GET")
class SampleAutoRoutingController @Inject constructor(private val dependency: SampleDependency): Controller {

    suspend fun handle() = "Hello world: $dependency"
}
```

Finally, add your auto routed controllers to your routing scheme

```kotlin
routing {
    autoRoute("<your root package>")
}
```

## Roadmap To 1.0

✔️MVC Controllers  
✔️Routing Integration  
✔️Autowired Controllers Support  
✔️Dependency Injection Support  
✔️Optional Values Support  
✔️MVC Controllers  
✔️Extensive @Dependency Annotation Support  
✔️Full Support For Non String Parameters  
✔️Out Of Box Integration With Kodein  
❌   Stable Architecture  
❌   High Performance    
❌   Test Coverage  
❌   Documentation  

## Future

🚀   Ability To 'Hack' Library Internals  
🚀   Extensive Feature Configuration

[Kodein Docs]: https://docs.kodein.org/kodein-di/7.2/framework/ktor.html
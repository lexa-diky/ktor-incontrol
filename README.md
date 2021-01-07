# INCONTROL - Spring MVC style controllers for Ktor framework
[![](https://jitpack.io/v/SkoSC/ktor-incontrol.svg)](https://jitpack.io/#SkoSC/ktor-incontrol)

## Advantages over default routing
1. You can easily use dependency injection
2. Simpler code without using complicated extensions
3. Cleaner object-oriented code style

## HowTo
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
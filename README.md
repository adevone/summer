Common: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer/images/download.svg) ](https://bintray.com/summermpp/summer/summer/_latestVersion)
AndroidX: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer-androidx/images/download.svg) ](https://bintray.com/summermpp/summer/summer-androidx/_latestVersion)

# Summer

Gradle dependencies:
```kotlin
// in settings.gradle
enableFeaturePreview("GRADLE_METADATA")

// in root build.gradle
allprojects {
    repositories {
        ...
        maven { url = uri("https://dl.bintray.com/summermpp/summer") }
    }
}

dependencies {

    // library itself
    implementation("com.github.adevone.summer:summer:0.8.12")
    
    // android part containing SummerActivity and SummerFragment
    implementation("com.github.adevone.summer:summer-androidx:0.8.12")
}
```

Summer is Model-View-Presenter library with kotlin-multiplatform support. It can be used to share presenters between iOS and Android apps.  
Summer does not use code generation and thus have not significant effort on compilation time.  
Project aims at standardization and nice IDE support

Example of feature written using Summer:

Common, Kotlin:
```kotlin
class GetDay : UseCase<String, GetDay.Params> {
    
    override suspend fun invoke(): String = when (params.number) {
        1 -> "Monday"
        2 -> "Thursday"
        else -> "Another day"
    } 
    
    data class Params(
        val number: Int
    )
}

object CalendarView {

    interface State {
        var isLoading: Boolean
        var dayName: String
    }
    
    interface Methods {
        fun showError()
    }
}

class CalendarPresenter(
    getDay: GetDay
) : BasePresenter<CalendarView.State, CalendarView.Methods> {
    
    private val defaultDayName = "Monday"
    
    // Proxy that allows to restore state and 
    // set properties even if view does not exist.
    // Summer plugin provides convenient intentions to write it easy
    override fun viewStateProxy = object : CalendarView.State {
    
        // Initial values are automatically emitted to view when it is created.
        // No matter in which state view was. Presenter will change
        // it to consistent state automatically
        override var isLoading by store({ it::isLoading }, initial = true)
        
        // You can get default values from prefs 
        // or presenter constructor params.
        // Any presenter properties can be used as initial values
        // for state properties
        override var dayName by store({ it::dayName }, initial = defaultDayName)
    }
    
    // Called when user sees screen for the first time 
    override fun onEnter() {
        getDayExecutor.execute(GetDay.Params(number = 1))
    }
    
    private val getDayExecutor = getDay.executor(
        interceptor = loadingInterceptor(
            getProperty = { viewStateProxy::isLoading },
            needShow = { viewStateProxy.dayName.isEmpty() }
        ),
        onSuccess = { dayName, _ ->
            viewStateProxy.dayName = dayName        
        },
        // Also you can handle exceptions in presenter onFailure method 
        onFailure = { e ->
            viewMethods?.showError()
            
            // rethrow exception if you need to handle it
            // in presenter onFailure method
            throw e
        }
    )
}



// Base classes and interfaces should be in user project to make it more flexible

typealias UseCase = SummerSource

class BasePresenter<TViewState, TViewMethods> : 
    SummerPresenter<TViewState, TViewMethods>() 
```

Android, Kotlin:
```kotlin
class CalendarFragment : SummerFragment<
    CalendarView.State, 
    CalendarView.Methods, 
    CalendarRouter,
    CalendarPresenter
>(R.layout.calendar_fragment) {

    override fun createPresenter() = CalendarPresenter(...)

    override fun createViewState() = object : CalendarView.State {
    
        override var isLoading: Boolean by didSet {
            progressBar.isVisible = isLoading
        }
        
        override var dayName: String by didSet {
            dayNameView.text = dayName
        }
    }
    
    override var viewMethods = object : CalendarView.Methods {
        
        override fun showError() {
            snackbar("Error occurred")
        }
    }
}
```

iOS, Swift:
```swift
class CalendarViewController: SummerViewController<CalendarPresenter>, CalendarViewState, CalendarViewMethods {

    @IBOutlet weak var loadingSpinner: UIView!
    @IBOutlet weak var dayNameLabel: UILabel!

    override func createPresenter(): CalendarPresenter {
        return CalendarPresenter(...)
    }

    var isLoading = false {
        didSet {
            loadingSpinner.isHidden = !isLoading
        }
    }    
    
    var dayName = "" {
        didSet {
            dayNameLabel.text = dayName
        }
    }
    
    func showError() {
        print("Error occurred!")
    }
}
```

### Convenient custom scope
```
((file[app]:src/main//*||file[app]:src/debug//*||file[app]:src/release//*)&&!*.iml||file[shared_commonMain]:*/||file[buildSrc]:*/||file[shared]:.gitignore||file[eshop]:build.gradle.kts||file[eshop_iosMain]:*/||file[app]:build.gradle.kts)&&!file[buildSrc]:buildSrc.iml||file:.gitignore||file:build.gradle.kts||file:gradle.properties||file:settings.gradle.kts||file:README.md||file[shared_iosMain]:*/||file[app]:src/test/java//*
```

### Feature presentation template 
```kotlin
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#parse("File Header.java")

import summer.example.presentation.base.ScreenPresenter

class ${NAME}Presenter : ScreenPresenter<
        ${NAME}View.State, 
        ${NAME}View.Methods, 
        ${NAME}Router>() {
    
    override val viewStateProxy = object : ${NAME}View.State {

    }
}

object ${NAME}View {

    interface State {
        
    }
    
    interface Methods {
        
    }
}

interface ${NAME}Router {
    
}
```

### Feature fragment template
```kotlin
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#parse("File Header.java")

import summer.example.R
import summer.example.presentation.${NAME}Presenter
import summer.example.presentation.${NAME}Router
import summer.example.presentation.${NAME}View
import summer.example.ui.ScreenFragment

class ${NAME}Fragment : ScreenFragment<
        ${NAME}View.State,
        ${NAME}View.Methods,
        ${NAME}Router,
        ${NAME}Presenter>() {

    override val router = object : ${NAME}Router {

    }

    override fun createViewState() = object : ${NAME}View.State {

    }

    override val viewMethods = object : ${NAME}View.Methods {

    }

    override fun createPresenter() = ${NAME}Presenter()
    
    override val layoutRes = R.layout.TODO()
    
    override val screenToolbar get() = TODO("not implemented")

    override fun initView() {

    }
}
```
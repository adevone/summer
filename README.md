Common: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer/images/download.svg) ](https://bintray.com/summermpp/summer/summer/_latestVersion)
AndroidX: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer-androidx/images/download.svg) ](https://bintray.com/summermpp/summer/summer-androidx/_latestVersion)

# Intellij IDEA / Android Studio plugin
[Plugin page](https://github.com/adevone/summer-plugin)

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

    // library itself (with enableFeaturePreview("GRADLE_METADATA"))
    implementation("com.github.adevone.summer:summer:1.0.0-beta4")

    // library itself (without enableFeaturePreview("GRADLE_METADATA"), jvm version)
    implementation("com.github.adevone.summer:summer-jvm:1.0.0-beta4")

    // androidx support, contains SummerActivity and SummerFragment
    implementation("com.github.adevone.summer:summer-androidx:1.0.0-beta4")

    // android compat support, contains SummerActivity and SummerFragment
    implementation("com.github.adevone.summer:summer-android-support:1.0.0-beta4")
}
```

Summer is Model-View-Presenter library with kotlin-multiplatform support. It can be used to share presenters between iOS and Android apps.  
Summer does not use code generation and thus have not significant effort on compilation time and odd build-time errors.  
Project aims at standardization and nice IDE support

Example of feature written using Summer:

Common, Kotlin:
```kotlin
class GetDay {
    
    operator fun invoke(number: Int): String = when (number) {
        1 -> "Monday"
        2 -> "Tuesday"
        else -> "Another day"
    }
}

interface CalendarView {
    var isLoading: Boolean
    var dayName: String
    val showError: () -> Unit
}

class CalendarPresenter(
    private val getDay: GetDay
) : SummerPresenter<CalendarView> {
    
    private val defaultDayName = "Monday"
    
    // Proxy that allows to restore state and 
    // set properties even if view does not exist.
    // Summer plugin provides convenient intentions to write it easy
    override val viewProxy = object : CalendarView {
    
        // Initial values are automatically emitted to view when it is created.
        // No matter in which state view was. Presenter will change
        // it to consistent state automatically
        override var isLoading by state({ it::isLoading }, initial = true)
        
        // You can get default values from prefs 
        // or presenter constructor params.
        // Any presenter properties can be used as initial values
        // for state properties
        override var dayName by state({ it::dayName }, initial = defaultDayName)
        
        // Events can be delivered to view using various strategies.
        // 4 is present out-of-box. `doOnlyWhenAttached` means that
        // action will be executed only if view attached now.
        // If view is not attached than event wont repeated if
        // this strategy used.
        override val showError = event { it.showError }.doOnlyWhenAttached()
    }
    
    // Called when user sees screen for the first time 
    override fun onEnter() {
        try {
            val day = getDay(number = 1)
            viewProxy.dayName = dayName
        } catch (e: Exception) {
            viewProxy.showError()        
        }
    }
} 
```

Android, Kotlin:
```kotlin
class CalendarFragment : SummerFragment(R.layout.calendar_fragment), CalendarView {

    private val presenter = summerPresenter { CalendarPresenter(...) } 

    override var isLoading: Boolean by didSet {
        progressBar.isVisible = isLoading
    }
    
    override var dayName: String by didSet {
        dayNameView.text = dayName
    }
    
    override val showError = {
        snackbar("Error occurred")
    }
}
```

[BaseController](https://gist.github.com/adevone/685ec7a397b22f47c2171e79ae5c0966) (Subject of change)

iOS, Swift:
```swift
class CalendarViewController: BaseController, CalendarView {

    @IBOutlet weak var loadingSpinner: UIView!
    @IBOutlet weak var dayNameLabel: UILabel!

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
    
    var showError: () -> Void = {
        print("Error occurred!")
    }
    
    private var presenter: CalendarPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = CalendarPresenter(...)
        super.viewDidLoad()
    }
    
}
```

### Convenient custom scope
```
((file[app]:src/main//*||file[app]:src/debug//*||file[app]:src/release//*)&&!*.iml||file[shared_commonMain]:*/||file[buildSrc]:*/||file[shared]:.gitignore||file[eshop]:build.gradle.kts||file[app]:build.gradle.kts)&&!file[buildSrc]:buildSrc.iml||file:.gitignore||file:build.gradle.kts||file:gradle.properties||file:settings.gradle.kts||file:README.md||file[shared_iosMain]:*/||file[app]:src/test/java//*
```
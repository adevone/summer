Common: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer/images/download.svg) ](https://bintray.com/summermpp/summer/summer/_latestVersion)
Arch: [ ![Download](https://api.bintray.com/packages/summermpp/summer/summer-arch-lifecycle/images/download.svg) ](https://bintray.com/summermpp/summer/summer-arch-lifecycle/_latestVersion)

# About Summer

Summer is Model-View-ViewModel library with kotlin-multiplatform support. It can be used to share viewModels between iOS and Android apps.
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

class CalendarViewModel(
    private val getDay: GetDay
) : SummerViewModel<CalendarView> {
    
    private val defaultDayName = "Monday"
    
    // Proxy that allows to restore state and 
    // set properties even if view does not exist.
    // Summer plugin provides convenient intentions to write it easy
    override val viewProxy = object : CalendarView {
    
        // Initial values are automatically emitted to view when it is created.
        // No matter in which state view was. ViewModel will change
        // it to consistent state automatically
        override var isLoading by state({ it::isLoading }, initial = true)
        
        // You can get default values from prefs 
        // or viewModel constructor params.
        // Any viewModel properties can be used as initial values
        // for state properties
        override var dayName by state({ it::dayName }, initial = defaultDayName)
        
        // Events can be delivered to view using various strategies.
        // 4 is present out-of-box. `doOnlyWhenAttached` means that
        // action will be executed only if view attached now.
        // If view is not attached than event wont repeated if
        // this strategy used.
        override val showError = event { it.showError }.doOnlyWhenAttached()
    }
    
    init {
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
class CalendarFragment : Fragment(R.layout.calendar_fragment), CalendarView {

    private val viewModel by viewModels<CalendarViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bindView { this }
    }

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

[BaseViewController](https://gist.github.com/adevone/994d5cd5a5cb11f6789dbe3732bb6b25) (Subject of change)

iOS, Swift:
```swift
class CalendarViewController: BaseViewController, CalendarView {

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
    
    private var viewModel: CalendarViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = CalendarViewModel(...)
        super.viewDidLoad()
    }
}
```

# Intellij IDEA / Android Studio plugin
[Plugin page](https://github.com/adevone/summer-plugin)

# Gradle dependencies:
```kotlin
// in root build.gradle
allprojects {
    repositories {
        ...
        maven { url = uri("https://dl.bintray.com/summermpp/summer") }
    }
}

dependencies {

    // library itself
    implementation("com.github.adevone.summer:summer:1.0.0-beta6-mvvm-20")

    // androidx support, contains ArchViewModel that allows using of bindView function (see example)
    implementation("com.github.adevone.summer:summer-arch-lifecycle:1.0.0-beta6-mvvm-20")

    // android compat support, contains SummerActivity and SummerFragment
    implementation("com.github.adevone.summer:summer-android-support:1.0.0-beta6-mvvm-20")
}
```
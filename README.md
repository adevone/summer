Common: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.adevone.summer/summer/badge.svg?gav=true)](https://maven-badges.herokuapp.com/maven-central/com.github.adevone.summer/summer)
Arch: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.adevone.summer/summer-arch-lifecycle/badge.svg?gav=true)](https://maven-badges.herokuapp.com/maven-central/com.github.adevone.summer/summer-arch-lifecycle)

# About Summer

Summer is a presentation level library with kotlin-multiplatform support. It can be used to share viewModels between iOS, Android and Web apps.  
Summer does not use code generation and thus have not significant effort on compilation time and odd build-time errors.  
Project aims to have out-of-box support of Android Framework, Jetpack Compose, UIKit and SwiftUI without any adapters and platform-specific limitations.  

Example of feature written using Summer:

Common, Kotlin Multiplatform:
```kotlin
class GetNews {
    
    operator fun invoke(offset: Int): String = when (offset) {
        0 -> "Today news"
        1 -> "Yesterday news"
        else -> throw NotAuthorizedException("Please login to read elder news")
    }
}

interface NewsView {
    var isLoading: Boolean
    var news: String
    val toAuth: () -> Unit
}

class NewsViewModel(
    getNews: GetNews
) : ArchViewModel<NewsView>() {
    
    private val defaultNews = "Loading news..."
    
    // Proxy that allows to manipulate view state and events 
    // even if view does not exist now.
    // Summer IDE plugin provides convenient intentions to write it easy.
    override val viewProxy = object : NewsView {
    
        // Initial values are automatically emitted to view when it created.
        // No matter in which state view was. ViewModel will change
        // it to consistent state automatically.
        override var isLoading by state({ it::isLoading }, initial = true)
        
        // You can get default values from prefs 
        // or viewModel constructor params.
        // Any viewModel properties available on init phase
        // can be used as initial values for state properties
        override var news by state({ it::news }, initial = defaultNews)
        
        // Events can be delivered to view using various strategies.
        // 4 is present out-of-box. `doExactlyOnce` means that
        // each invocation of event in viewProxy will lead
        // exactly one performance of the event on view.
        // It is useful for navigation. Also Summer allows to
        // implement navigation logic in a separate entity 
        // and not mix presentation and navigation logic. 
        override val toAuth = event { it.toAuth }.perform.exactlyOnce()
    }
    
    init {
        try {
            val news = getNews(offset = 1)
            viewProxy.news = news
        } catch (e: NotAuthorizedException) {
            viewProxy.toAuth()        
        }
    }
} 
```

Android Framework, Kotlin:
```kotlin
class NewsFragment : Fragment(R.layout.news_fragment), NewsView {

    private val viewModel by viewModels<NewsViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bindView { this }
    }

    override var isLoading: Boolean by didSet {
        progressBar.isVisible = isLoading
    }
    
    override var news: String by didSet {
        newsView.text = news
    }
    
    override val toAuth: () -> Unit = {
        // navigate to auth
    }
}
```

[BaseViewController](https://gist.github.com/adevone/994d5cd5a5cb11f6789dbe3732bb6b25) (Subject of change)

UIKit, Swift:
```swift
class NewsViewController: BaseViewController, NewsView {

    @IBOutlet weak var loadingSpinner: UIView!
    @IBOutlet weak var newsLabel: UILabel!

    var isLoading = false {
        didSet {
            loadingSpinner.isHidden = !isLoading
        }
    }    
    
    var news = "" {
        didSet {
            newsLabel.text = news
        }
    }
    
    var toAuth: () -> Void = {
        // navigate to auth
    }
    
    private var viewModel: NewsViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = NewsViewModel(...)
        super.viewDidLoad()
    }
}
```

Jetpack Compose, Kotlin:
```kotlin
@Composable
fun NewsUI() {
    val viewModel = viewModel<NewsViewModel>()
    val view = viewModel.bind(object : NewsView {
        override var isLoading: Boolean by remember { mutableStateOf(false) }
        override var news: String by remember { mutableStateOf("") }
        override val toAuth: () -> Unit = {
            // navigate to auth
        }
    })
    if (!view.isLoading) {
        Text(view.news)
    } else {
        CircularProgressIndicator()
    }
}
```

SwiftUI, Swift:
```swift
class NewsViewState: BaseState, NewsView {
    @Published var isLoading: Bool = false
    @Published var news: String = ""
    lazy var toAuth: () -> Void = {
        // navigate to auth
    }
}

struct NewsUI: View {

    @ObservedObject var state = NewsViewState()
    var viewModel = NewsViewState()
    init() {
        state.bind(viewModel)
    }

    var body: some View {
        VStack {
            if !state.isLoading {
                Text(state.news)
            } else {
                ProgressView()
            }
        }
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
        // ...
        mavenCentral()
    }
}

dependencies {

    // library itself
    implementation("com.github.adevone.summer:summer:1.0.0-beta11")

    // contains ArchViewModel that allows using of bindView function on Android (see example)
    implementation("com.github.adevone.summer:summer-arch-lifecycle:1.0.0-beta11")

    // based on old support lib of 28.0.0 version, contains SummerActivity and SummerFragment
    implementation("com.github.adevone.summer:summer-android-support:1.0.0-beta11")
}
```
# Summer

Summer is Model-View-Presenter library with kotlin-multiplatform support. It can be used to share presenters between iOS and Android apps.  
Summer does not use code generation and thus have not significant effort on compilation time.  
Project targets to standardization and nice IDE support

Example of feature written using Summer:

Common, Kotlin:
```
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

interface CalendarRouter {

}

class CalendarPresenter(
    private val getDay: GetDay
) : BasePresenter<
    CalendarView.State, 
    CalendarView.Methods, 
    CalendarRouter> {
    
    private val defaultDayName = "Loading..."
    
    // Proxy that allows to restore state and 
    // set properties even if view does not exists.
    // Summer plugin provides convenient intentions to write it easy
    override fun createViewStateProxy() = object : {
    
        // Initial values are automatically emitted to view when it is created.
        // No matter in which state view was. Presenter will change
        // it to consistent state automatically
        override var isLoading by store(vs::isLoading, initialValue = true)
        
        // You can get default values from prefs 
        // or presenter constructor params.
        // Any presenter properties can be used as initial values
        // for state properties
        override var dayName by store(vs::dayName, initialValue = "Loading...")
    }
    
    // Called when user sees screen first times 
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

class BasePresenter<TViewState, TViewMethods, TRouter> : 
    SummerPresenter<TViewState, TViewMethods, TRouter>(
        localStore = InMemoryStore(),
        workContext = Dispatchers.IO,
        uiContext = Dispatchers.Main,
        loggerFactory = <...>
    ) 
```

Android, Kotlin:
```
class CalendarFragment : SummerFragment<
    CalendarView.State, 
    CalendarView.Methods, 
    CalendarRouter,
    CalendarPresenter
>(R.layout.calendar_fragment) {

    override fun createPresenter() = CalendarPresenter(...)

    override var router = object : CalendarRouter {
        
    }

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
```
class CalendarViewController: SummerViewController<CalendarPresenter>, CalendarViewState, CalendarViewMethods, CalendarRouter {

    @IBOutlet weak var loadingSpinner: UISpinner!
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

### Содержание
1) Android и iOS приложения с общим кодом
2) Схема Clean Architecture (VIPER) на summer
3) Пример работы с данными
4) Тесты на бизнес-логику уровня приложения
5) Тест на презентационную бизнес-логику

### Convenient custom scope
```
((file[app]:src/main//*||file[app]:src/debug//*||file[app]:src/release//*)&&!*.iml||file[shared_commonMain]:*/||file[buildSrc]:*/||file[shared]:.gitignore||file[eshop]:build.gradle.kts||file[eshop_iosMain]:*/||file[app]:build.gradle.kts)&&!file[buildSrc]:buildSrc.iml||file:.gitignore||file:build.gradle.kts||file:gradle.properties||file:settings.gradle.kts||file:README.md||file[shared_iosMain]:*/||file[app]:src/test/java//*
```

### Feature presentation template 
```
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#parse("File Header.java")

import summer.log.KLogging
import summer.example.presentation.base.ScreenPresenter

class ${NAME}Presenter : ScreenPresenter<
        ${NAME}View.State, 
        ${NAME}View.Methods, 
        ${NAME}View.InitialState, 
        ${NAME}Router>() {
        
    override val viewInitialState = ${NAME}View.InitialState(
        
    )
    
    override fun createViewStateProxy(vs: ${NAME}View.State) = object : ${NAME}View.State {

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
```
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#parse("File Header.java")

import ru.napoleonit.example.R
import ru.napoleonit.example.presentation.${NAME}Presenter
import ru.napoleonit.example.presentation.${NAME}Router
import ru.napoleonit.example.presentation.${NAME}View
import ru.napoleonit.example.ui.ScreenFragment

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

        override 
    }

    override fun createPresenter() = ${NAME}Presenter()
    
    override val layoutRes = R.layout.TODO()
    
    override val screenToolbar get() = TODO("not implemented")

    override fun initView() {

    }
}
```
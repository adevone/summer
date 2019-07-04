# Summer

### Содержание
1) Android и iOS приложения с общим кодом
2) Схема Clean Architecture (VIPER) на summer
3) Пример работы с данными
4) Тесты на бизнес-логику уровня приложения
5) Тест на презентационную бизнес-логику

### Инструкция
1) Скопируйте содержание `gradle.properties.example` в файл `gradle.properties`
2) Допишите в `gradle.properties` ваши доступы к maven
2) Откройте проект с помощью Android Studio

### Удобный custom scope
```
((file[app]:src/main//*||file[app]:src/debug//*||file[app]:src/release//*)&&!*.iml||file[shared_commonMain]:*/||file[buildSrc]:*/||file[shared]:.gitignore||file[eshop]:build.gradle.kts||file[eshop_iosMain]:*/||file[app]:build.gradle.kts)&&!file[buildSrc]:buildSrc.iml||file:.gitignore||file:build.gradle.kts||file:gradle.properties||file:settings.gradle.kts||file:README.md||file[shared_iosMain]:*/||file[app]:src/test/java//*
```

### Шаблон для feature presentation 
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

### Шаблон для feature fragment
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

    }

    override fun createPresenter() = ${NAME}Presenter()
    
    override val layoutRes = R.layout.TODO()
    
    override val screenToolbar get() = TODO("not implemented")

    override fun initView() {

    }
}
```
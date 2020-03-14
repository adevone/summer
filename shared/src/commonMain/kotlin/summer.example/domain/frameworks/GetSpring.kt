package summer.example.domain.frameworks

import summer.example.entity.Framework

class GetSpring {

    operator fun invoke(version: String): Framework {
        return Framework(
            name = "Spring",
            version = version
        )
    }
}
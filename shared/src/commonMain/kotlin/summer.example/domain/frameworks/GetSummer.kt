package summer.example.domain.frameworks

import summer.example.entity.Framework

class GetSummer {

    operator fun invoke(): Framework {
        return Framework(
            name = "Summer",
            version = "0.8.17"
        )
    }
}
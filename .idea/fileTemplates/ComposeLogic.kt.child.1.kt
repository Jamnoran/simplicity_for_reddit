package ${PACKAGE_NAME}

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput

class ${NAME}Input(val inputData: String = "") : BaseInput

data class Data(val data: String){
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}

package ${PACKAGE_NAME}

data class {$NAME}Input(
    val inputData: String
)

data class Data(val data: String){
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}

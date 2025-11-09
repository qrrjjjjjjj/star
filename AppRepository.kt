// AppRepository.kt - Log failure when loading icon/label
class AppRepository {
    fun loadIconAndLabel(id: String) {
        try {
            // Icon label loading logic
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to load icon/label: ", e)
        }
    }
}
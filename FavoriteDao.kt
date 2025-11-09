// FavoriteDao.kt - Replace delimiter and improve fallback for corrupt/empty
class FavoriteDao {
    fun getFavorites(): List<Favorite> {
        // Use new delimiter
        // Improve handling for corrupt or empty favorite lists
        return favoritesList.split(newDelimiter).map { Favorite(it) }
            .filter { it.isValid() }
    }
}
// StarHomeScreen.kt - Use collectAsState for DataStore, add permission denial handling
@Composable
fun StarHomeScreen() {
    val permissionsState = remember { mutableStateOf(true) } // Placeholder for actual permission logic
    if (!permissionsState.value) {
        // Disable button and show permission denial info
    }
}
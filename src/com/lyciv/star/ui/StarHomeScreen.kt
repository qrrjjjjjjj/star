package com.lyciv.star.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lyciv.star.data.AppRepository
import com.lyciv.star.data.FavoriteDao
import com.lyciv.star.ui.components.AppList
import com.lyciv.star.ui.components.ClockPanel
import com.lyciv.star.ui.components.FavoriteBar
import com.lyciv.star.ui.components.SearchBar
import com.lyciv.star.ui.theme.StarLauncherTheme
import com.lyciv.star.utils.WallpaperUtils
import kotlinx.coroutines.launch

@Composable
fun StarHomeScreen() {
    StarLauncherTheme {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val repository = remember { AppRepository(context) }
        val favoriteDao = remember { FavoriteDao(context) }

        var wallpaperUri by remember { mutableStateOf<String?>(null) }
        var favoritePackages by remember { mutableStateOf<List<String>>(emptyList()) }
        var showAppList by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        var allApps by remember { mutableStateOf<List<com.lyciv.star.data.AppModel>>(emptyList()) }

        val wallpaperLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                scope.launch {
                    favoriteDao.saveWallpaperUri(it.toString())
                }
            }
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                wallpaperLauncher.launch("image/*")
            }
        }

        LaunchedEffect(Unit) {
            favoriteDao.getWallpaperUri().collect {
                wallpaperUri = it
            }
        }

        LaunchedEffect(Unit) {
            favoriteDao.getFavorites().collect {
                favoritePackages = it
            }
        }

        LaunchedEffect(Unit) {
            allApps = repository.getAllApps()
        }

        val wallpaperBitmap = remember(wallpaperUri) {
            WallpaperUtils.loadWallpaper(context, wallpaperUri)
        }

        val favoriteApps = remember(favoritePackages, allApps) {
            favoritePackages.mapNotNull { pkg ->
                repository.getAppByPackage(pkg)
            }
        }

        val filteredApps = remember(allApps, searchQuery) {
            if (searchQuery.isBlank()) {
                allApps
            } else {
                allApps.filter { it.label.contains(searchQuery, ignoreCase = true) }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            wallpaperBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Wallpaper",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                ClockPanel(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.weight(1f))

                if (showAppList) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    ) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        AppList(
                            apps = filteredApps,
                            onAppClick = { pkg ->
                                repository.launchApp(pkg)
                                showAppList = false
                            }
                        )
                    }
                } else {
                    FavoriteBar(
                        favorites = favoriteApps,
                        onAppClick = { pkg -> repository.launchApp(pkg) },
                        onAddClick = { showAppList = true }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

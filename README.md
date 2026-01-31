# 🎌 Anime Explorer App

Anime Explorer is an Android application that fetches and displays popular and top-rated anime using the **Jikan API**.  
The app is built with a clean **MVVM architecture**, supports **offline usage**, and handles network and data errors gracefully.

---

## 📱 App Overview

The app allows users to:
- Browse top-rated anime
- View detailed anime information
- Watch anime trailers (if available)
- Use the app offline with automatic data syncing

---

## 🎯 Objective

To build a simple yet robust Android application that:
- Fetches anime data from a public API
- Displays anime lists and detailed views
- Works seamlessly in offline mode
- Follows modern Android development best practices

---

## 🔗 APIs Used

### Top Anime List
GET https://api.jikan.moe/v4/top/anime

### Anime Details
GET https://api.jikan.moe/v4/anime/{anime_id}

---

## ✨ Features Implemented

### 1️⃣ Anime List Screen (Home)
- Fetches top-rated anime from the Jikan API
- Displays:
  - Title
  - Number of Episodes
  - MyAnimeList Rating
  - Poster Image

---

### 2️⃣ Anime Detail Screen
- Opens on anime selection
- Displays:
  - Trailer video player (if available)
  - Poster image fallback if trailer is unavailable
  - Title
  - Plot / Synopsis
  - Genres
  - Main Cast
  - Total Episodes
  - Rating

---

### 3️⃣ Local Database (Room)
- Anime data is cached locally using Room
- Enables offline access to previously fetched data
- Reduces unnecessary network calls

---

### 4️⃣ Offline Mode & Data Sync
- App remains functional without internet
- Loads data from local database when offline
- Automatically syncs with server when network is restored

---

### 5️⃣ Error Handling
Gracefully handles:
- API failures
- Network connectivity issues
- Database read/write errors
- Empty or missing API fields

User-friendly error states are shown in the UI.

---

### 6️⃣ Design Constraint Handling
- If poster or profile images cannot be displayed due to legal or policy changes:
  - UI adapts without breaking layout
  - Placeholder UI is shown instead
- Ensures flexibility and future-proof UI design

---

## 🏗 Architecture

The app follows **MVVM (Model–View–ViewModel)** architecture:

UI (Activity / Fragment / Compose)
↓
ViewModel (LiveData / StateFlow)
↓
Repository
↓
Data Sources
├─ Remote (Retrofit)
└─ Local (Room)

### Benefits
- Clear separation of concerns
- Lifecycle-aware components
- Easy scalability and testability

---

## 🛠 Tech Stack & Libraries

- **Kotlin**
- **MVVM Architecture**
- **Retrofit** – API calls
- **Room** – Local database
- **Glide / Picasso** – Image loading
- **LiveData / StateFlow** – Reactive state handling
- **Coroutines** – Asynchronous operations

---

## ⚠️ Assumptions Made

- Some anime may not have trailers available
- Cast information may be partial
- Offline mode prioritizes cached data
- API rate limits are handled implicitly by Jikan

---

## 🚧 Known Limitations

- Pagination is not implemented
- Limited retry mechanism for failed API calls
- Trailer playback depends on API availability
- UI tested mainly on standard device sizes

---

## 🚀 Future Improvements

- Pagination for anime list
- Search and filter functionality
- Favorites feature
- Improved error retry strategy
- Unit and UI testing
- Dark mode enhancements

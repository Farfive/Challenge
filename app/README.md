Pixabay Image Search App
This is an Android application that allows users to search for images using the Pixabay API and display the results in a list format.
The app is written in Kotlin and utilizes various libraries and tools to provide a seamless user experience.

Features:
Search for images using keywords.
Display a list of search results with thumbnails, usernames, and tags.
Offline caching of search results for offline handling.
Clicking on a list item opens a detail screen with a larger image, user information, and image details.
Pagination for loading more search results.
Utilizes Kotlin Coroutines for asynchronous programming.
Dependency injection with Koin for managing dependencies.
Uses Android Material Design components for a modern and visually appealing UI.
Implements Android Architecture Components (ViewModel, LiveData) for efficient data handling.
Instrumented unit testing with Espresso to ensure app functionality.
Libraries and Tools Used
Kotlin: The primary programming language used for development.
Kotlin Coroutines: For asynchronous programming and managing background tasks.
Koin: For dependency injection and managing app dependencies.
Paging: For implementing pagination in the image search results.
Coil: For image loading and caching.
Android Material Design: Provides UI components and guidelines for a modern and consistent look and feel.
Android Architecture Components: Including ViewModel and LiveData for efficient data management and lifecycle handling.
Instrumented Unit Testing: Using Espresso to write UI tests for app functionality.
Getting Started
To get started with the project, follow these steps:

Clone the repository.
Open the project in Android Studio.
Build and run the project on an emulator or physical device.
The app will start and automatically trigger a search for the keyword "fruits" to display relevant images.
Feel free to explore the codebase, modify it according to your needs, and contribute to the project by submitting pull requests.


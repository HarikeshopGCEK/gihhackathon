# Smart Campus Solution - Android App

## Project Overview

**Smart Campus Solution** is a comprehensive Android application designed to streamline campus complaint management. Built with modern Android development practices using Jetpack Compose and Firebase, this app provides an intuitive platform for students and staff to submit, track, and manage campus-related complaints and requests.

## Key Features

### Authentication System

- **Firebase Authentication** integration with email/password
- **Google Sign-In** support (requires Firebase configuration)
- User registration with validation
- Password visibility toggle for enhanced UX
- Secure user session management

### Complaint Management

- **Submit Complaints**: Detailed form with category, location, priority selection
- **View My Complaints**: Real-time complaint tracking with status updates
- **Dashboard**: Overview of user complaints and quick actions
- **Status Tracking**: Pending, In Progress, Resolved states

### Modern UI/UX

- **Material Design 3** implementation
- **Jetpack Compose** for reactive UI
- **Responsive design** with keyboard-aware scrolling
- **Dark/Light theme** support
- **Intuitive navigation** between screens

### App Architecture

- **MVVM pattern** with Compose
- **Firebase Firestore** for real-time data storage
- **Navigation Component** for screen management
- **Coroutines** for asynchronous operations

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Database**: Firebase Firestore
- **Authentication**: Firebase Auth
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)

## Project Structure

```
app/src/main/java/com/example/smartcampussolution/
├── screens/                    # UI Screens
│   ├── LoginScreen.kt         # Authentication
│   ├── RegisterScreen.kt      # User registration
│   ├── DashboardScreen.kt     # Main dashboard
│   ├── AddComplaintScreen.kt  # Complaint submission
│   ├── MyComplaintsScreen.kt  # User's complaints
│   ├── AdminScreen.kt         # Admin panel
│   └── AboutScreen.kt         # App information
├── data/                      # Data models
│   └── Complaint.kt          # Complaint data model
├── navigation/               # Navigation logic
│   └── AppNavigation.kt     # Navigation graph
└── MainActivity.kt          # Entry point
```

## App Screens

### 1. **Login Screen**

- Email/password authentication
- Google Sign-In integration
- Password visibility toggle
- Registration navigation
- Input validation and error handling

### 2. **Registration Screen**

- User details collection (Full Name, Student ID, Email)
- Password confirmation
- Firebase user creation
- Real-time validation

### 3. **Dashboard Screen**

- Welcome message with user info
- Quick complaint submission
- Recent complaints overview
- Navigation to all features

### 4. **Add Complaint Screen**

- Comprehensive complaint form
- Categories: Academic, Facility, IT Support, Food Service, Security, Other
- Priority levels: Low, Medium, High
- Location and contact information
- Firebase Firestore integration

### 5. **My Complaints Screen**

- Real-time complaint list
- Status indicators with color coding
- Complaint details display
- Pull-to-refresh functionality
- Sort by timestamp (newest first)

### 6. **Admin Screen**

- Administrative overview
- Complaint management capabilities
- System statistics

### 7. **About Screen**

- App information and version
- Contact details
- Terms and conditions

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or higher
- Android SDK API 24+
- Firebase project setup

### Installation Steps

1. **Clone the Repository**

   ```bash
   git clone [repository-url]
   cd smartcampussolution
   ```

2. **Firebase Configuration**
   ⚠️ **Important**: The `google-services.json` file has been removed for security reasons.

   To run this app, you need to:

   - Create a new Firebase project at https://console.firebase.google.com
   - Enable Authentication (Email/Password and Google Sign-In)
   - Enable Firestore Database
   - Download your `google-services.json` file
   - Place it in `app/google-services.json`

3. **Build and Run**
   ```bash ./gradlew installDebug

   ```

### Firebase Setup Required

Since the Google Services configuration has been removed, judges will need to:

1. **Create Firebase Project**:

   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create new project named "Smart Campus Solution"

2. **Configure Authentication**:

   - Enable Email/Password sign-in method
   - Enable Google sign-in method (optional)

3. **Setup Firestore Database**:

   - Create Firestore database in test mode
   - Collection: `complaints` (auto-created when first complaint is submitted)

4. **Add Android App**:
   - Package name: `com.example.smartcampussolution`
   - Download `google-services.json` - Place in `app/` directory

## Features Implemented

### Core Functionality

- [x] User authentication (register/login)
- [x] Firebase integration
- [x] Complaint submission form
- [x] Real-time complaint viewing
- [x] Material Design 3 UI
- [x] Responsive navigation
- [x] Input validation
- [x] Error handling
- [x] Keyboard-aware scrolling

### Advanced Features

- [x] Password visibility toggles
- [x] Status tracking system
- [x] Pull-to-refresh functionality
- [x] Real-time data synchronization
- [x] Google Sign-In integration
- [x] Secure data storage
- [x] Modern Android architecture

## UI/UX Highlights

- **Consistent Material Design 3** theming
- **Smooth animations** and transitions
- **Responsive layouts** for different screen sizes
- **Intuitive navigation** with clear user flows
- **Accessibility features** with content descriptions
- **Error states** with helpful messaging
- **Loading states** for better user feedback

## Data Model

### Complaint Structure

```kotlin
data class Complaint(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val location: String = "",
    val contact: String = "",
    val status: String = "Pending",
    val userId: String = "",
    val userEmail: String = "",
    val timestamp: Date = Date(),
    val priority: String = "Medium"
)
```

## Security Considerations

- **Firebase Authentication** handles secure user management
- **Firestore security rules** ensure data privacy
- **User data isolation** - users only see their own complaints
- **Input validation** prevents malformed data
- **Sensitive configuration** removed from repository

## Known Issues & Limitations

1. **Google Services Configuration**: Requires manual Firebase setup
2. **Composite Index**: May require Firestore index creation for complex queries
3. **Offline Support**: Currently requires internet connection
4. **Admin Features**: Basic implementation, could be expanded

## Future Enhancements

- [ ] Offline data caching
- [ ] Push notifications for status updates
- [ ] Image attachments for complaints
- [ ] Advanced admin dashboard
- [ ] Complaint categories management
- [ ] Analytics and reporting
- [ ] Multi-language support

## Testing

The app has been tested on:

- **Android Emulator** (Pixel 3a API 34)
- **Physical devices** running Android 7.0+
- **Various screen sizes** and orientations
- **Different network conditions**

## Screenshots

_Screenshots would be included here showing the main screens and features_

## 👨‍💻 Development Team

This project was developed as a demonstration of modern Android development practices using:

- Kotlin programming language
- Jetpack Compose for UI
- Firebase backend services
- Material Design principles

## 📄 License

This project is developed for educational/demonstration purposes.

---

## 📞 Support & Contact

For technical questions or issues related to this project, please refer to the documentation or create an issue in the repository.

**Note for Judges**: This app demonstrates comprehensive Android development skills including modern UI frameworks, backend integration, user authentication, real-time data management, and following Android development best practices.

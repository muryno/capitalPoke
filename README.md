# Capital App

A modern Android application built with Jetpack Compose that demonstrates clean architecture, modern Android development practices, and proper testing approaches.

## Project Structure


## Architecture

The project follows Clean Architecture principles with MVVM pattern:

- **UI Layer**: Composable functions and ViewModels
- **Domain Layer**: Business logic and use cases
- **Data Layer**: Repository implementation and data sources

### Key Components

- **UI Layer**
  - Jetpack Compose for UI
  - ViewModels for state management
  - UI state modeling for different screens

- **Domain Layer**
  - Use cases for business logic
  - Repository interfaces
  - Domain models

- **Data Layer**
  - Repository implementations
  - API service interfaces
  - Data models and mappers

## Testing

The project includes comprehensive testing:

- **Unit Tests**: Testing individual components
  - ViewModel tests
  - UseCase tests
  - Repository tests
  - Mapper tests

- **UI Tests**: Testing UI components
  - Compose UI tests
  - Screen navigation tests
  - User interaction tests

## Technologies Used

- **UI**
  - Jetpack Compose
  - Material Design 3
  - Coil for image loading

- **Architecture**
  - MVVM Architecture
  - Clean Architecture
  - Dependency Injection with Hilt

- **Testing**
  - JUnit
  - MockK
  - Compose UI Testing
  - Hilt Testing

- **Networking**
  - Retrofit
  - OkHttp
  - Gson

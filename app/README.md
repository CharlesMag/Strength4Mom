ViewModel
↓
ExerciseRepository (interface) — decouples ViewModel from implementation
↓
ExerciseRepositoryImpl — implements interface, contains logic
↓
ExerciseService (Retrofit interface) — defines network calls
↓
Retrofit — makes actual HTTP request
↓
Server

------------------------------------

[ UI / ViewModel Layer ]
↓
┌─────────────────────────────────────────────┐
│               SearchViewModel               │
│ ┌─────────────────────────────────────────┐ │
│ │ Needs on:                               │ │
│ │   - ExerciseRepository (interface)      │ │
│ │   - CoroutineDispatcher (IO)            │ │
│ └─────────────────────────────────────────┘ │
└─────────────────────────────────────────────┘
↓
↓ Koin injects
↓
┌─────────────────────────────────────────────┐
│         ExerciseRepositoryImpl              │  ← implements ExerciseRepository
│ ┌─────────────────────────────────────────┐ │
│ │ Depends on:                             │ │
│ │   - ExerciseService (Retrofit interface)│ │
│ └─────────────────────────────────────────┘ │
└─────────────────────────────────────────────┘
↓
↓ Koin injects
↓
┌─────────────────────────────────────────────┐
│               ExerciseService               │  ← Retrofit creates this
│ @GET("exercises")                           │
│ suspend fun fetchExercises(...)             │
└─────────────────────────────────────────────┘
↓
↓ Retrofit makes real network request
↓
🌍 External API


---------------------------------

For a more detailed flow with the DI if I start from the SearchViewModel

+---------------------+
|   UI Layer (Jetpack|
|   Compose / XML)    |
+---------------------+
|
v
+---------------------+
|  SearchViewModel    |  <-- Injected with:
|---------------------|      - ExerciseRepository
| - loadExerciseList()|      - CoroutineDispatcher
+---------------------+
|
v
+-------------------------------+
| ExerciseRepository (interface)|  <-- Abstraction
+-------------------------------+
|
v
+-------------------------------------+
| ExerciseRepositoryImpl              |  <-- Implements interface
|-------------------------------------|
| - Depends on: ExerciseService       |
| - Handles exceptions, wraps result |
+-------------------------------------+
|
v
+----------------------------+
| ExerciseService (interface)|  <-- Retrofit interface
| - fetchExercises(...)      |  <-- @GET endpoint
+----------------------------+
|
v
+--------------------+
| Retrofit HTTP Call |  <-- Makes actual network request
+--------------------+
|
v
+-----------------+
|  Remote API     |
+-----------------+



ExerciseRepository (interface): Allows your ViewModel to be decoupled from implementation.

ExerciseRepositoryImpl: Handles real data-fetching logic using Retrofit.

ExerciseService: Retrofit interface that defines API endpoints.

Koin: Injects dependencies based on bindings in your module.

Dispatcher: Ensures network work runs on background threads.
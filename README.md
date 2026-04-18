The Android core of CRACK 11 is built using Java, focusing on high-performance data processing and reliable background services. This ensures that the fantasy gaming experience remains fluid, even during high-traffic live match windows.

Key Java Modules
Entity Sports Integration: Custom Java classes handle the asynchronous fetching and parsing of match fixtures, player statistics, and live scoreboards.

Team Selection Engine: Logic implemented in Java to validate team compositions (e.g., ensuring 11 players are selected within the 100-credit limit).

Context & State Management: Utilizes Java-based ViewModels and LiveData to sync team selection states across different app components.

Network Layer: Leverages Retrofit and OkHttp in Java for resilient API communication and custom interceptors for authentication with Entity Sports.

Core Android Components used:
Activities & Fragments: Managed via Java for modular UI components.

Services: Background Java services for real-time "Match Started" or "Team Update" notifications.

Broadcast Receivers: To monitor network changes and ensure the live scoreboard stays updated.

🧩 Technical Architecture
The project follows the MVVM (Model-View-ViewModel) pattern implemented in Java to separate business logic from the UI:

Model: Java POJOs representing Entity Sports data (Players, Teams, Matches).

View: XML layouts coupled with Java Activities/Fragments.

ViewModel: Handles the logic for joining contests and calculating real-time fantasy points.

📂 Java-Specific Directory Structure
Plaintext
├── android/app/src/main/java/com/crack11/
│   ├── api/                # Entity Sports API interfaces
│   ├── models/             # Player and Team Java objects
│   ├── viewmodels/         # Team selection & contest logic
│   ├── ui/                 # Activities and Adapters for RecyclerViews
│   └── utils/              # Calculation helpers for fantasy points
🛠 Tools & Libraries
Build Tool: Gradle

JSON Parsing: GSON / Jackson

Image Loading: Glide (for player and team logos)

Concurrency: Java Threads and Handlers for smooth UI performance

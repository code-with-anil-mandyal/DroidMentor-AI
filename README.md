# DroidMentor AI 🤖

**DroidMentor AI** is a modern Android AI chat application that allows users to interact with an AI assistant for coding help, explanations, and technical discussions.  
The app demonstrates clean architecture, modern Android development practices, and real-time AI streaming responses.

Built using **Kotlin and Jetpack Compose**, the project focuses on creating a smooth chat experience similar to modern AI chat applications.

---

# ✨ Features

## AI Chat Experience
- AI powered chat assistant
- Streaming AI response (ChatGPT-style typing)
- Typing indicator while AI responds
- Regenerate AI response
- Stop generating response

## Chat Interaction
- Auto-scroll to latest message
- Scroll-to-bottom floating button
- Copy AI message button
- Markdown rendering support
  - Code blocks
  - Lists
  - Formatted text

## Smart Chat UI
- Real-time message updates
- Smooth chat conversation UI
- Auto-scroll when new messages arrive

## Data Persistence
- Chat history stored locally using **Room Database**

---

# 🏗 Architecture

The project follows **MVVM (Model-View-ViewModel)** architecture to ensure a scalable and maintainable codebase.
UI (Jetpack Compose)
│
ViewModel
│
Repository
│
Data Sources (API + Room Database)

---

# 🛠 Tech Stack

- Kotlin
- Jetpack Compose UI
- MVVM Architecture
- Hilt (Dependency Injection)
- Retrofit 2 (Networking)
- Room Database (Local Storage)
- Gemini AI API
- Kotlin Coroutines & Flow

---

# 📱 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/1c694557-5643-4e8f-8735-c5ea43c793c0" width="250"/>
  <img src="https://github.com/user-attachments/assets/166cb939-e75d-4cf9-9ec2-7474a48dff95" width="250"/>
  <img src="https://github.com/user-attachments/assets/da423cad-c126-487d-957a-9201ebe4a997" width="250"/>
</p>

## App Flow Video

https://github.com/user-attachments/assets/1a4ff273-d975-437f-843b-7de313c51d67



---

# 🚀 Getting Started

## 1. Clone the Repository

git clone https://github.com/yourusername/DroidMentor-AI.git

## 2. Open in Android Studio

Open the project in Android Studio Hedgehog or later.

## 3. Open in Android Studio

Add your Gemini API key in the GeminiApiKey file.

const val API_KEY = "YOUR API KEY"

## 4. Run the App

Build and run the application on an Android device or emulator.

🎯 Purpose of the Project

This project demonstrates how to build a modern AI chat application on Android using Jetpack Compose, clean architecture, local database persistence, and streaming AI responses.

It serves as a reference project for developers interested in building AI-powered Android applications using modern Android technologies.

📄 License

This project is open-source and available under the MIT License.






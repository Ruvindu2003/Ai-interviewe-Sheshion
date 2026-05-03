# 🌌 AI Video Interview System

[![Next.js](https://img.shields.io/badge/Next.js-16-black?style=for-the-badge&logo=next.js)](https://nextjs.org/)
[![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge&logo=react)](https://reactjs.org/)
[![Three.js](https://img.shields.io/badge/Three.js-r184-white?style=for-the-badge&logo=three.js)](https://threejs.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind-4.0-38B2AC?style=for-the-badge&logo=tailwind-css)](https://tailwindcss.com/)

A premium, high-fidelity AI-powered video interview platform. Experience the future of recruitment with a real-time 3D AI agent, advanced speech intelligence, and a sleek, modern interface.

---

## ✨ Key Features

### 🤖 Advanced 3D AI Agent
*   **Real-time Lip-Sync**: Dynamic viseme mapping for realistic speech synchronization.
*   **Expression Engine**: Intelligent state-based expressions (Thinking, Listening, Happy, Serious).
*   **Holographic Projection**: A sophisticated fallback mode with digital "dust" and volumetric glow effects.
*   **Interactive HUD**: Real-time neural link status and core load visualization.

### 🎙️ Speech Intelligence
*   **STT (Speech-to-Text)**: High-accuracy browser-native speech recognition.
*   **TTS (Text-to-Speech)**: Natural-sounding vocal synthesis with neural link synchronization.
*   **`useSpeech` Hook**: A powerful custom abstraction for managing complex audio interactions.

### 🎨 Premium UI/UX
*   **Glassmorphism Design**: Modern, translucent interfaces with deep indigo and emerald color palettes.
*   **Responsive Video Arena**: A unified workspace for candidates to interact with the AI interviewer.
*   **Smooth Animations**: Framer Motion and CSS-based micro-animations for a "live" feel.

---

## 🛠️ Tech Stack

- **Core**: Next.js 16 (App Router), React 19, TypeScript
- **3D**: Three.js, React Three Fiber, @react-three/drei
- **Styling**: Tailwind CSS 4, Lucide React
- **Backend**: Spring AI / Spring Boot 3.x (Integration Ready)

---

## 🚀 Getting Started

### 1. Prerequisites
*   **Node.js**: 20.x or higher
*   **NPM/PNPM**: Latest stable version

### 2. Installation
Clone the repository and install dependencies:
```bash
npm install
```

### 3. Assets & Models
The 3D agent requires specific GLB models. Run the automated downloader to fetch them:
```bash
npm run download-model
```
*Note: If the download fails, the system automatically falls back to a high-fidelity holographic projection.*

### 4. Environment Setup
Create a `.env.local` file in the root directory:
```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api
```

### 5. Launch Development Server
```bash
npm run dev
```
Open [http://localhost:3000](http://localhost:3000) in your browser.

---

## 🏗️ Architecture Highlights

*   **`app/interview`**: The main entry point for the interview session.
*   **`components/VideoArena`**: Manages the orchestration of video feeds and AI logic.
*   **`components/ThreeDAgent`**: The core 3D visualization engine.
*   **`hooks/useSpeech`**: Centralized logic for STT, TTS, and vocal synchronization.

---

## 🛡️ Troubleshooting

*   **Media Permissions**: Ensure the app is running on `localhost` or `https` to access the microphone and camera.
*   **Browser Support**: Chrome is highly recommended for the best Speech Recognition experience.
*   **3D Performance**: If the interface feels sluggish, use the "FORCE HOLOGRAM" mode in the 3D Agent panel.

---

<p align="center">
  Developed with ❤️ for the future of AI Recruitment.
</p>

# Java Group Chat Application

### Developed By: Aniket Kumar Jha

## 📌 Overview
The **Java Group Chat Application** is a console-based, real-time messaging platform built using **Java Socket Programming**.  
It enables multiple clients to connect to a central server and exchange messages instantly over a network.  
With multi-threading, it ensures smooth and responsive communication even when multiple users are active at the same time.

---

## ⚙️ Features
- **Multi-Client Support:** Handles multiple user connections simultaneously.
- **Real-Time Communication:** Messages are instantly broadcast to all connected clients.
- **Join & Exit Notifications:** Notifies users when someone joins or leaves the chat.
- **Server Messaging:** Server can send messages directly to all clients.
- **Timestamps:** Every message is time-stamped for clarity.
- **Graceful Exit:** Clients can disconnect cleanly without affecting others.

---

## 🛠 Tech Stack
- **Language:** Java  
- **Networking:** Java Socket API (`java.net`)  
- **I/O Handling:** `BufferedReader`, `PrintWriter`  
- **Concurrency:** Runnable Interface & Multi-threading  

---

## 🚀 How It Works
1. **Server Startup**
   - Run the server on a specified port (default: `12345`).
2. **Client Connection**
   - Clients connect by entering the server's IP address and port.
3. **Message Exchange**
   - Messages from any client are broadcast to all others in real-time.
4. **User Notifications**
   - Automatic alerts when a user joins or leaves.

---

## 📂 Project Structure
```
Java_Group_Chat_App/
│── GroupChatServer.java   # Server-side logic
│── GroupChatClient.java   # Client-side logic
│── README.md              # Project documentation
```


---

## 🖥 Usage

### 1️⃣ Start the Server
```bash
javac GroupChatServer.java
java GroupChatServer
```

### 2️⃣ Start the Client
```bash
javac GroupChatClient.java
java GroupChatClient
```

### 💡 Applications
Small group communication over LAN
Classroom chat tool
Learning Java networking and multi-threading
Base for GUI-based or secure messaging platforms


### This project is created by Aniket Kumar Jha and is free to use for learning and educational purposes.

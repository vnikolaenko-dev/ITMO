// js/auth.js
const API_BASE = 'http://localhost:8080/'; // Assume base path for API

async function handleLogin(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    // формируем данные как application/x-www-form-urlencoded
    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);

    try {
        const response = await fetch("auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData.toString()
        });

        if (response.ok) {
            const data = await response.json()
            console.log("JWT token:", data.token);
            sessionStorage.setItem('jwtToken', data.token);
            window.location.href = '../main.html';
        } else {
            const data = await response.json()
            document.getElementById("errorMessage").innerText = "Login failed: " + data.message;
        }
    } catch (err) {
        console.error("Error:", err);
    }
}

if (document.getElementById('loginForm')) {
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
}
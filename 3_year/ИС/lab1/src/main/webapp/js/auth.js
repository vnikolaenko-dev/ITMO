// js/auth.js
const API_BASE = 'http://localhost:25114/lab1-1.0-SNAPSHOT/api'; // Assume base path for API

function handleLogin(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    }).then(res => {
        if (res.ok) {
            localStorage.setItem('auth', 'true'); // Simple auth flag
            window.location.href = 'main.html';
        } else {
            document.getElementById('errorMessage').textContent = 'Login failed';
        }
    }).catch(err => console.error(err));
}

function handleRegister(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    fetch(`${API_BASE}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    }).then(res => {
        if (res.ok) {
            window.location.href = 'login.html';
        } else {
            document.getElementById('errorMessage').textContent = 'Registration failed';
        }
    }).catch(err => console.error(err));
}

if (document.getElementById('loginForm')) {
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
} else if (document.getElementById('registerForm')) {
    document.getElementById('registerForm').addEventListener('submit', handleRegister);
}
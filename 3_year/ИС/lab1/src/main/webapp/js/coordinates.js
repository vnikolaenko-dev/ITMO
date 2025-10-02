const API_BASE = 'http://localhost:25114/lab1-1.0-SNAPSHOT/api';

// Загрузка всех координат
async function loadCoordinates() {
    try {
        const response = await fetch(`${API_BASE}/coordinates/get-all`);
        if (response.ok) {
            const coordinates = await response.json();
            displayCoordinates(coordinates);
        } else {
            showError('Failed to load cars');
        }
    } catch (error) {
        showErrorModal('Failed to load coordinates', 'Network error: ' + error.message);
    }
}

// Отображение координат в таблице
function displayCoordinates(coordinates) {
    const tableBody = document.getElementById('coordinatesTableBody');
    tableBody.innerHTML = '';

    coordinates.forEach(coord => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${coord.id}</td>
            <td>${coord.x}</td>
            <td>${coord.y}</td>
            <td>
                <button onclick="deleteCoordinates(${coord.id})" class="secondary-btn">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Создание новых координат
async function createCoordinates() {
    const x = parseFloat(document.getElementById('newCoordX').value);
    const y = parseInt(document.getElementById('newCoordY').value);

    if (isNaN(x) || isNaN(y)) {
        showError('Please enter valid coordinates');
        return;
    }

    if (x > 72) {
        showError('X coordinate cannot be greater than 72');
        return;
    }

    if (y > 78) {
        showError('Y coordinate cannot be greater than 78');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/coordinates/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ x, y })
        });

        if (response.ok) {
            showSuccess('Coordinates created successfully!');
            hideCreateCoordinatesForm();
            document.getElementById('newCoordX').value = '';
            document.getElementById('newCoordY').value = '';
            loadCoordinates();
        } else {
            const error = await response.json();
            showErrorModal("Error creating coordinates", error.message);
        }
    } catch (error) {
        showError('Network error: ' + error.message);
    }
}

// Удаление координат
async function deleteCoordinates(id) {
    try {
        const response = await fetch(`${API_BASE}/coordinates/delete/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showSuccess('Coordinates deleted successfully!');
            loadCoordinates();
        } else {
            const error = await response.json();
            showErrorModal("Error deleting coordinates", error.message);
        }
    } catch (error) {
        showErrorModal("Error deleting coordinates", error.message);
    }
}

// Показать/скрыть форму создания
function showCreateCoordinatesForm() {
    document.getElementById('createCoordinatesForm').style.display = 'block';
}

function hideCreateCoordinatesForm() {
    document.getElementById('createCoordinatesForm').style.display = 'none';
}

// Вспомогательные функции для сообщений
function showError(message) {
    document.getElementById('errorMessage').textContent = message;
    document.getElementById('successMessage').textContent = '';
    setTimeout(() => {
        document.getElementById('errorMessage').textContent = '';
    }, 5000);
}

function showSuccess(message) {
    document.getElementById('successMessage').textContent = message;
    document.getElementById('errorMessage').textContent = '';
    setTimeout(() => {
        document.getElementById('successMessage').textContent = '';
    }, 5000);
}

// Загрузка данных при открытии страницы
document.addEventListener('DOMContentLoaded', loadCoordinates);
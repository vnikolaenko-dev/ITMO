const API_BASE = 'http://localhost:25114/lab1-1.0-SNAPSHOT/api';

// Загрузка всех автомобилей
async function loadCars() {
    try {
        const response = await fetch(`${API_BASE}/car/get-all`);
        if (response.ok) {
            const cars = await response.json();
            displayCars(cars);
        } else {
            showError('Failed to load cars');
        }
    } catch (error) {
        showErrorModal('Failed to load coordinates', 'Network error: ' + error.message);
    }
}

// Отображение автомобилей в таблице
function displayCars(cars) {
    const tableBody = document.getElementById('carsTableBody');
    tableBody.innerHTML = '';

    cars.forEach(car => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${car.id}</td>
            <td>${car.name}</td>
            <td>${car.cool ? 'Yes' : 'No'}</td>
            <td>
                <button onclick="deleteCar(${car.id})" class="secondary-btn">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Создание нового автомобиля
async function createCar() {
    const name = document.getElementById('newCarName').value;
    const cool = document.getElementById('newCarCool').value === 'true';

    if (!name) {
        showError('Car name is required');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/car/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, cool })
        });

        if (response.ok) {
            showSuccess('Car created successfully!');
            hideCreateCarForm();
            document.getElementById('newCarName').value = '';
            loadCars();
        } else {
            const error = await response.json();
            showErrorModal("Error creating car", error.message);
        }
    } catch (error) {
        showError('Network error: ' + error.message);
    }
}

// Удаление автомобиля
async function deleteCar(id) {
    try {
        const response = await fetch(`${API_BASE}/car/delete/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showSuccess('Car deleted successfully!');
            loadCars();
        } else {
            const error = await response.json();
            showErrorModal("Error deleting car", error.message);
        }
    } catch (error) {
        showErrorModal("Error deleting car", error.message);
    }
}

// Показать/скрыть форму создания
function showCreateCarForm() {
    document.getElementById('createCarForm').style.display = 'block';
}

function hideCreateCarForm() {
    document.getElementById('createCarForm').style.display = 'none';
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
document.addEventListener('DOMContentLoaded', loadCars);
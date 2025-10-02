const API_BASE = 'http://localhost:25114/lab1-1.0-SNAPSHOT/api'; // Assume base path for API

// Функции для работы с Coordinates
function toggleCoordinatesInput() {
    const select = document.getElementById('coordinatesSelect');
    const newCoordsDiv = document.getElementById('newCoordinates');

    if (select.value === 'new') {
        newCoordsDiv.style.display = 'block';
    } else {
        newCoordsDiv.style.display = 'none';
    }
}

// Функции для работы с Car
function toggleCarInput() {
    const select = document.getElementById('carSelect');
    const newCarDiv = document.getElementById('newCar');

    if (select.value === 'new') {
        newCarDiv.style.display = 'block';
    } else {
        newCarDiv.style.display = 'none';
    }
}

// Загрузка существующих координат и машин при загрузке страницы
async function loadExistingData() {
    try {
        // Загрузка координат
        const coordsResponse = await fetch(`${API_BASE}/coordinates/get-all`);
        if (coordsResponse.ok) {
            const coordinates = await coordsResponse.json();
            const coordsSelect = document.getElementById('coordinatesSelect');

            coordinates.forEach(coord => {
                const option = document.createElement('option');
                option.value = coord.id;
                option.textContent = `Coordinates (X: ${coord.x}, Y: ${coord.y})`;
                coordsSelect.appendChild(option);
            });
        }

        // Загрузка машин
        const carsResponse = await fetch(`${API_BASE}/car/get-all`);
        if (carsResponse.ok) {
            const cars = await carsResponse.json();
            const carSelect = document.getElementById('carSelect');

            cars.forEach(car => {
                const option = document.createElement('option');
                option.value = car.id;
                option.textContent = `${car.name} (${car.cool ? 'Cool' : 'Not Cool'})`;
                carSelect.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading existing data:', error);
    }
}

// Обновленная функция отправки формы
document.getElementById('createForm').addEventListener('submit', async function(e) {
    e.preventDefault();


    const coordinatesId = document.getElementById("carSelect").value;
    let coordinatesData;
    if (Number.isInteger(Number(coordinatesId))) {
        coordinatesData = {
            id: coordinatesId,
        }
    } else {
        coordinatesData = {
            x: parseFloat(document.getElementById('coordX').value),
            y: parseInt(document.getElementById('coordY').value)
        }
    }

    const carId = document.getElementById("carSelect").value;
    let carData;
    if (Number.isInteger(Number(carId))) {
        carData = {
            id: carId,
        }
    } else {
        carData = {
            name: document.getElementById('carName').value,
            cool: document.getElementById('carCool').value === 'true'
        }
    }

    const formData = {
        name: document.getElementById('name').value,

        coordinates: coordinatesData,

        realHero: document.getElementById('realHero').checked,
        hasToothpick: document.getElementById('hasToothpick').value === 'true',

        car: carData,

        mood: document.getElementById('mood').value,
        impactSpeed: document.getElementById('impactSpeed').value ? parseFloat(document.getElementById('impactSpeed').value) : null,
        minutesOfWaiting: parseFloat(document.getElementById('minutesOfWaiting').value),
        weaponType: document.getElementById('weaponType').value || null
    };

    try {
        const response = await fetch(`${API_BASE}/human-being/save`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            document.getElementById('successMessage').textContent = 'Human being created successfully!';
            document.getElementById('errorMessage').textContent = '';
            setTimeout(() => {
                window.location.href = 'main.html';
            }, 2000);
        } else {
            const error = await response.json();
            // document.getElementById('errorMessage').textContent = error.message || 'Error creating human being';
            showErrorModal("Error creating human being", error.message)
        }
    } catch (error) {
        document.getElementById('errorMessage').textContent = 'Network error: ' + error.message;
    }
});

// Загружаем данные при загрузке страницы
document.addEventListener('DOMContentLoaded', loadExistingData);
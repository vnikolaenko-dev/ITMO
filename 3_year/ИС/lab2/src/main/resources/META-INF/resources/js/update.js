// js/update.js
const API_BASE = 'http://localhost:8080';
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
let human;

function loadOptions() {
    fetch(`${API_BASE}/coordinates/get-all`, {
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(coords => {
            const select = document.getElementById('coordinates');
            coords.forEach(c => {
                const opt = document.createElement('option');
                opt.value = JSON.stringify(c);
                opt.textContent = `X: ${c.x}, Y: ${c.y}`;
                select.appendChild(opt);
            });
        });

    fetch(`${API_BASE}/car/get-all`, {
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(cars => {
            const select = document.getElementById('car');
            cars.forEach(c => {
                const opt = document.createElement('option');
                opt.value = JSON.stringify(c);
                opt.textContent = `Name: ${c.name}, Cool: ${c.cool}`;
                select.appendChild(opt);
            });
        });
}

function loadHuman() {
    // Assuming get by ID endpoint, but backend doesn't have it; simulate or add if needed. For now, assume fetch from all or add endpoint.
    // To keep simple, we'll assume we fetch from main list or implement get by id on backend.
    // For this, let's pretend we have /human-being/get/{id}
    fetch(`${API_BASE}/human-being/get-all`, {
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`,
            'Content-Type': 'application/json'
        }
    }) // Placeholder, filter by id client-side
        .then(res => res.json())
        .then(data => {
            human = data.find(h => h.id == id);
            if (human) {
                document.getElementById('name').value = human.name;
                document.getElementById('coordinates').value = JSON.stringify(human.coordinates);
                document.getElementById('realHero').checked = human.realHero;
                document.getElementById('hasToothpick').value = human.hasToothpick.toString();
                document.getElementById('car').value = JSON.stringify(human.car);
                document.getElementById('mood').value = human.mood;
                document.getElementById('impactSpeed').value = human.impactSpeed;
                document.getElementById('minutesOfWaiting').value = human.minutesOfWaiting;
                document.getElementById('weaponType').value = human.weaponType;
            }
        });
}

document.getElementById('updateForm').addEventListener('submit', (event) => {
    event.preventDefault();
    const humanReq = {
        id: human.id,
        name: document.getElementById('name').value,
        coordinates: JSON.parse(document.getElementById('coordinates').value),
        realHero: document.getElementById('realHero').checked,
        hasToothpick: document.getElementById('hasToothpick').value === 'true',
        car: JSON.parse(document.getElementById('car').value),
        mood: document.getElementById('mood').value,
        impactSpeed: parseFloat(document.getElementById('impactSpeed').value) || null,
        minutesOfWaiting: parseFloat(document.getElementById('minutesOfWaiting').value),
        weaponType: document.getElementById('weaponType').value || null
    };

    fetch(`${API_BASE}/human-being/update`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(humanReq)
    }).then(async res => {
        if (res.ok) {
            window.location.href = 'main.html';
        }
        else {
            const error = await res.json();
            showErrorModal("Error creating human being", error.message)
        }
    }).catch(err => console.error(err));
});

document.addEventListener('DOMContentLoaded', loadOptions);
document.addEventListener('DOMContentLoaded', loadHuman);
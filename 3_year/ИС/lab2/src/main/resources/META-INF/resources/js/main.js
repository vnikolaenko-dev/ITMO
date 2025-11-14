// js/main.js
const API_BASE = 'http://localhost:8080/';
let currentPage = 0;
const pageSize = 5;
let filter = 'null';
let sort = 'id';
let order = 'ASC'

function logout() {
    localStorage.removeItem('auth');
    window.location.href = 'login.html';
}

function loadHumans(page = 0, size = pageSize, filterStr = filter, sortCol = sort, orderType = order) {
    let url = `${API_BASE}/human-being/get-all?page=${page}&size=${size}&sort=${sortCol}&order=${orderType}&nameFilter=${filterStr}`;
    fetch(url, {
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector('#humanTable tbody');
            tbody.innerHTML = '';
            data.forEach(human => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${human.id}</td>
                    <td>${human.name}</td>
                    <td>${human.coordinates ? human.coordinates.x : ''}</td>
                    <td>${human.coordinates ? human.coordinates.y : ''}</td>
                    <td>${human.creationDate}</td>
                    <td>${human.realHero}</td>
                    <td>${human.hasToothpick}</td>
                    <td>${human.car ? human.car.name : ''}</td>
                    <td>${human.car ? human.car.cool : ''}</td>
                    <td>${human.mood}</td>
                    <td>${human.impactSpeed}</td>
                    <td>${human.minutesOfWaiting}</td>
                    <td>${human.weaponType}</td>
                    <td>
                        <a href="view.html?id=${human.id}">View</a>
                        <a href="update.html?id=${human.id}">Update</a>
                        <button onclick="deleteHuman(${human.id})">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
            document.getElementById('pageInfo').textContent = `Page ${page + 1}`;
        }).catch(err => console.error(err));
}

function deleteHuman(id) {
    fetch(`${API_BASE}/human-being/delete/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('jwtToken')}`
        }
    })
        .then(res => {
            if (res.ok) loadHumans();
            else showErrorModal("Error", res.message);
        }).catch(err => console.error(err));
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        loadHumans(currentPage);
    }
}

function nextPage() {
    currentPage++;
    loadHumans(currentPage);
}

function applyFilterAndSort() {
    let selectedValue = document.getElementById('sortColumn').value;
    order = 'ASC'
    if (selectedValue.includes("Reverse")) {
        sort = selectedValue.replace("Reverse", "")
        order = 'DESC'
    }
    loadHumans(0);
}

// Функция для применения фильтра по имени
function applyNameFilter() {
    filter = document.getElementById('filterInput').value;
    if (filter === '') {
        filter = 'null'
    }
    currentPage = 0; // Сбрасываем на первую страницу при новом фильтре
    loadHumans(currentPage);
}

// Добавляем обработчик события для поля ввода
document.addEventListener('DOMContentLoaded', function() {
    const filterInput = document.getElementById('filterInput');

    // Поиск при нажатии Enter
    filterInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            applyNameFilter();
        }
    });

    // Поиск при изменении значения (с задержкой для удобства)
    let filterTimeout;
    filterInput.addEventListener('input', function() {
        clearTimeout(filterTimeout);
        filterTimeout = setTimeout(applyNameFilter, 500); // Задержка 500мс
    });
});

loadHumans();
setInterval(loadHumans, 5000); // Polling for auto-update
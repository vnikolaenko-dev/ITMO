// js/special.js
const API_BASE = 'http://localhost:8080/';

function deleteByImpactSpeed() {
    const speed = document.getElementById('deleteSpeed').value;
    fetch(`${API_BASE}/human-being/delete-with-impact-speed/${speed}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) showErrorModal('Deleted', "Human beings with input impact speed were deleted", { type: 'success' });
            else showErrorModal('Failed', "Something went wrong");
        }).catch(err => console.error(err));
}

function getAvgSpeed() {
    fetch(`${API_BASE}/human-being/avg-speed`)
        .then(res => res.json())
        .then(avg => {
            document.getElementById('avgSpeedResult').textContent = `Average: ${avg}`;
        }).catch(err => console.error(err));
}

function getByWeaponLess() {
    const weapon = document.getElementById('weaponForLess').value;
    fetch(`${API_BASE}/human-being/get-all-weapon/${weapon}`)
        .then(res => res.json())
        .then(data => {
            const div = document.getElementById('weaponLessResult');
            div.innerHTML = data.map(h => `<p>${h.name} - ${h.weaponType}</p>`).join('');
        }).catch(err => console.error(err));
}

function deleteWithoutToothpick() {
    fetch(`${API_BASE}/human-being/delete-without-scraper`, { method: 'DELETE' }) // Assuming scraper means toothpick
        .then(res => {
            if (res.ok) showErrorModal('Deleted', "Human beings without scraper were deleted", { type: 'success' });
            else showErrorModal('Failed', "Something went wrong");
        }).catch(err => console.error(err));
}

function assignLada() {
    fetch(`${API_BASE}/human-being/give-lada`)
        .then(res => {
            if (res.ok) showErrorModal('Give Lada', "Done", { type: 'success' });
            else showErrorModal('Failed', "Something went wrong");
        }).catch(err => console.error(err));
}
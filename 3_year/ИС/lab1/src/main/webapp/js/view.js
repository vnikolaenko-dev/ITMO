// js/view.js (weapon rendering added)

const API_BASE = 'http://localhost:25114/lab1-1.0-SNAPSHOT/api';
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');

if (!localStorage.getItem('auth')) window.location.href = 'login.html';

function loadDetails() {
    fetch(`${API_BASE}/human-being/get-all`)
        .then(res => res.json())
        .then(data => {
            const human = data.find(h => h.id == id);
            if (human) {
                const container = document.getElementById('details');
                if (container) {
                    container.innerHTML = `
                        <div style="display:flex; gap:20px; align-items:flex-start;">
                            <canvas id="pixelCanvas" width="220" height="260"></canvas>
                            <div id="details-text" style="flex:1;"></div>
                        </div>
                    `;
                    const div = container.querySelector('#details-text');
                    div.innerHTML = `
                        <p>ID: ${human.id}</p>
                        <p>Name: ${human.name}</p>
                        <p>Coordinates: X ${human.coordinates.x}, Y ${human.coordinates.y}</p>
                        <p>Creation Date: ${human.creationDate}</p>
                        <p>Real Hero: ${human.realHero}</p>
                        <p>Has Toothpick: ${human.hasToothpick}</p>
                        <p>Car: Name ${human.car.name}, Cool ${human.car.cool}</p>
                        <p>Mood: ${human.mood}</p>
                        <p>Impact Speed: ${human.impactSpeed}</p>
                        <p>Minutes Of Waiting: ${human.minutesOfWaiting}</p>
                        <p>Weapon Type: ${human.weaponType}</p>
                    `;
                }
                renderVisualization(human);
            }
        }).catch(err => console.error(err));
}

function editFromView() {
    window.location.href = `update.html?id=${id}`;
}

function deleteHuman() {
    fetch(`${API_BASE}/human-being/delete/${id}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) window.location.href = 'main.html';
            else alert('Cannot delete: may be linked');
        }).catch(err => console.error(err));
}

// ---------------------------
// Pixel / Mega Man rendering depending on characteristics
// ---------------------------

let _animRequest = null;
let _lastFrameTime = 0;
let _animFrame = 0;

function renderVisualization(human) {
    const canvas = document.getElementById('pixelCanvas');
    if (!canvas) return;
    const ctx = canvas.getContext('2d');

    const pixelSize = 8;
    const scale = Math.max(1, Math.floor(canvas.width / 200));
    const ps = pixelSize * scale;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    const palette = getMegaManPalette(human);

    const spriteWidth = 8 * ps;
    const spriteHeight = 12 * ps;
    const spriteX = Math.floor((canvas.width - spriteWidth) / 2);
    const spriteY = Math.floor((canvas.height - spriteHeight) / 2);

    if (_animRequest) cancelAnimationFrame(_animRequest);
    _lastFrameTime = performance.now();
    _animFrame = 0;

    function loop(now) {
        const elapsed = now - _lastFrameTime;
        if (elapsed > 250) {
            _animFrame = (_animFrame + 1) % 2;
            _lastFrameTime = now;
        }

        ctx.clearRect(0, 0, canvas.width, canvas.height);

        drawEllipse(ctx, canvas.width / 2, spriteY + spriteHeight + 6, spriteWidth * 0.7, ps * 2, 'rgba(0,0,0,0.25)');

        const sprite = chooseMegaManSprite(_animFrame, human);
        drawSpriteFromMap(ctx, sprite.map, palette, spriteX, spriteY, ps);

        if (human.weaponType) drawWeapon(ctx, spriteX, spriteY, ps, human.weaponType);

        if (human.realHero) drawHeroAura(ctx, canvas.width / 2, spriteY + spriteHeight / 2, ps, human.mood);

        _animRequest = requestAnimationFrame(loop);
    }

    _animRequest = requestAnimationFrame(loop);
}

function getMegaManPalette(human) {
    let baseBlue = '#1e5cb6';

    if (human.impactSpeed > 100) baseBlue = '#ff3333';
    else if (human.impactSpeed > 50) baseBlue = '#f57c00';
    else if (human.impactSpeed < 10) baseBlue = '#2b6cf6';

    const accent = human.hasToothpick ? '#00cc66' : '#ffd24d';

    const darkBlue = shadeColor(baseBlue, -25);
    const lightBlue = shadeColor(baseBlue, 25);
    const skin = '#f1c27d';
    const visor = '#a0f0ff';
    const black = '#000000';
    const white = '#ffffff';

    return {
        '0': null,
        '1': darkBlue,
        '2': baseBlue,
        '3': lightBlue,
        '4': skin,
        '5': visor,
        '6': black,
        '7': white,
        '8': accent
    };
}

function shadeColor(color, percent) {
    const num = parseInt(color.replace('#',''),16);
    const r = (num >> 16) + Math.round(255 * (percent/100));
    const g = ((num >> 8) & 0x00FF) + Math.round(255 * (percent/100));
    const b = (num & 0x0000FF) + Math.round(255 * (percent/100));
    const clamp = v => Math.max(0, Math.min(255, v));
    return '#' + ( (1 << 24) + (clamp(r) << 16) + (clamp(g) << 8) + clamp(b) ).toString(16).slice(1);
}

function drawSpriteFromMap(ctx, map, palette, x, y, pixelSize) {
    for (let row = 0; row < map.length; row++) {
        const cols = map[row];
        for (let col = 0; col < cols.length; col++) {
            const key = String(cols[col]);
            const color = palette[key];
            if (!color) continue;
            ctx.fillStyle = color;
            ctx.fillRect(x + col * pixelSize, y + row * pixelSize, pixelSize, pixelSize);
        }
    }
}

function chooseMegaManSprite(frame, human) {
    const idle = [
        [0,0,6,6,6,6,0,0],
        [0,6,2,2,2,2,6,0],
        [6,2,2,2,2,2,2,6],
        [6,2,3,2,2,3,2,6],
        [6,2,4,4,4,4,2,6],
        [6,2,4,5,5,4,2,6],
        [6,2,2,2,2,2,2,6],
        [0,6,1,1,1,1,6,0],
        [0,0,6,1,1,6,0,0],
        [0,0,6,1,1,6,0,0],
        [0,0,6,0,0,6,0,0],
        [0,0,6,0,0,6,0,0]
    ];

    const action = [
        [0,0,6,6,6,6,0,0],
        [0,6,2,2,2,2,6,0],
        [6,2,2,2,2,2,2,6],
        [6,2,3,2,2,3,2,6],
        [6,2,4,4,4,4,2,6],
        [6,2,4,5,5,4,2,6],
        [6,2,2,2,2,2,2,6],
        [0,6,1,1,1,1,6,0],
        [0,0,6,1,1,6,0,0],
        [0,0,6,1,1,6,8,0],
        [0,0,6,0,0,6,8,0],
        [0,0,6,0,0,6,0,0]
    ];

    if (human.mood && human.mood.toLowerCase() === "bad") return { map: action };
    return { map: frame === 0 ? idle : action };
}

function drawWeapon(ctx, spriteX, spriteY, ps, weaponType) {
    const centerX = spriteX + 8 * ps;
    const centerY = spriteY + 6 * ps;

    ctx.fillStyle = '#333';
    ctx.strokeStyle = '#000';

    switch (weaponType) {
        case 'PISTOL':
            ctx.fillStyle = '#666';
            ctx.fillRect(centerX, centerY, 3 * ps, ps);
            ctx.fillRect(centerX + 3 * ps, centerY + ps / 2, ps, ps);
            break;
        case 'MACHINE_GUN':
            ctx.fillStyle = '#444';
            ctx.fillRect(centerX, centerY, 4 * ps, ps);
            ctx.fillRect(centerX + 4 * ps, centerY - ps / 2, ps, 2 * ps);
            break;
        case 'HAMMER':
            ctx.fillStyle = '#8B4513';
            ctx.fillRect(centerX, centerY, ps, 4 * ps);
            ctx.fillStyle = '#aaa';
            ctx.fillRect(centerX - 2 * ps, centerY - ps, 5 * ps, ps);
            break;
        case 'AXE':
            ctx.fillStyle = '#8B4513';
            ctx.fillRect(centerX, centerY, ps, 4 * ps);
            ctx.fillStyle = '#bbb';
            ctx.beginPath();
            ctx.ellipse(centerX + ps / 2, centerY, 3 * ps, 2 * ps, 0, 0, Math.PI * 2);
            ctx.fill();
            break;
        case 'BAT':
            ctx.fillStyle = '#deb887';
            ctx.fillRect(centerX, centerY, ps, 6 * ps);
            ctx.fillStyle = '#444';
            ctx.fillRect(centerX, centerY + 5 * ps, ps, ps);
            break;
    }
}

function drawEllipse(ctx, cx, cy, rx, ry, color) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.ellipse(cx, cy, rx, ry, 0, 0, Math.PI * 2);
    ctx.fill();
}

function drawHeroAura(ctx, x, y, ps, mood) {
    if (mood) {
        const m = mood.toUpperCase();
        if (m === 'LONGING') color = 'rgba(255,215,0,0.3)';
        else if (m === 'GLOOM') color = 'rgba(50,150,255,0.3)';
        else if (m === 'RAGE') color = 'rgba(255,0,0,0.3)';
    }

    const radius = 12 * ps;
    const gradient = ctx.createRadialGradient(x, y, ps, x, y, radius);
    gradient.addColorStop(0, color);
    gradient.addColorStop(1, 'rgba(0,0,0,0)');
    ctx.fillStyle = gradient;
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, Math.PI * 2);
    ctx.fill();
}

loadDetails();
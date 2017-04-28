const elements = {
    gps: {
        delay: document.getElementById('gps-delay'),
        status: {
            on: document.getElementById('gps-status-on'),
            off: document.getElementById('gps-status-off')
        },
        update: document.getElementById('gps-update'),
        cords: {
            container: document.getElementById('gps-cords-container'),
            x: document.getElementById('gps-cords-x'),
            y: document.getElementById('gps-cords-y')
        },
        btn: {
            start: document.getElementById('gps-btn-start'),
            stop: document.getElementById('gps-btn-stop')
        }
    },
    math: {
        sum: document.getElementById('math-sum')
    }
};

const WebApp = {
    gpsStart: () => {
        elements.gps.status.on.classList.remove('hide');
        elements.gps.status.off.classList.add('hide');

        elements.gps.cords.container.classList.remove('hide');
        elements.gps.cords.x.innerText = '-';
        elements.gps.cords.y.innerText = '-';

        elements.gps.btn.start.classList.add('hide');
        elements.gps.btn.stop.classList.remove('hide');
    },

    gpsStop: () => {
        elements.gps.status.off.classList.remove('hide');
        elements.gps.status.on.classList.add('hide');

        elements.gps.cords.container.classList.add('hide');

        elements.gps.btn.stop.classList.add('hide');
        elements.gps.btn.start.classList.remove('hide');
    },

    onGpsUpdate: (x, y) => {
        elements.gps.update.classList.remove('hide');
        setTimeout(() => {
            elements.gps.update.classList.add('hide');
        }, 500);

        elements.gps.cords.x.innerText = x;
        elements.gps.cords.y.innerText = y;
    }
};

const gps   = NativeApp.getGps();
const math  = NativeApp.getMath();

elements.gps.delay.value = gps.getDelay();

elements.gps.btn.start.addEventListener('click', (e) => {
    gps.start();
    WebApp.gpsStart();
});
elements.gps.btn.stop.addEventListener('click', (e) => {
    gps.stop();
    WebApp.gpsStop();
});

elements.gps.delay.addEventListener('change', (e) => {
    gps.setDelay(e.target.value);
});

elements.math.sum.addEventListener('click', (e) => {
    alert(math.sum([1, 3.14, 'jazzy']));
});

gps.onUpdate('WebApp.onGpsUpdate');




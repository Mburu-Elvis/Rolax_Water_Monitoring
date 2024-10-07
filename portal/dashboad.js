app.controller("DashboardController", ["$scope", "$http", "$route", "$location", "$websocket", function($scope, $http, $route, $location, $websocket) {

    let waterFlowChart, consumptionChart, historyChart, anomalyChart;

    const data = [];
    const labels = [];
    const THRESHOLD = 53;

    let currentTime = new Date();

    $scope.fetchSensorReading = function() {
        const apiUrl = 'https://stallion-holy-informally.ngrok-free.app/sensor-average';
        const alertDiv = document.getElementById('alertDiv');
        const details = document.getElementById('anomalyDetails');
    
        $http({
            method: 'GET',
            url: apiUrl,
            mode: 'no-cors',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            // Success callback
            console.log("Response:", response); 
            if (response.status === 200) { 
                const reading = response.data; 
                console.log("Reading:", reading);
                updateChart(reading.average);

                if (reading.average < THRESHOLD) {
                    console.log("Readings lower than threshold");
                    alertDiv.style.display = 'block';
                    details.innerHTML = `<li>Current Reading: ${reading.average}</li>`
                } else {
                    alertDiv.style.display = 'none';
                }
            } else {
                console.error("Error fetching sensor reading:", response.status, response.statusText);
            }
        }, function(error) {
            console.error("Error fetching sensor reading:", error);
        });
    };

    // Function to update the chart
    function updateChart(reading) {
        const now = new Date();
        
        data.push(reading); 
        labels.push(now.toLocaleTimeString()); 
        removeOldData();
    
        waterFlowChart.update();
    }

    function removeOldData() {
        const oneHourAgo = new Date();
        oneHourAgo.setMinutes(oneHourAgo.getMinutes() - 60);
    
        // Filter out readings older than 1 hour
        while (labels.length > 0 && new Date(labels[0]) < oneHourAgo) {
            labels.shift(); 
            data.shift();
        }
    }
    
    const ctx = document.getElementById('myChart').getContext('2d');
    waterFlowChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: 'Water Flow',
                data: data,
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Time'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Flow Rate (L/s)'
                    }
                }
            }
        },
        elements: {
            point: {
                radius: 5,
                hoverRadius: 7
            }
        }
    });

    // Initialize Map
    var map = L.map('map').setView([-1.082061, 36.99866], 14);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    var coordinates =  [
        [-1.105081, 37.01678],
        [-1.1019290, 37.0143900],
        [-1.082061, 36.99866],		
		[-1.0820610, 36.9986603],
		[-1.076808, 37.007061],
        [-1.088914, 37.017825],
        [-1.089655, 37.017383],
        [-1.092733, 37.020014]
    ];
    
    coordinates.forEach(function(coord) {
        L.marker([coord[0] - 0.00001, coord[1]]).addTo(map);
        L.circle([coord[0] - 0.001, coord[1]], {
            color: 'red',
            fillColor: '#f03'
        }).addTo(map);
    });

    // Initialize Consumption Chart
    const ctxConsumption = document.getElementById('consumptionChart').getContext('2d');
    consumptionChart = new Chart(ctxConsumption, {
        type: 'bar',
        data: {
            labels: ['January', 'February', 'March', 'April', 'May'],
            datasets: [{
                label: 'Water Consumption (m³)',
                data: [300, 400, 350, 450, 420],
                backgroundColor: '#0066cc',
                borderColor: '#004c99',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Initialize Historical Data Chart
    const ctxHistory = document.getElementById('historyChart').getContext('2d');
    historyChart = new Chart(ctxHistory, {
        type: 'line',
        data: {
            labels: ['2019', '2020', '2021', '2022', '2023'],
            datasets: [{
                label: 'Water Flow Over Time',
                data: [200, 250, 300, 280, 320],
                borderColor: '#0099cc',
                backgroundColor: 'rgba(0, 153, 204, 0.2)',
                fill: true,
                tension: 0.3
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Initialize Anomaly Detection Chart
    const ctxAnomaly = document.getElementById('anomalyChart').getContext('2d');
    anomalyChart = new Chart(ctxAnomaly, {
        type: 'radar',
        data: {
            labels: ['Sensor 1', 'Sensor 2', 'Sensor 3', 'Sensor 4'],
            datasets: [{
                label: 'Anomaly Score',
                data: [0.2, 0.3, 0.4, 0.1],
                borderColor: '#ff6600',
                backgroundColor: 'rgba(255, 102, 0, 0.2)',
                fill: true
            }]
        },
        options: {
            scales: {
                r: {
                    suggestedMin: 0,
                    suggestedMax: 1
                }
            }
        }
    });

    $scope.fetchSensorReading();
    setInterval($scope.fetchSensorReading, 0.5 * 60 * 1000); 
}]);

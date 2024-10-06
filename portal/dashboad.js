app.controller("DashboardController", ["$scope", "$http", "$route", "$location", "$websocket", function($scope, $http, $route, $location, $websocket) {
    // const socket = new SockJS('http://localhost:8084/readings-ws'); // Use http:// for SockJS
    // const stompClient = Stomp.over(socket);
    
    // Chart instances
    let waterFlowChart, consumptionChart, historyChart, anomalyChart;

    // stompClient.connect({}, function(frame) {
    //     console.log('Connected: ' + frame);
    //     stompClient.subscribe('/readings/updates', function(message) {
    //         $scope.averageReading = JSON.parse(message.body);
    //         $scope.$apply(); // Ensure AngularJS knows about the change
    //     });
    // });

    // $scope.$on('$destroy', function() {
    //     stompClient.disconnect(); // Disconnect when the controller is destroyed
    //     if (waterFlowChart) waterFlowChart.destroy();
    //     if (consumptionChart) consumptionChart.destroy();
    //     if (historyChart) historyChart.destroy();
    //     if (anomalyChart) anomalyChart.destroy();
    // });

    // function generateWaterFlowData(points) {
    //     const data = [];
    //     const labels = [];
    //     let currentTime = new Date();
    
    //     for (let i = 0; i < points; i++) {
    //         labels.push(currentTime.toTimeString().split(' ')[0]); // Time in HH:MM:SS format
    //         data.push(Math.random() * (10 - 5) + 5); // Random flow between 5 and 10 liters/sec
    //         currentTime.setMinutes(currentTime.getMinutes() + 1); // Increment time by 1 minute
    //     }
    
    //     return { labels, data };
    // }

    const data = [];
    const labels = [];

    // Get the current time for labeling
    let currentTime = new Date();

    $scope.fetchSensorReading = function() {
        const apiUrl = 'https://stallion-holy-informally.ngrok-free.app/sensor-average';
    
        // Make an HTTP GET request using AngularJS $http service
        $http({
            method: 'GET',
            url: apiUrl,
            mode: 'no-cors',
            headers: {
                'ngrok-skip-browser-warning': 'hello',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json' // Set Content-Type correctly
            }
        }).then(function(response) {
            // Success callback
            console.log("Response:", response); // Log the response object for debugging
    
            // Check if the response is OK
            if (response.status === 200) { // AngularJS $http returns a response object with status
                const reading = response.data; // Assuming response.data contains the desired JSON
                console.log("Reading:", reading); // Log the parsed JSON
                updateChart(reading.average); // Update the chart with the average value
            } else {
                console.error("Error fetching sensor reading:", response.status, response.statusText);
            }
        }, function(error) {
            // Error callback
            console.error("Error fetching sensor reading:", error);
        });
    };
    // Function to update the chart
    function updateChart(reading) {
        const now = new Date();
        
        // Add new reading and corresponding timestamp
        data.push(reading); // Add the latest reading to the data array
        labels.push(now.toLocaleTimeString()); // Use the time as the label
    
        // Remove data older than 1 hour
        removeOldData();
    
        // Update the chart
        waterFlowChart.update();
    }

    function removeOldData() {
        const oneHourAgo = new Date();
        oneHourAgo.setMinutes(oneHourAgo.getMinutes() - 60);
    
        // Filter out readings older than 1 hour
        while (labels.length > 0 && new Date(labels[0]) < oneHourAgo) {
            labels.shift(); // Remove the oldest label
            data.shift();   // Remove the oldest data point
        }
    }
    
    // const flowData =  generateWaterFlowData(50);
    
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
                radius: 5, // Size of the points
                hoverRadius: 7 // Size of the points on hover
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

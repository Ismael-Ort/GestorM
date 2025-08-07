// This file contains JavaScript code for the frontend application, handling interactivity and making HTTP requests to the backend.

document.addEventListener('DOMContentLoaded', function() {
    const obrasList = document.getElementById('obras-list');

    // Function to fetch obras from the backend
    function fetchObras() {
        fetch('/api/obras')
            .then(response => response.json())
            .then(data => {
                displayObras(data);
            })
            .catch(error => console.error('Error fetching obras:', error));
    }

    // Function to display obras in the HTML
    function displayObras(obras) {
        obrasList.innerHTML = '';
        obras.forEach(obra => {
            const obraItem = document.createElement('li');
            obraItem.textContent = `${obra.titulo} - ${obra.descripcion}`;
            obrasList.appendChild(obraItem);
        });
    }

    // Initial fetch of obras
    fetchObras();
});
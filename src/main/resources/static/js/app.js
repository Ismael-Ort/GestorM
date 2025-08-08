async function loadShows() {
    const res = await fetch('/api/shows');
    const shows = await res.json();
    renderCards(shows);
    renderTable(shows);
}

function renderCards(shows) {
    const counts = { serie: 0, anime: 0, pelicula: 0 };
    shows.forEach(s => counts[s.type] = (counts[s.type] || 0) + 1);
    const cardContainer = document.getElementById('cards');
    cardContainer.innerHTML = '';
    Object.entries(counts).forEach(([type, count]) => {
        const name = type.charAt(0).toUpperCase() + type.slice(1);
        cardContainer.innerHTML += `\
<div class="col-md-4">\
  <div class="card text-center mb-3">\
    <div class="card-body">\
      <h5 class="card-title">${name}</h5>\
      <p class="card-text">${count}</p>\
    </div>\
  </div>\
</div>`;
    });
}

function renderTable(shows) {
    const tbody = document.querySelector('#showsTable tbody');
    tbody.innerHTML = '';
    shows.forEach(show => {
        tbody.innerHTML += `\
<tr>\
  <td>${show.title}</td>\
  <td>${show.type}</td>\
  <td>${show.status}</td>\
  <td><button class="btn btn-sm btn-danger" onclick="deleteShow(${show.id})">Borrar</button></td>\
</tr>`;
    });
}

async function deleteShow(id) {
    await fetch('/api/shows/' + id, { method: 'DELETE' });
    loadShows();
}

document.getElementById('showForm').addEventListener('submit', async e => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target).entries());
    await fetch('/api/shows', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    e.target.reset();
    loadShows();
});

loadShows();

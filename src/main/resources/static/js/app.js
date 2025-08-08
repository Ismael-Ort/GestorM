const params = new URLSearchParams(location.search);
const state = {
    page: Number(params.get('page')) || 0,
    pageSize: Number(params.get('pageSize')) || 20,
    sort: params.get('sort') || 'title,asc',
    type: params.get('type') || '',
    status: params.get('status') || '',
    search: params.get('search') || ''
};

function updateURL() {
    const p = new URLSearchParams();
    if (state.page) p.set('page', state.page);
    p.set('pageSize', state.pageSize);
    p.set('sort', state.sort);
    if (state.type) p.set('type', state.type);
    if (state.status) p.set('status', state.status);
    if (state.search) p.set('search', state.search);
    history.replaceState(null, '', '?' + p.toString());
}

async function loadStats() {
    const res = await fetch('/api/shows/stats');
    const stats = await res.json();
    const cardContainer = document.getElementById('cards');
    cardContainer.innerHTML = '';
    ['serie', 'anime', 'pelicula'].forEach(type => {
        const name = type.charAt(0).toUpperCase() + type.slice(1);
        const count = stats[type] || 0;
        cardContainer.innerHTML += `\
<div class="col-12 col-md-4">\
  <div class="card text-center mb-3 shadow-sm">\
    <div class="card-body">\
      <h5 class="card-title">${name}</h5>\
      <p class="card-text fs-4">${count}</p>\
    </div>\
  </div>\
</div>`;
    });
}

async function loadShows() {
    updateURL();
    const q = new URLSearchParams();
    q.set('page', state.page);
    q.set('pageSize', state.pageSize);
    q.set('sort', state.sort);
    if (state.type) q.set('type', state.type);
    if (state.status) q.set('status', state.status);
    if (state.search) q.set('search', state.search);
    const res = await fetch('/api/shows?' + q.toString());
    const page = await res.json();
    renderTable(page.content);
    renderPagination(page);
    renderRange(page);
}

function renderTable(shows) {
    const tbody = document.querySelector('#showsTable tbody');
    tbody.innerHTML = '';
    shows.forEach(show => {
        tbody.innerHTML += `\
<tr>\
  <td><img src="https://via.placeholder.com/60x90?text=No+Img" class="img-fluid" loading="lazy"></td>\
  <td>${show.title}</td>\
  <td>${show.type}</td>\
  <td>${show.status}</td>\

  <td>${show.description || ''}</td>\

  <td>-</td>\

  <td class="text-end">\
    <div class="btn-group btn-group-sm">\
      <button class="btn btn-outline-secondary" disabled>Ver</button>\
      <button class="btn btn-outline-secondary" disabled>Editar</button>\
      <button class="btn btn-outline-danger" onclick="deleteShow(${show.id})">Eliminar</button>\
    </div>\
  </td>\
</tr>`;
    });
}

function renderPagination(page) {
    const pag = document.getElementById('pagination');
    let html = '';
    html += `<li class="page-item ${page.first ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${page.number - 1}">Anterior</a></li>`;
    for (let i = 0; i < page.totalPages; i++) {
        if (i === 0 || i === page.totalPages - 1 || Math.abs(i - page.number) <= 1) {
            html += `<li class="page-item ${i === page.number ? 'active' : ''}"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`;
        } else if (i === 1 && page.number > 2) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        } else if (i === page.totalPages - 2 && page.number < page.totalPages - 3) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
    }
    html += `<li class="page-item ${page.last ? 'disabled' : ''}"><a class="page-link" href="#" data-page="${page.number + 1}">Siguiente</a></li>`;
    pag.innerHTML = html;
    pag.querySelectorAll('a.page-link').forEach(a => {
        a.addEventListener('click', e => {
            e.preventDefault();
            const p = Number(a.dataset.page);
            if (!isNaN(p)) {
                state.page = p;
                loadShows();
            }
        });
    });
}

function renderRange(page) {
    const start = page.number * page.size + 1;
    const end = start + page.content.length - 1;
    document.getElementById('rangeInfo').textContent = `Mostrando ${start}-${end} de ${page.totalElements} • Página ${page.number + 1} de ${page.totalPages}`;
}

async function deleteShow(id) {
    await fetch('/api/shows/' + id, { method: 'DELETE' });
    loadStats();
    loadShows();
}

document.getElementById('search').value = state.search;
document.getElementById('typeFilter').value = state.type;
document.getElementById('statusFilter').value = state.status;
document.getElementById('sort').value = state.sort;
document.getElementById('pageSize').value = state.pageSize;

document.getElementById('search').addEventListener('input', e => {
    state.search = e.target.value;
    state.page = 0;
    loadShows();
});

document.getElementById('typeFilter').addEventListener('change', e => {
    state.type = e.target.value;
    state.page = 0;
    loadShows();
});

document.getElementById('statusFilter').addEventListener('change', e => {
    state.status = e.target.value;
    state.page = 0;
    loadShows();
});

document.getElementById('sort').addEventListener('change', e => {
    state.sort = e.target.value;
    state.page = 0;
    loadShows();
});

document.getElementById('pageSize').addEventListener('change', e => {
    state.pageSize = Number(e.target.value);
    state.page = 0;
    loadShows();
});

loadStats();
loadShows();


document.addEventListener('DOMContentLoaded', loadReceptek);
const API = '/api/recipes';

async function loadReceptek() {
    const tableBody = document.getElementById('recept-table-body');
    try {
        const response = await fetch(API);
        const receptek = await response.json();
        tableBody.innerHTML = '';

        receptek.forEach(recept => {
            const row = tableBody.insertRow();

            row.insertCell().textContent = recept.id;
            row.insertCell().textContent = recept.nev;
            const actionCell = row.insertCell();
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Törlés';
            deleteButton.onclick = () => deleteRecept(recept.id);
            actionCell.appendChild(deleteButton);
        });
    } catch (error) {
        console.error("Hiba a receptek betöltésekor:", error);
        tableBody.innerHTML = `<tr><td colspan="4" style="color: red;">Hiba a betöltés során: ${error.message}</td></tr>`;
    }
}

async function deleteRecept(id) {
    const url = `${API}/deleteById?id=${id}`;
    try {
        const response = await fetch(url, {
            method: 'DELETE',
        });

        if (response.ok) {
            alert('Recept törölve!');
            loadReceptek();
    }} catch (error) {
        alert("HIBA");
    }
}

document.getElementById('login-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => data[key] = value);

    const url = '/auth/bejelentkezes';

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
            credentials: 'include'
        });

        if (response.ok) {
            alert('Sikeres bejelentkezés!');
            window.location.href = '/html/receptfo';
        } else if (response.status === 401) {
            alert('Hibás felhasználónév vagy jelszó!');
        } else {
            alert('Hiba: ' + response.status);
        }

    } catch (error) {
        alert('Hiba: ' + error.message);
    }
});

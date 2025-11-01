document.getElementById('regisztracio-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const data = {};
    formData.forEach((value, key) => data[key] = value);

    const url = '/auth/regisztracio';

    fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    })
    .then(response => {
      if (!response.ok) throw new Error(`Hiba: ${response.status}`);
      alert('Sikeres regisztráció!');
      window.location.href = '/html/bejelentkezes';
    })
    .catch(error => {
      alert('Hiba történt: ' + error.message);
    });
});

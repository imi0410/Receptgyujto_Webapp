const myRecipeList = document.getElementById("my-recipe-list");
const allRecipeList = document.getElementById("all-recipe-list");
const addForm = document.getElementById("add-recipe-form");
const ingredientsContainer = document.getElementById("ingredients-container");
const recipeIdToEdit = document.getElementById("recipe-id-to-edit");
const saveBtn = document.getElementById("save-button");
const updateBtn = document.getElementById("update-recipe-btn");
const titleInput = document.getElementById("title");
const descriptionInput = document.getElementById("description");
const API = "/api/recipes";
const userId = sessionStorage.getItem("userId");


//felhasznalo -----------------------------------

if (!userId) {
    alert("Jelentkezz be!");
    window.location.href = "bejelentkezes.html";
}

function logout() {
    sessionStorage.removeItem("userId");
    alert("Sikeresen kijelentkeztél.");
    window.location.href = "bejelentkezes.html";
}

async function toAdmin(){
try{
    const response = await fetch("adminpage.html");
    if(response.ok){
        window.location.href = "adminpage.html";
    }else{
        alert("Csak adminok léphetnek be ide!");
    }}catch(err){
        alert("Hiba")
    }
}

// navigacio -----------------------------------------

function showSection(sectionId) {
    document.querySelectorAll('section').forEach(section => {
        section.style.display = 'none';
    });

    document.getElementById(sectionId).style.display = 'block';

    if (sectionId === 'add-recipe') {
        saveBtn.style.display = 'inline-block';
        addForm.reset();
        ingredientsContainer.innerHTML = `
            <div class="ingredient-row">
                <input type="text" class="ingredient-name" placeholder="Hozzávaló neve" required>
                <input type="number" class="ingredient-quantity" placeholder="Mennyiség" required>
                <input type="text" class="ingredient-unit" placeholder="Egység (pl. g, db)">
            </div>
        `;
    } else if (sectionId === 'my-recipes') {
        loadMyRecipes();
    } else if (sectionId === 'all-recipes') {
        loadAllRecipes();
    }
}



function addIngredientRow() {
    const newRow = document.createElement("div");
    newRow.className = "ingredient-row";
    newRow.innerHTML = `
        <input type="text" class="ingredient-name" placeholder="Hozzávaló neve" required>
        <input type="number" class="ingredient-quantity" placeholder="Mennyiség" required>
        <input type="text" class="ingredient-unit" placeholder="Egység (pl. g, db)">
        <button type="button" onclick="this.parentNode.remove()">Törlés</button>
    `;
    ingredientsContainer.appendChild(newRow);
}

function createRecipeElement(r, isOwner) {
    const div = document.createElement("div");
    div.className = "recipe";

    const h3 = document.createElement("h3");
    h3.textContent = r.nev;
    div.appendChild(h3);

    const p = document.createElement("p");
    p.textContent = r.leiras;
    div.appendChild(p);

    if (r.hozzavalok && r.hozzavalok.length > 0) {
        const ul = document.createElement("ul");
        ul.innerHTML = "<strong>Hozzávalók:</strong>";
        r.hozzavalok.forEach(ing => {
            const li = document.createElement("li");
            li.textContent = `${ing.mennyiseg} ${ing.mertekegyseg} ${ing.nev}`;
            ul.appendChild(li);
        });
        div.appendChild(ul);
    }

    if (isOwner) {
        const editBtn = document.createElement("button");
        editBtn.textContent = "Szerkesztés";
        editBtn.onclick = () => editRecipe(r.id);
        div.appendChild(editBtn);

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Törlés";
        deleteBtn.onclick = () => deleteRecipe(r.id);
        div.appendChild(deleteBtn);
    }

    return div;
}

// recept betoltes -------------------------------

async function loadMyRecipes() {
    try {
        const res = await fetch(`${API}/user/${userId}`);
        if (!res.ok) throw new Error("Nem sikerült betölteni a saját recepteket.");
        const recipes = await res.json();

        myRecipeList.innerHTML = "";
        if (recipes.length === 0) {
            myRecipeList.innerHTML = "<p>Nincsenek még receptjeid. Adj hozzá egyet!</p>";
            return;
        }

        recipes.forEach(r => {
            myRecipeList.appendChild(createRecipeElement(r, true));
        });
    } catch (err) {
        alert("Hiba: " + err.message);
    }
}

async function loadAllRecipes() {
    try {
        const res = await fetch(`${API}`);
        if (!res.ok) throw new Error("Nem sikerült betölteni az összes receptet.");
        const recipes = await res.json();

        allRecipeList.innerHTML = "";
        if (recipes.length === 0) {
            allRecipeList.innerHTML = "<p>Még senki sem adott hozzá receptet.</p>";
            return;
        }

        recipes.forEach(r => {
            const isOwner = r.felhasznaloId == userId;
            allRecipeList.appendChild(createRecipeElement(r, isOwner));
        });
    } catch (err) {
        alert("Hiba: " + err.message);
    }
}

// adatok ----------------------------------------------------

function getRecipeDataFromForm() {
    const title = titleInput.value.trim();
    const description = descriptionInput.value.trim();
    const id = recipeIdToEdit.value.trim();

    const ingredients = [];
    document.querySelectorAll('#ingredients-container .ingredient-row').forEach(row => {
        const nameInput = row.querySelector('.ingredient-name');
        const quantityInput = row.querySelector('.ingredient-quantity');
        const unitInput = row.querySelector('.ingredient-unit');

        const name = nameInput ? nameInput.value.trim() : '';
        const quantity = quantityInput ? parseFloat(quantityInput.value) : NaN;
        const unit = unitInput ? unitInput.value.trim() : '';

        if (name && !isNaN(quantity) && quantity > 0) {
            ingredients.push({
                nev: name,
                mennyiseg: quantity,
                mertekegyseg: unit
            });
        }
    });

    if (!title || !description || ingredients.length === 0) {
        alert("Kérlek adj meg címet, leírást és legalább egy hozzávalót (pozitív mennyiséggel)!");
        return null;
    }

    const recipeData = {
        nev: title,
        leiras: description,
        felhasznaloId: userId,
        hozzavalok: ingredients
    };

    if(id){
        recipeData.id = parseInt(id);
    }

    return recipeData;
}

// recept hozzaadas -------------------------------------------------

addForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    console.log("RECIPE FORM DATA:", getRecipeDataFromForm());

    const recipe = getRecipeDataFromForm();
    if (!recipe) return;

    try {
        const res = await fetch(`${API}/save`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(recipe)
        });

        const text = await res.text();
        console.log("UPDATE RESPONSE:", res.status, text);

        if (res.ok) {
            alert("Recept sikeresen mentve!");
            recipeIdToEdit.value = "";
            saveBtn.textContent = 'Recept mentése';
            showSection('my-recipes');
        } else {
            alert("Hiba a mentés közben!");
        }
    } catch (err) {
        alert("Hiba: " + err.message);
    }
});


// recept törlés -----------------------------

async function deleteRecipe(id) {
    if (!confirm("Biztosan törölni szeretnéd ezt a receptet?")) return;

    try {
        const res = await fetch(`${API}/deleteById?id=${id}`, {
            method: "DELETE",
            credentials: "include"
        });

        if (res.ok) {
            alert("Recept törölve!");
            loadMyRecipes();
            loadAllRecipes();
        } else {
            alert("Nem sikerült törölni a receptet!");
        }
    } catch (err) {
        alert("Hiba: " + err.message);
    }
}


// recept edit -------------------------------------------------------

async function editRecipe(id) {
    let originalRecipe;
    try {
        const fetchRes = await fetch(`${API}/byId?id=${id}`);
        if (!fetchRes.ok) throw new Error("Nem sikerült lekérni a receptet a szerkesztéshez.");
        originalRecipe = await fetchRes.json();
    } catch (err) {
        alert("Hiba a recept lekérése közben: " + err.message);
        return;
    }

    showSection('add-recipe');
    titleInput.value = originalRecipe.nev;
    descriptionInput.value = originalRecipe.leiras;
    recipeIdToEdit.value = id;

    ingredientsContainer.innerHTML = '';

    originalRecipe.hozzavalok.forEach(ing => {
        const newRow = document.createElement("div");
        newRow.className = "ingredient-row";
        newRow.innerHTML = `
            <input type="text" class="ingredient-name" placeholder="Hozzávaló neve" required value="${ing.nev || ''}">
            <input type="number" class="ingredient-quantity" placeholder="Mennyiség" required value="${ing.mennyiseg || ''}">
            <input type="text" class="ingredient-unit" placeholder="Egység (pl. g, db)" value="${ing.mertekegyseg || ''}">
            <button type="button" onclick="this.parentNode.remove()">Törlés</button>
        `;
        ingredientsContainer.appendChild(newRow);
    });
    if (!originalRecipe.hozzavalok || originalRecipe.hozzavalok.length === 0) {
        addIngredientRow();
    }

}

loadMyRecipes();
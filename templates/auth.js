function drawAuth() {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
    `;
}

function parseAuthToJson() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const formData = {
        username: username,
        password: password
    };
    return JSON.stringify(formData);
}
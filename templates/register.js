function drawRegister() {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br>

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <label for="confirm_password">Repeat password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required><br>
    `;
}

function parseRegisterToJson() {
    const name = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirm_password = document.getElementById('confirm_password').value;
    const formData = {
        name: name,
        username: username,
        password: password,
        confirmPassword: confirm_password
    };
    return JSON.stringify(formData);
}
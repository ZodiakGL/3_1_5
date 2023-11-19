async function thisUser() {
    fetch("http://localhost:8080/api/user/info")
        .then(res => res.json())
        .then(data => {
            // Добавляем информацию в шапку
            $('#headerUsername').append(data.name);
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#headerRoles').append(roles);

            //Добавляем информацию в таблицу
            let user = `$(
            <tr>
                <td>${data.name}</td>
                <td>${data.mail}</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}

$(async function() {
    await thisUser();
});

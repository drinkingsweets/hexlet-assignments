@import exercise.model.User
@param User USER

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Details</title>
    <style>
        .user-details {
            text-align: center;
            margin-top: 50px;
            font-size: 18px;
        }
        .user-details h2 {
            margin-bottom: 20px;
        }
        .user-details p {
            margin: 10px 0;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="user-details">
        <h2>User Details</h2>
        <p><strong>ID:</strong> ${USER.getId()}</p>
        <p><strong>First Name:</strong> ${USER.getFirstName()}</p>
        <p><strong>Last Name:</strong> ${USER.getLastName()}</p>
        <p><strong>Email:</strong> ${USER.getEmail()}</p>
        <a href="/users">Back to Users List</a>
    </div>
</body>
</html>

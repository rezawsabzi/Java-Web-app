
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Login</title>

    <style>


        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }


        html {
            font-size: 62.5%;
        }

        body {
            font-family: Estedad, sans-serif;
            padding: 3rem;
        }

        .container {
            padding: 2rem;
            width: 35%;
            border-radius: 1.7rem;
        }

        .container-sign-up {
            background-color: #a6773c;
            border: 3px solid #465a86;
        }

        .container-sign-in {
            background-color: #465a86;
        }

        .form__group {
            display: flex;
            flex-direction: column;
            margin-bottom: 1rem;
        }

        .form__group:last-child {
            margin-bottom: 1rem;
        }

        h2 {
            text-align: center;
            font-size: 3rem;

            margin-bottom: 2rem;
        }

        .heading-login {
            color: #ffd8a8;
        }

        .heading-sign-up {
            color: #333;
        }

        .form__input {
            font-family: inherit;
            color: inherit;

            font-size: 1.5rem;
            padding: 1rem;
            border-radius: 2px;
            background-color: rgba(#fff, 0.5);
            border: none;

            border-bottom: 5px solid transparent;

            width: 100%;
            display: block;

            transition: all 0.3s;
        }


        .form__input:focus {
            outline: none;
            box-shadow: 0 1rem 2rem rgba(#000, 0.1);
        }

        .form__input::-webkit-input-placeholder {
            color: #b39776;
        }

        .form__input:placeholder-shown + .form__label {
            opacity: 0;
            visibility: hidden;
            transform: translateY(-4rem);
        }

        .form__label {
            font-size: 1.2rem;
            font-weight: 700;
            margin-top: 0.7rem;

            display: block;

            transition: all 0.3s;
        }


        .btn:link,
        .btn:visited {
            width: 100%;
            display: inline-block;
            text-decoration: none;
            color: #333;
            background-color: #ffd8a8;
            padding: 1rem 3rem;
            text-align: center;
            font-size: 1.8rem;
            font-weight: 700;
            margin-bottom: 2rem;
            transition: all 0.3s;
        }

        .btn-sign-up:link,
        .btn-sign-up:visited {
            border: 2px solid #333;
        }

        .btn-sign-up:hover,
        .btn-sign-up:active {
            background-color: #333;
            color: #ffd8a8;
        }

        .btn-login {
            border-radius: 1.2rem;

        }
        .btn-login:hover,
        .btn-login:active {
            background-color: #ffa64d;
        }


        .detail {
            display: flex;
            justify-content: space-between;
            font-size: 1.6rem;
        }

        .detail__link:link,
        .detail__link:visited {
            display: inline-block;
            text-decoration: none;
            color: #ffd8a8;
            border-bottom: 1px solid transparent;
        }
        .detail__link:hover,
        .detail__link:active {
            border-bottom: 1px solid #b39776;
        }


        body {
            background-color: #6c796b;
            display: block;
        }

        .form__input:focus {
            border-bottom: 5px solid #c2dd92;
        }

        .form__input:focus:invalid {
            border-bottom: 5px solid #ffca94;
        }

        .form__label {
            color: #ffe0b9;
        }

        .main-div{
            display: flex;
            flex-direction: column;
            gap: 1rem;
            align-items: center;
            justify-content: center;
            width: 150rem;
            margin: 0 auto;
        }


        .error-div{
            color: #c92a2a;
            font-size: 1.4rem;
            font-weight: 600;
        }



    </style>
</head>
<body>

<div class="main-div">
    <%
        String error = request.getParameter("error");

        if (error!= null && session.getAttribute("admin")!= null) {
            String err = "";
            if (error.equals("2")){
                err = "Invalid Username or Password";
            }

            else if (error.equals("1")){
                err = "Username and Password can not be Null";
            }
    %>
    <div class="error-div">
        <%= err %>
    </div>
    <%
        }
    %>


    <div class="container container-sign-in">
        <h2 class="heading-login">Sign in</h2>
        <form action="login-servlet">
            <div class="form__group">
                <input
                        type="text"
                        name="username"
                        id="username"
                        placeholder="Username"
                        class="form__input"

                />
                <label for="username" class="form__label">Username</label>
            </div>

            <div class="form__group">
                <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="Password"
                        class="form__input"

                />
                <label for="password" class="form__label">Password</label>
            </div>
            <div class="form__group">
                <input type="submit" value="LOGIN" class="btn btn-login" style="padding: 1rem;font-size:1.6rem;font-weight:600">
            </div>

        </form>


    </div>

</div>

</body>
</html>
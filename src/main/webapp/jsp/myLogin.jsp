<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" http-equiv="Content-Type" content="text/html;">
    <title>login</title>
</head>
<body>
    <div class="login">
        <h2>Acced Form</h2>
        <div class="login-top">
            <h1>Login Form</h1>
            <form action="/auth/form" method="post">
                <input type="text" name="username" placeholder="username"/>
                <input type="password" name="password" placeholder="password"/>
                <div style="display: flex">
                    <!--新增图形验证码的输入框-->
                    <input type="text" name="captcha" placeholder="captcha"/>
                    <!--图片指向验证码api-->
                    <img src="/captcha.jpg" alt="captcha" height="50px" width="150px" style="margin-left: 20px"/>
                </div>

                <div class="forgot">
                    <a href="#">forgot password</a>
                    <input type="submit" value="Login"/>
                </div>

            </form>
        </div>
        <div class="login-bottom">
            <h3>New User &nbsp;<a href="#">Register</a> &nbsp Here</h3>
        </div>
    </div>
</body>
</html>
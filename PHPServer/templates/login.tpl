<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Sign in &middot; Cydu's SMS Manager</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/SMSManager/bootstrap/css/bootstrap.css" rel="stylesheet"> 
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 500px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    <link href="/SMSManager/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"> 

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
  </head>

  <body>

    <div class="container">

      <form class="form-signin">
        <h2 class="form-signin-heading">Please sign in Cydu's SMS Manager</h2>
        <input id="email" type="text" class="input-block-level" placeholder="Email address" value="root@cydu.net">
        <input id="password" type="password" class="input-block-level" placeholder="Password">
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me </label>
        <button id="submit_login" class="btn btn-large btn-primary" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->
<script src="/SMSManager/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
$("#submit_login").click(function() {

var email = $("#email").val();
var password = $("#password").val();
    $.ajax({
        type: "POST",
        url: "do_login.php",
        data: "email=" + email + "&password=" + password,
        success: function(result) {
            if(result === '0')
            {
                $(document.location = 'index.php');
            }
            else
            {
                $(document.location = 'index.php');
            }
        }
    });         
    return false;
});
});
</script>
  </body>
</html>

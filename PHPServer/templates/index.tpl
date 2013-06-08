<!DOCTYPE html> 
<html lang="en"> 
<head> 
<meta charset="utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cydu's SMS Manager</title> 
<meta name="description" content="Cydu's SMS Manager">
<link href="/SMSManager/bootstrap/css/bootstrap.css" rel="stylesheet"> 
<link href="/SMSManager/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"> 
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">Cydu's SMS Manager</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
<div class="container">
<h1>Hello from cydu</h1>
<table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Phone Number</th>
            <th>Time</th>
            <th>Text</th>
          </tr>
        </thead>
        <tbody>
{foreach $lines as $line}
	<tr>
	{foreach $line as $l}
		<td>{$l}</td>
	{/foreach}
	</tr>
{/foreach}
        </tbody>
      </table>
</div>
</body>
</html>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body style="background-color:rgb(248, 249, 250)">  
	<div class="container">
	
		<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		  <a class="navbar-brand" href="/springMVC">
		  	BlogWorld
		  	<c:if test="${ user.getRole() == 2 }">
		  		<small style="font-weight:bold;color:red">Hello User</small>
		  	</c:if>
		  	<c:if test="${ user.getRole() == 1 }">
		  		<small style="font-weight:bold;color:red">Hello Admin</small>
		  	</c:if>
		  </a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="collapsibleNavbar">
		    <ul class="navbar-nav">
		    	<c:if test="${ user.getRole() == 1 }">
			  		<li class="nav-link">
			  			<a href="/springMVC/users">users list</a>
			  		</li>
			  	  </c:if>
		    
		    
		      <li class="nav-item dropdown">
			      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
			        Setting
			      </a>
			      <div class="dropdown-menu">
			      	<a class="dropdown-item" href="/springMVC/changepassword">change password</a>
			        <form method="POST" action="/springMVC/logout">
			        	<button class="btn-link dropdown-item" type="submit">logout</button>
			        </form>
			      </div>
			    </li>    
		    </ul>
		  </div>  
		</nav>
	
	
		<div class="jumbotron">
		  <center><h1>Click on create to make new topic</h1>
		  <c:if test="${ not empty success }">
			  <div class="card bg-success text-white">
			    <div class="card-body">${ success }</div>
			  </div>
		  </c:if>
		  <c:if test="${ not empty error }">
			  <div class="card bg-danger text-white">
			    <div class="card-body">${ error }</div>
			  </div>
		  </c:if>
		  <br/>
		  <form action="/springMVC/topics/${topics_id}" method="POST">
			  <div class="form-group">
			    <input type="text" name="topic_name" placeholder="Enter Topic Name" class="form-control" required>
			  </div>
			  <button class="btn btn-primary" type="submit">Create New Topic</button>
		  </form>
		  
		</div>
		
		<br/>
			<div style="padding:2px 10px;border-left:4px solid green">
				<h3>List of Topics</h3>
			</div><br/>
			<div class="list-group">
				
				<c:if test="${ not empty topics }">
					<c:forEach items="${ topics }" var="topic">
			  			<a href="/springMVC/posts/${ topic.id }" class="list-group-item list-group-item-action">
			  				${ topic.text }
			  				<span class="badge badge-primary badge-pill">${ topic.sections_name }</span>
			  				<c:if test="${ user.getId() == topic.users_id || user.getRole() == 1 }">
				  				<form action="/springMVC/topics/topicsDelete/${ topics_id }" method="POST" style="positio:absolute;top:0;float:right">
				  					<input type="hidden" name="delete_id" value="${topic.id}"/>
				  					<button  type="submit" class="btn btn-danger">Delete</button>
				  				</form>
			  				</c:if>
			  			</a>
			  		</c:forEach>
			  	</c:if>	
			  	<c:if test="${ empty topics }">
		  			<center><h3 style="background-color:white;padding:10px">no topics found</h3></center>
		  		</c:if>
		  	</div>
		
	</div>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.2.1.js"
  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
  crossorigin="anonymous"></script>
</head>
<body>
<h1 align = "center">欢迎</h1>

<div id="">
            用户名 :
            <input type="text" name="name" id="name" value="" />
        </div>
        <div id="">
            密 码 :
            <input type="text" name="password" id="password" value="" />
        </div>
          <input type="button" name="" id="denglu" value="登录"  onclick="login()"/>
</body>
<script type="text/javascript">
function login(){
	$.ajax({
	    type: "post",
	    url: "/codeBase/test/login",
	    data: {
	    	name: $("#name").val(),
	    	password: $("#password").val()
	    },
	    dataType: "json",
	    success: function(r) {     
	    	if(r.code == 20000){
	    		window.location = "www.baidu.com";
	    	}else{
				alert(r.msg);
	    	}
	    }
	});
}
</script>
</html>
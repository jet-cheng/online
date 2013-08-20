<!DOCTYPE HTML>
<html>
<head>
<title>WiniOnline Console</title>
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/wini.css">
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
				<a class="brand" href="/index.wini">WiniOnline Console</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="/index.wini" title="Hadoop中文资源网主页，展现最新的hadoop资源以及热门的技术社区" data-toggle="tooltip">控制台</a></li>
                      	<li><a href="http://www.winilog.com" title="本人的技术博客，欢迎大家来访！" data-toggle="tooltip" target="_blank">维尼小弟的博客</a> </li>
                      	<li><a href="http://www.hadoopchina.net" title="Hadoop技术交流" data-toggle="tooltip" target="_blank">Hadoop中文资源网</a> </li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<section>
				<form action="/weixin.wini" method="post">
					<label class="btn btn-danger">${welcome}</label>
					<a href="/weixin.wini" class="btn btn-primary">签名公众平台</a>
					<button type="submit" value="验证公众平台" class="btn btn-primary">验证公众平台</button>
				</form>
			</section>
		</div>
	</div>
</body>
<script type="text/javascript" src="js/bootstrap.js"></script>
</html>

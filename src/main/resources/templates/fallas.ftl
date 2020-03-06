<html>
    <head>
		<meta charset="UTF-8">
        <title>Fallas 2020</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/normalize.css">
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/css/bootstrap.min.css'>
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.4.0/animate.min.css'>
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css'>
		<link rel="stylesheet" href="css/style.css">
		<style>
			* {margin: 0; padding: 0;}
			
			div {
			margin: 20px;
			}
			
			ul {
			list-style-type: none;
			width: 500px;
			}
			
			h3 {
			font: bold 20px/1.5 Helvetica, Verdana, sans-serif;
			}
			
			li img {
			float: left;
			margin: 0 15px 0 0;
			}
			
			li p {
			font: 200 12px/1.5 Georgia, Times New Roman, serif;
			}
			
			li {
			padding: 10px;
			overflow: auto;
			}
			
			li:hover {
			background: #eee;
			cursor: pointer;
			}
		</style>
    </head>
    <body>
	<#if fallasMap??>
		<div>
		<ul>
		<#list fallasMap?values as featureMap>
			<#list featureMap?values as falla>

		<div class="container m-t-md">
			<!-- First row -->
			<div class="row">
				<div class="col-xs-12 col-md-4">
				<!-- Card -->
				<article class="card">
					<a href="${falla.getProperties().getBoceto()}" target="_blank"><img src="${falla.getProperties().getBoceto()}" height="240" width="175"></a>&nbsp;<a href="${falla.getProperties().getBocetoI()}" target="_blank"><img src="${falla.getProperties().getBocetoI()}" height="240" width="175"></a>
					<div class="card-block">
					<h4 class="card-title">${falla.getProperties().getNombre()}</h4>
					<h6 class="text-muted">George Orwell</h6>
					<h6 class="text-muted">Nombre<p class="card-text">Nombre Falla</p></h6>
					<a href="#" class="btn btn-primary">Read more</a>
					</div>
				</article><!-- .end Card -->
				</div>
			</div><!-- .end First row -->
		</div>






			<li>
				<p><h3>${falla.getProperties().getNombre()}</h3></p>
				<p>${falla.getProperties().getSeccion()}</p>
				<p>${falla.getProperties().getFallera()}</p>
				<p>${falla.getProperties().getPresidente()}</p>
				<p>${falla.getProperties().getArtista()}</p>
				<p>${falla.getProperties().getLema()}</p>
				<p>--------------------------------</p>
				<p>${falla.getProperties().getSeccionI()}</p>
				<p>${falla.getProperties().getFalleraI()}</p>
				<p>${falla.getProperties().getPresidenteI()}</p>
				<p><a href="${falla.getProperties().getBoceto()}" target="_blank"><img src="${falla.getProperties().getBoceto()}" height="240" width="175"></a>&nbsp;<a href="${falla.getProperties().getBocetoI()}" target="_blank"><img src="${falla.getProperties().getBocetoI()}" height="240" width="175"></a></p>
			</li>			
			</#list>
		</#list>
		</ul>
		</div>
	</#if>

<!--
		<div>
		<ul>
			<li>
			<img src="http://lorempixum.com/100/100/nature/1" />
			<h3>Headline</h3>
			<p>Lorem ipsum dolor sit amet...</p>
			</li>
			
			<li>
			<img src="http://lorempixum.com/100/100/nature/2" />
			<h3>Headline</h3>
			<p>Lorem ipsum dolor sit amet...</p>
			</li>
		
			<li>
			<img src="http://lorempixum.com/100/100/nature/3" />
			<h3>Headline</h3>
			<p>Lorem ipsum dolor sit amet...</p>
			</li>
		
			<li>
			<img src="http://lorempixum.com/100/100/nature/4" />
			<h3>Headline</h3>
			<p>Lorem ipsum dolor sit amet...</p>
			</li>
		</ul>
		</div>
-->
	</body>
</html>
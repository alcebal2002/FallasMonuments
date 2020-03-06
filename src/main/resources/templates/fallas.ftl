<html>
    <head>
		<meta charset="UTF-8">
        <title>Fallas 2020</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="/fallas-cards/css/normalize.css">
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/css/bootstrap.min.css'>
    	<link rel="stylesheet" href="/fallas-cards/css/style.css">		
    </head>
    <body>
	<#if fallasMap??>
		<#list fallasMap?values as featureMap>
			<#list featureMap?values as falla>

			<div class="container m-t-md">
			<!-- First row -->
			<div class="row">
				<div class="col-xs-12 col-md-4">
				<!-- Card -->
				<article class="card">
					<a href="${falla.getProperties().getBoceto()}" target="_blank"><img class="card-img-top img-responsive" src="${falla.getProperties().getBoceto()}"></a>
					<div class="card-block">
						<h4 class="card-title">${falla.getProperties().getNombre()}</h4>
						<h6 class="text-muted">${falla.getProperties().getLema()}</h6>
						<p class="card-text"><b>Sección: </b>${falla.getProperties().getSeccion()}</p>
						<p class="card-text"><b>Fallera: </b>${falla.getProperties().getFallera()}</p>
						<p class="card-text"><b>Presidente: </b>${falla.getProperties().getPresidente()}</p>
						<p class="card-text"><b>Artista: </b>${falla.getProperties().getArtista()}</p>
					</div>
				</article><!-- .end Card -->
				</div>
				<div class="col-xs-12 col-md-4">
				<!-- Card -->
				<article class="card">
					<a href="${falla.getProperties().getBocetoI()}" target="_blank"><img class="card-img-top img-responsive" src="${falla.getProperties().getBocetoI()}"></a>
					<div class="card-block">
						<h4 class="card-title">${falla.getProperties().getNombre()} (Infantil)</h4>
						<h6 class="text-muted">${falla.getProperties().getLema()}</h6>
						<p class="card-text"><b>Sección: </b>${falla.getProperties().getSeccionI()}</p>
						<p class="card-text"><b>Fallera: </b>${falla.getProperties().getFalleraI()}</p>
						<p class="card-text"><b>Presidente: </b>${falla.getProperties().getPresidenteI()}</p>
						<p class="card-text"><b>Artista: </b>${falla.getProperties().getArtistaI()}</p>
					</div>
				</article><!-- .end Card -->
				</div>
			</div><!-- .end First row -->
			</#list>
		</#list>
	</#if>
	</body>
</html>
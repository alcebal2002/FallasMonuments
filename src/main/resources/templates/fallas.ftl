<html>
    <head>
		<!-- Required meta tags -->
    	<meta charset="utf-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="/fallas-cards/css/normalize.css">
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/css/bootstrap.min.css'>
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.4.0/animate.min.css'>
		<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css'>
	    <link rel="stylesheet" href="/fallas-cards/css/style.css">

		<link rel="stylesheet" media="all" href="https://s3.amazonaws.com/dynatable-docs-assets/css/bootstrap-2.3.2.min.css" />
		<link rel="stylesheet" media="all" href="https://s3.amazonaws.com/dynatable-docs-assets/css/jquery.dynatable.css" />
		<script type="text/javascript" src="https://s3.amazonaws.com/dynatable-docs-assets/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="https://s3.amazonaws.com/dynatable-docs-assets/js/jquery.dynatable.js"></script>

		<title>Fallas 2020</title>
    </head>
    <body>

	<nav class="navbar navbar-light bg-light">
		<form class="form-inline">
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">Nombre Falla: </span>
				</div>
				<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
				<div class="dropdown">
				<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Dropdown
				</button>

				<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
					<button class="dropdown-item" type="button">Action</button>
					<button class="dropdown-item" type="button">Another action</button>
					<button class="dropdown-item" type="button">Something else here</button>
				</div>
				</div>				
			</div>
		</form>
	</nav>
	<br>
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
			</div>
			</#list>
		</#list>
	</#if>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<input type="hidden" id="jsonData" value=${jsonData}>

	<script>
		(function() {
		var myRecords = JSON.parse(document.getElementById("jsonData").value);
		var dynatable = $('#my-final-table').dynatable({
			dataset: {
			records: myRecords
			}
		}).data('dynatable');

		$records.bind('input', function() {
			try {
			var json = JSON.parse($(this).text());
			$records.removeClass('error');

			dynatable.settings.dataset.originalRecords = json;
			dynatable.process();
			} catch(e) {
			$records.addClass('error');
			}
		});
		})();
	</script>
	</body>
</html>
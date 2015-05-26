$(document).ready(function() {
	  
  $('.grids').hide();
  
  /**************************************************************  INTRO  **************************************************************/ 
  $('#api-intro').show();
  $("#rival").hide();
  $("#noticias").hide();
  $('#api-intro .cargando').show();
  
  $('.menu-trigger').click(function() {
	$('.grids').hide();
	$('#api-menu').show();
  });//#menu


  /**************************************************************  RIVAL  **************************************************************/ 
 
  $.ajax({
  	url: 'http://www.antoniolf.es/sporting/rival.json',
	type: 'GET',
	success: function(res) {
    var content = $(res.responseText).text();
	var data = $.parseJSON(content);
	  for (var i=0; i<data.length; i++) {   	  
		var local = '<img src="img/rival/'+data[i].esc1+'.png"/><br>'+data[i].Local+'';
		$('#loc').append(local);
		var info = ''+data[i].Jornada+'<br>'+data[i].Fecha+'<br>'+data[i].Hora+'<br>'+data[i].Estadio+'';
		$('#inf').append(info);
		var visitante = '<img src="img/rival/'+data[i].esc2+'.png"/><br>'+data[i].Visitante+'';
		$('#vis').append(visitante);
      }//#for	
	}//#success
   });//#rival
	
  /**************************************************************  NOTICIAS  **************************************************************/  
    
	$.ajax({
  	url: 'http://www.antoniolf.es/sporting/noticias.json',
	type: 'GET',
	success: function(res) {
    var content = $(res.responseText).text();
	var data = $.parseJSON(content);
	  
	  for (var i=0; i<data.length; i++) {
	
		var fecha = "<p class='fechanot'>"+data[i].Fecha+"<p>";
		var texto = "<p>"+data[i].Texto+"<p>";
		var enlace = "<p style='text-align:center;'><a href='"+data[i].Link+"' class='button' target='_blank'>Visitar</a><p>"
		 
		$("#noticias").append("<tr><td>"+fecha+""+texto+enlace+"</td></tr>");
	  }//#for
	  $('#api-intro .cargando').hide();
	  $("#rival").show();
	  $("#noticias").show();
	}//#success
  });//#noticias
  
/**************************************************************  CALENDARIO  **************************************************************/
  $('#click-calendario').click(function() {
	$('.grids').hide();
	$('#api-calendario').show();
  });//#calendario
  
/**************************************************************  CALEN_IDA  **************************************************************/
  $('#calen_ida').click(function() {
	$("#calendario").empty();
	f_calendario("http://www.antoniolf.es/sporting/ida.json");	
  });//#calen_ida
  
/**************************************************************  CALEN_VUELTA  **************************************************************/
  $('#calen_vue').click(function() {
	$("#calendario").empty();
	f_calendario("http://www.antoniolf.es/sporting/vuelta.json");	
  });//#calen_vuelta


/**************************************************************  CLASIFICACION  **************************************************************/
  $('#click-clasificacion').click(function() {
	$('.grids').hide();
    $('#api-clasificacion').show(); 

    $("#clasificacion").empty();
	$('#api-clasificacion .cargando').show();
	
	$.ajax({
  	url: 'http://www.antoniolf.es/sporting/clasificacion.json',
	type: 'GET',
	success: function(res) {
    var content = $(res.responseText).text();
	var data = $.parseJSON(content);
    $("#clasificacion").append("<th>Pos</th><th>Equipo</th><th>Pts</th><th>PJ</th><th>PG</th><th>PE</th><th>PP</th><th>GF</th><th>GC</th>");
	  for (var i=0; i<data.length; i++) {
		if (data[i].Pos==1 || data[i].Pos==2){
			var pos = "<td class='verdon'>"+data[i].Pos+"</td>";
		}else if(data[i].Pos>=3 && data[i].Pos<=6){
			var pos = "<td class='verde'>"+data[i].Pos+"</td>";
		}else if(data[i].Pos>=19 && data[i].Pos<=22){
			var pos = "<td class='rojo'>"+data[i].Pos+"</td>";
		}else{
			var pos = "<td>"+data[i].Pos+"</td>";	
		}
		var equ = "<td>"+data[i].Equipos+"</td>";
		var jug = "<td>"+data[i].J+"</td>";	
		var gan = "<td>"+data[i].G+"</td>";		
		var emp = "<td>"+data[i].E+"</td>";	
		var per = "<td>"+data[i].P+"</td>";	
		var gfa = "<td>"+data[i].GF+"</td>";			
		var gco = "<td>"+data[i].GC+"</td>";		
		var pts = "<td><b>"+data[i].Pts+"</b></td>";	  
		
		if (data[i].Equipos=="Sporting"){
			$("#clasificacion").append("<tr>"+pos+"<td style='text-align:center;'><img src='img/escudom.png'/></td>"+pts+jug+gan+emp+per+gfa+gco+"</tr>");
		}else{
			$("#clasificacion").append("<tr>"+pos+equ+pts+jug+gan+emp+per+gfa+gco+"</tr>");
		}
	  }//#for
      $('#api-clasificacion .cargando').hide();
	}//#success
 	});	
  });//#clasificacion
  
  
/**************************************************************  LINKS  **************************************************************/
  $('#click-links').click(function() {
	$('.grids').hide();
	$('#api-links').show();
  });//#links

/**************************************************************  PLANTILLA  **************************************************************/
  $('#click-plantilla').click(function() {
	$('.grids').hide();
	$('#api-porteros').show();
  });//#plantilla

/**************************************************************  PORTEROS  **************************************************************/
  $('.btn_porteros').click(function() {
	$('.grids').hide();
	$('#api-porteros').show();
  });//#defensas

/**************************************************************  DEFENSAS  **************************************************************/
  $('.btn_defensas').click(function() {
	$('.grids').hide();
	$('#api-defensas').show();
  });//#defensas

/**************************************************************  MEDIOS  **************************************************************/
  $('.btn_medios').click(function() {
	$('.grids').hide();
	$('#api-medios').show();
  });//#porteros
  
/**************************************************************  DELANTEROS  **************************************************************/
  $('.btn_delanteros').click(function() {
	$('.grids').hide();
	$('#api-delanteros').show();
  });//#delanteros
  
/**************************************************************  STAFF  **************************************************************/
  $('#click-staff').click(function() {
	$('.grids').hide();
	$('#api-staff').show();
  });//#staff
  
/**************************************************************  FILIAL  **************************************************************/
  $('#click-filial').click(function() {
	$('.grids').hide();
	$('#api-filial').show();
  });//#filial
 
/**************************************************************  EQUIPACION  **************************************************************/
  $('#click-equip').click(function() {
	$('.grids').hide();
	$('#api-equip').show();
  });//#molinon 
  
/**************************************************************  EL MOLINON  **************************************************************/
  $('#click-molinon').click(function() {
	$('.grids').hide();
	$('#api-molinon').show();
  });//#molinon
  
/**************************************************************  MAREO  **************************************************************/
  $('#click-mareo').click(function() {
	$('.grids').hide();
	$('#api-mareo').show();
  });//#molinon
  
/**************************************************************  HIMNO  **************************************************************/
  $('#click-himno').click(function() {
	$('.grids').hide();
	$('#api-himno').show();
  });//#himno
  
/**************************************************************  ESCUDOS  **************************************************************/
  $('#click-escudos').click(function() {
	$('.grids').hide();
	$('#api-escudos').show();
  });//#escudos
  
/**************************************************************  HISTORIA   **************************************************************/
  $('#click-historia').click(function() {
	$('.grids').hide();
	$('#api-historia1').show();
  });//#historia

/**************************************************************  HISTORIA 1  **************************************************************/
  $('.btn_historia1').click(function() {
	$('.grids').hide();
	$('#api-historia1').show();
  });//#historia1
  
/**************************************************************  HISTORIA 2  **************************************************************/
  $('.btn_historia2').click(function() {
	$('.grids').hide();
	$('#api-historia2').show();
  });//#historia2
  
/**************************************************************  HISTORIA 3  **************************************************************/
  $('.btn_historia3').click(function() {
	$('.grids').hide();
	$('#api-historia3').show();
  });//#historia3
  
/**************************************************************  LEYENDAS  **************************************************************/
  $('#click-leyendas').click(function() {
	$('.grids').hide();
	$('#api-leyendas').show();
  });//#leyendas
  
/**************************************************************  ESTADISTICAS  **************************************************************/
  $('#click-estadisticas').click(function() {
	$('.grids').hide();
	$('#api-estadisticas').show();
  });//#estadisticas
  
/**************************************************************  MATCHLIVE  **************************************************************/
  
  $('#click-matchlive').click(function() {
	$('.grids').hide();
	$('#api-matchlive').show();
	$('#api-matchlive .cargando').show();
	
    $.ajax({
  	url: 'http://www.antoniolf.es/sporting/partido.json',
	type: 'GET',
	success: function(res) {
    var content = $(res.responseText).text();
	var data = $.parseJSON(content);
	  for (var i=0; i<data.length; i++) {   	  
		var partido = data[i].url;
		$("#matchlive").append("<iframe id='inscore-xdc-355918' src='"+partido+"' width='300' height='750' frameborder='0' scrolling='no'></iframe>");
      }//#for	
      $('#api-matchlive .cargando').hide();
	}//#success	
   });//#rival
   
});//#matchlive
  
/**************************************************************  FUNCIONES  **************************************************************/
function f_calendario(calen){
	
  $('#api-calendario .cargando').show();
  
  $.ajax({
  	url: calen,
	type: 'GET',
	success: function(res) {
    var content = $(res.responseText).text();
	var data = $.parseJSON(content);
	  $("#calendario").append("<th>Fecha</th><th>Local</th><th>Visit.</th><th>Res.</th>");
	  for (var i=0; i<data.length; i++) {
		var fecha = "<td class='fecha'>"+data[i].Fecha+"</td>";
		if (data[i].Local=="Sporting"){
			var local = "<td style='text-align:center;'><img src='img/escudom.png'/></td>";
		}else{
			var local = "<td style='text-align:center;'>"+data[i].Local+"</td>";
		}
		if (data[i].Visitante=="Sporting"){
			var visitante = "<td style='text-align:center;'><img src='img/escudom.png'/></td>";
		}else{
			var visitante = "<td style='text-align:center;'>"+data[i].Visitante+"</td>";
		}
		if (data[i].RS=="v"){
			var resultado = "<td class='verde' style='text-align:center;'>"+data[i].Resultado+"</td>";
		}else if(data[i].RS=="e"){
			var resultado = "<td class='amarillo' style='text-align:center;'>"+data[i].Resultado+"</td>";
		}else if(data[i].RS=="d"){
			var resultado = "<td class='rojo' style='text-align:center;'>"+data[i].Resultado+"</td>";
		}else{
			var resultado = "<td>"+data[i].Resultado+"</td>";
		} 
		  
		$("#calendario").append("<tr>"+fecha+local+visitante+resultado+"</tr>");
	  }//#for
    $('#api-calendario .cargando').hide();
	}
  });
	
}//#f_calendario
 
 
});//#ready
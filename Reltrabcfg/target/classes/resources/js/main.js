$(document).ready(function() {

	$("#sidebar").mCustomScrollbar();
	//$(".money").maskMoney({showSymbol:false, thousands:'.', decimal:','});
	
});

function hideMenu() {
	if($("#sidebar").width() > 100) {
		$("#sidebar").css('width', '50px');
		$("header").css('width', '50px');
		$("#content").css('width', '100%');
		$(".menuG").hide();
		$(".menuP").show();
		

	} else {
		$("#sidebar").css('width', '250px');
		$("#content").css('width', 'calc(100% - 250px)');
		$("header").css('width', '250px');
		$(".menuP").hide();
		$(".menuG").show();	
	}
	
}

PrimeFaces.locales['pt'] = {  
	closeText: 'Fechar',  
	prevText: 'Anterior',  
	nextText: 'Próximo',  
	currentText: 'Começo',  
	monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
	monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],  
	dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
	dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],  
	dayNamesMin: ['D','S','T','Q','Q','S','S'],  
	weekHeader: 'Semana',  
	firstDay: 1,  
	isRTL: false,  
	showMonthAfterYear: false,  
	yearSuffix: '',  
	timeOnlyTitle: 'Só Horas',  
	timeText: 'Tempo',  
	hourText: 'Hora',  
	minuteText: 'Minuto',  
	secondText: 'Segundo',  
	currentText: 'Data Atual',  
	ampm: false,  
	month: 'Mês',  
	week: 'Semana',  
	day: 'Dia',  
	allDayText : 'Todo Dia'  
};
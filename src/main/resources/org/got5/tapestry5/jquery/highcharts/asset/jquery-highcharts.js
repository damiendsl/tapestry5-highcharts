(function( $ ) {
	globalHighchartsMap = new Array();
	$.extend(Tapestry.Initializer, {
		highcharts : function(spec){
			var params = {};
			var extra = $("#" + spec.id).data('highcharts');
			if(extra) {
				params = extra;
			}
			
			$.extend(params, spec.opt);
			
			chart = new Highcharts.Chart(params);
			globalHighchartsMap[spec.id]=chart;
			 $("#" + spec.id).removeData('highcharts');
		}
	});
}) ( jQuery );
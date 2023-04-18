// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function sortArray(arr) {
  return arr.sort((a, b) => a[0] - b[0]);
}

// Draw the chart and set the chart values
function drawChart() {
  $.ajax({
	url: '/chart'
  }).done(function(counters) {


    let result = [];
     for (let i = 0; i < counters.length; i += 2) {
       result.push([counters[i], counters[i+1]]);
     }
     result = sortArray(result);
     result.reverse();

     topic1 = result[0];
     topic2 = result[1];
     topic3 = result[2];
     topic4 = result[3];
     topic5 = result[4];


	var data = google.visualization.arrayToDataTable([
		['Topic', 'Comments Number'],
		[String(topic1[0]), Number(topic1[1])],
		[String(topic2[0]), Number(topic2[1])],
		[String(topic3[0]), Number(topic3[1])],
		[String(topic4[0]), Number(topic4[1])],
		[String(topic5[0]), Number(topic5[1])]
	]);

	var options = {
	    backgroundColor: 'red',
	    titleTextStyle: {color: 'white'},
		backgroundColor: 'transparent',
		chartArea: {width: '100%', height: '85%'},
		legend: { position: 'right', alignment: 'center', textStyle: { color: 'white', fontSize: 16 } },
		colors: ['#69c0a1', '#6399A4', '#426f76', '#203E4F', '#356E57']
	};

	// Display the chart inside the <div> element with id="piechart"
	var chart = new google.visualization.PieChart(document.getElementById('piechart'));
	chart.draw(data, options);
	window.onresize = RedibujarGrafica;

    function RedibujarGrafica() {
        google.charts.setOnLoadCallback(drawChart);
    };
  });
}

					[this.topicsArray[0][0], this.topicsArray[0][1]],
          [this.topicsArray[1][0], this.topicsArray[1][1]],
          [this.topicsArray[2][0], this.topicsArray[2][1]],
          [this.topicsArray[3][0], this.topicsArray[3][1]],
          [this.topicsArray[4][0], this.topicsArray[4][1]],
import { Component, OnInit } from '@angular/core';
import { ChartType } from 'angular-google-charts';



@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent {
  title = ' Topic popularity';
  data = [
    ['Firefox', 45.0],
   ['IE', 26.8],
   ['Chrome', 12.8],
   ['Safari', 8.5],
   ['Opera', 6.2],
   ['Others', 0.7] 
  ]
  type: ChartType = ChartType.PieChart;
  columnNames = ['Browser', 'Percentage'];
  options = {
  
	  titleTextStyle: {color: 'white'},
		backgroundColor: 'transparent',
		chartArea: {width: '100%', height: '85%'},
		legend: { position: 'right', alignment: 'center', textStyle: { color: 'white', fontSize: 16 } },
		colors: ['#69c0a1', '#6399A4', '#426f76', '#203E4F', '#356E57']
  };
  width = 550;
  height = 400;

}



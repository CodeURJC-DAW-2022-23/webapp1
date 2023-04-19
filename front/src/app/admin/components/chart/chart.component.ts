import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AdminHttpsService } from '../../services/admin-Https-Service';
import { ChartType, Row } from 'angular-google-charts';
declare var google: any;


@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent implements OnInit {
  topicsArray: Array<[string, number]> | undefined
  title: string | undefined;
  type!: ChartType;
  data: Row[] = [];
  options!: object;
  width: number | undefined;
  height: number | undefined;

  constructor(private httpService: AdminHttpsService) { }

  ngOnInit() {

    const map = this.httpService.getTopicsMapped().subscribe(topics => {
      //from object to Map<string,number>
      const topicsMapped = new Map<string, number>(
        Object.entries(topics).reduce((acc, [key, value]) => {
          acc.set(key, value);
          return acc;
        }, new Map<string, number>())
      );
      this.topicsArray = [...topicsMapped.entries()].sort((a, b) => b[1] - a[1]);
      this.data = [
        [this.topicsArray[0][0], this.topicsArray[0][1]],
        [this.topicsArray[1][0], this.topicsArray[1][1]],
        [this.topicsArray[2][0], this.topicsArray[2][1]],
        [this.topicsArray[3][0], this.topicsArray[3][1]],
        [this.topicsArray[4][0], this.topicsArray[4][1]],
      ]


    });

    this.options = {
      titleTextStyle: { color: 'white' },
      backgroundColor: 'transparent',
      chartArea: { width: '100%', height: '85%' },
      legend: { position: 'right', alignment: 'center', textStyle: { color: 'white', fontSize: 16 } },
      colors: ['#69c0a1', '#6399A4', '#426f76', '#203E4F', '#356E57']
    };
    this.title = ' Topic popularity';
    this.type = ChartType.PieChart;
    this.width = 550;
    this.height = 400;

  }


}



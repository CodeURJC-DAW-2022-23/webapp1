import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-icon',
  templateUrl: './icon.component.html',
  styleUrls: ['./icon.component.css'],
})
export class IconComponent implements OnInit {
  @Input()
  glow!: boolean;
  @Input()
  iconName!: string;
  @Input()
  routerLink!: any[];

  constructor() {}

  ngOnInit(): void {}
}

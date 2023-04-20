import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-icon',
  templateUrl: './icon.component.html',
  styleUrls: ['./icon.component.css'],
})
export class IconComponent {
  @Input() glow!: boolean;
  @Input() iconName!: string;
  @Input() routerLink!: string[];
}

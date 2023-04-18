import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { GoogleChartsModule } from 'angular-google-charts';


import { AdminPanelComponent } from './pages/admin-panel/admin-panel.component';
import { ChartComponent } from './components/chart/chart.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';
import { ManageTopicsComponent } from './components/manage-topics/manage-topics.component';

@NgModule({
  declarations: [
    AdminPanelComponent,
    ChartComponent,
    ManageUsersComponent,
    ManageTopicsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    GoogleChartsModule

    ],
  exports: [AdminPanelComponent],
})
export class AdminModule {}

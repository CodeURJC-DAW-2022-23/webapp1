import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminPanelComponent } from './pages/admin-panel/admin-panel.component';
import { ChartComponent } from './components/chart/chart.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';
import { ManageTopicsComponent } from './components/manage-topics/manage-topics.component';

@NgModule({
  declarations: [
    AdminPanelComponent,
    ChartComponent,
    ManageUsersComponent,
    ManageTopicsComponent,
  ],
  imports: [CommonModule],
  exports: [AdminPanelComponent],
})
export class AdminModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NavComponent } from './components/nav/nav.component';
import { SearchComponent } from './components/search/search.component';
import { ErrorComponent } from './pages/error/error.component';
import { IconComponent } from './components/icon/icon.component';

@NgModule({
  declarations: [NavComponent, SearchComponent, ErrorComponent, IconComponent],
  imports: [CommonModule, RouterModule],
  exports: [NavComponent, ErrorComponent, IconComponent],
})
export class SharedModule {}

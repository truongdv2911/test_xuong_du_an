import { Routes } from '@angular/router';
import { HomeComponent } from './conponent/home/home.component';
import { DetailStaffComponent } from './conponent/detail-staff/detail-staff.component';

export const routes: Routes = [
    {
        path: '',
       component: HomeComponent,
   }, 
   {
        path: 'detail/:id',
        component: DetailStaffComponent,
    }, 
];

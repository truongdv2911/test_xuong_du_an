import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './conponent/header/header.component';
import { SidebarComponent } from "./conponent/sidebar/sidebar.component";
import { HomeComponent } from "./conponent/home/home.component";

@Component({
  selector: 'app-root',
  imports: [HeaderComponent, SidebarComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'staff_management';
}

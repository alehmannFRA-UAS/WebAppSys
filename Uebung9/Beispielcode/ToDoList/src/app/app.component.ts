import { Component } from '@angular/core';
import { ToDosComponent } from "./components/to-dos/to-dos.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ToDosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ToDoList';
}

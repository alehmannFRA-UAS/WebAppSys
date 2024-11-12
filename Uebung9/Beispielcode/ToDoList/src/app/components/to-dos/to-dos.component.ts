import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToDo } from "../../models/to-do";

import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-to-dos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './to-dos.component.html',
  styleUrl: './to-dos.component.css'
})
export class ToDosComponent implements OnInit {

  toDos: ToDo[] = [];
  inputToDoText: string = '';

  data: any[]= [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.toDos = [
      {
        content: "Buy milk",
        completed: true
      },
      {
        content: "Do homework",
        completed: false
      },
      {
        content: "Go to the gym",
        completed: false
      }
    ];
    this.fetchRestData();
  }

  toggleCompleted(indexToUpdate: number): void {
    this.toDos.map((item, index) => {
       if (index == indexToUpdate) {
         item.completed = !item.completed;
       }
     })
   }

  deleteToDo(indexToDelete: number): void {
    this.toDos = this.toDos.filter((item, index) => index !== indexToDelete);
  }

  addToDo(): void {
    this.toDos.push({ content: this.inputToDoText, completed: false });
    this.inputToDoText = '';
  }

  fetchRestData(): void {
    this.http.get<any[]>('http://localhost:3000/v1/articles').subscribe((fetchData: any[]) => {
        this.data = fetchData;
    });
  }

}

import { Component , OnInit} from '@angular/core';
import { Room } from '../room';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-room-search',
  templateUrl: './room-search.component.html',
  styleUrl: './room-search.component.css'
})
export class RoomSearchComponent implements OnInit {

  name: any = '';
  rooms: Array<Room> = [];
  selectedRoom: Room | null = null;
  searchedRoom: string = '';

  apiUrl = '/api/users/1/chatrooms';
  
  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    const headers = new HttpHeaders().set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiUrl, {headers}).subscribe({
        next: (res: any) => {
          console.log(res);
          this.rooms = res;
        },
        error: (err: any) => console.log(err.error)
      });
  }

  search(): void { 
    for(const room of this.rooms){
      if(room.name === this.searchedRoom){
        this.select(room);
      }
    }

  }

  select(r: Room): void {
    this.selectedRoom = r;
  }

  createRoom(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
    
    this.httpClient.post<Room>(this.apiUrl, this.name, {headers}).subscribe({
        next: (room) => {
          this.rooms.push(room);
          this.name = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }

}

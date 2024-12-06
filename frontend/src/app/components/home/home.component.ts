import { Component, OnInit } from '@angular/core';
import { Note } from 'src/app/model/note.model';
import { NoteService } from 'src/app/services/note/note.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userId: number = 0;
  text: string = "";
  tag: string = "";

  constructor(public noteService: NoteService) { }

  ngOnInit(): void {
    
  }

  createNote() {
    let note: Note = {
      user_id: this.noteService.userId,
      note_id: null,
      text: this.text,
      tag: this.tag
    };

    this.noteService.create(note);

    this.text = "";
    this.tag = "";
  }

  getNotes() {
    this.noteService.getNotes();
  }

  connect() {
    if(this.userId != 0) {
      this.noteService.userId = this.userId;
      this.noteService.connect();
    }
  }

}

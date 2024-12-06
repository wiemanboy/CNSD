import { Component, Input, OnInit } from '@angular/core';
import { ReceivedNote } from 'src/app/model/received_note.model';
import { NoteService } from 'src/app/services/note/note.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {

  @Input()
  note!: ReceivedNote;
  editing: Boolean = false;

  constructor(public noteService: NoteService) { }

  ngOnInit(): void {

  }

  delete() {
    console.log(this.note.NoteID)
    this.noteService.delete(this.note.NoteID);
  }

  edit() {
    this.editing = true;
  }

  save() {
    this.editing = false;
    this.noteService.edit(this.note);
  }

}

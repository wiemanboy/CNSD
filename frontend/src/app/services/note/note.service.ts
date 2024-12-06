import { TypeModifier } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { NoteEvent } from 'src/app/model/event.model';
import { Note } from 'src/app/model/note.model';
import { ReceivedNote } from 'src/app/model/received_note.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  userId: number = 0;
  webSocket: any;
  notes: Map<String, ReceivedNote> = new Map<String, ReceivedNote>();

  constructor() { }

  connect() {
    this.webSocket = webSocket(environment.webSocketUrl + '?userId=' + this.userId);
    this.webSocket.subscribe({
      next: (event: NoteEvent) => {
        this.listen(event);
      }
    });
  }

  listen(event: any) {
    switch (event.event_type) {
      case 'note_created':
        console.log("Note created!");
        this.notes.set(event.data.NoteID, event.data);
        break;

      case 'note_updated':
        console.log("Note updated!");
        if(this.notes.has(event.data.NoteID)) {
          this.notes.set(event.data.NoteID, event.data);
        }
        break;

      case 'note_deleted':
        console.log("Note deleted!");
        break;

      case 'notes_received':
        console.log("Notes received!");
        for (let note of event.data.Items) {
          if (!this.notes.has(note.NoteID)) {
            this.notes.set(note.NoteID, note);
          }
        }
        break;

      case 'note_received':
        console.log("Note received!");
        console.log(event);
        break;

      default:
        console.log(event);
        break;
    }
  }

  create(note: Note) {
    console.log("Creating note..");

    this.webSocket.next(
      { 
        action: 'post_note', 
        user_id: this.userId,
        text: note.text,
        tag: note.tag
      }
    );
  }

  edit(note: ReceivedNote) {
    console.log("Editing note..");

    this.webSocket.next(
      { 
        action: 'put_note', 
        user_id: this.userId,
        note_id: note.NoteID,
        text: note.Tekst,
        tag: note.Tag
      }
    );
  }

  delete(noteId: String) {
    console.log("Deleting note..");

    this.webSocket.next(
      {
        action: 'delete_note',
        user_id: this.userId,
        note_id: noteId
      }
    );

    this.notes.delete(noteId);
  }

  getNotes() {
    console.log("Retrieving notes..");

    this.webSocket.next(
      { 
        action: 'get_notes', 
        user_id: this.userId
      }
    );
  }

  getNoteById(noteId: number) {
    console.log("Retrieving note..");

    this.webSocket.next(
      { 
        action: 'get_note', 
        user_id: this.userId,
        note_id: noteId
      }
    );
  }
}

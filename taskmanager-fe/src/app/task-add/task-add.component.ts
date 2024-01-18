import { Component } from '@angular/core';
import { TaskAddModule } from './task-add.module';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-task-add',
  standalone: true,
  imports: [TaskAddModule],
  templateUrl: './task-add.component.html',
  styleUrl: './task-add.component.css'
})
export class TaskAddComponent {

  priorities = [
    "TRIVIAL",
    "LOW",
    "MEDIUM",
    "HIGH"
  ]

  task = {
    name: null,
    finishDate: null,
    priority: null,
    goalId: null,
    description: null
  }

  constructor(public dialogRef: MatDialogRef<TaskAddComponent>) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}

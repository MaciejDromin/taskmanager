import { Component, OnInit, Inject } from '@angular/core';
import { TaskAddModule } from './task-add.module';
import { MatDialogRef } from '@angular/material/dialog';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AsyncPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-task-add',
  standalone: true,
  imports: [TaskAddModule, AsyncPipe],
  templateUrl: './task-add.component.html',
  styleUrl: './task-add.component.css'
})
export class TaskAddComponent implements OnInit {

  myControl = new FormControl<string | any>('');
  options: any[] = [];
  filteredOptions: Observable<any[]> = new Observable<any[]>;

  priorities = [
    "TRIVIAL",
    "LOW",
    "MEDIUM",
    "HIGH"
  ]

  task: any = {
    id: null,
    name: null,
    finishDate: null,
    priority: null,
    goal: null,
    description: null,
    status: null
  }

  constructor(public dialogRef: MatDialogRef<TaskAddComponent>, private httpClient: HttpClient, @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data === null) return
    this.httpClient.get("http://localhost:8080/goals/tasks/" + data.id).subscribe(dt => this.task.goal = dt)
    this.task = {
      id: data.id,
      name: data.name,
      finishDate: data.finishDate,
      priority: data.priority,
      description: data.description,
      goal: null,
      status: data.status
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.httpClient.get("http://localhost:8080/goals").subscribe(data => this.options = data as any[])
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );
  }

  displayFn(goal: any): string {
    return goal && goal.name ? goal.name : '';
  }

  private _filter(name: string): any[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }

}

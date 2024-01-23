import { Component, OnInit } from '@angular/core';
import { TaskDetailsModule } from './task-details.module';
import { HttpClient } from '@angular/common/http';
import { RxState } from '@rx-angular/state';
import { ActivatedRoute } from '@angular/router';
import { RxStompService } from '../rx-stomp.service';
import { MatDialog } from '@angular/material/dialog';
import { TaskAddComponent } from '../task-add/task-add.component';
import { SubTaskAddComponent } from '../subtask-add/subtask-add.component';
import { Message } from '@stomp/stompjs'

const priorityIcons: any = {
  HIGH: "keyboard_arrow_up",
  LOW: "keyboard_arrow_down",
  MEDIUM: "menu",
  TRIVIAL: "arrow_downward"
}

const priorityColors: any = {
  HIGH: "cl-red",
  MEDIUM: "cl-orange",
  LOW: "cl-yellow",
  TRIVIAL: "cl-blue"
}

const statusColors: any = {
  NOT_STARTED: "cl-grey",
  ON_HOLD: "cl-orange",
  IN_PROGRESS: "cl-yellow",
  FINISHED: "cl-green"
}

enum Actions {
  EDIT = "Edit",
  CLONE = "Clone",
  ADD_SUBTASK = "Add SubTask"
}

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [TaskDetailsModule],
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css',
  providers: [RxState]
})
export class TaskDetailsComponent implements OnInit {

  taskId = ''

  task$ = this.state.select('task')
  actions$ = this.state.select('actions') 

  constructor(private httpClient: HttpClient,
    private state: RxState<{task: any, actions: any[]}>,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private rxStompService: RxStompService) {
    this.taskId = this.route.snapshot.paramMap.get("taskId")!
    this.state.set({
      task: {
        name: null,
        description: null,
        finishDate: null
      },
      actions: []
    })
  }

  ngOnInit() {
    this.httpClient.get(`http://localhost:8080/tasks/${this.taskId}`)
      .subscribe(data => {
        console.log(data)
        this.state.set('task', (oldState) => oldState.task = data as any)
      })
    this.httpClient.get(`http://localhost:8080/tasks/${this.taskId}/actions`)
      .subscribe(data => this.state.set('actions', (oldState) => oldState.actions = [...data as any[]]))
    this.rxStompService.watch('/taskmngr/subtasks/new').subscribe((goal: Message) => {
      this.state.set('task', (oldState) => {
        oldState.task.subTasks = [...oldState.task.subTasks, JSON.parse(goal.body)]
      })
    })
  }

  determinePriorityIcon(priority: string): string {
    if (priority === undefined) return "highlight_off"
    return priorityIcons[priority]
  }

  determinePriorityColor(priority: string): string {
    if (priority === undefined) return "default-color"
    return priorityColors[priority]
  } 

  determineStatusColor(status: string): string {
    if (status === undefined) return "default-color"
    return statusColors[status]
  } 

  executeAction(action: string): void {
    let task
    this.task$.subscribe(t => task = t)
    switch(action) {
      case Actions.EDIT:
        this.editTask(task)
        break
      case Actions.CLONE:
        this.cloneTask(this.taskId)
        break
      case Actions.ADD_SUBTASK:
        this.addSubtask(task)
        break
    }
  }

  editTask(task: any):void {
    if (task.subTasks !== undefined) {
      this.editActualTask(task)
      return
    }
    this.editSubTask(task)
  }

  editActualTask(task: any):void {
    const dialogRef = this.dialog.open(TaskAddComponent, {
      data: task
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      this.rxStompService.publish({ destination: '/app/tasks/update', body: JSON.stringify({
        id: result.id,
        name: result.name,
        finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
        priority: result.priority,
        description: result.description,
        goalId: typeof result.goal === 'object' ? result.goal.id : null
      }) })

      const updatetaskInState = (oldState: {task: any, actions: any[]}) => {
        return {
          task: {
            id: result.id,
            name: result.name,
            finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
            priority: result.priority,
            description: result.description,
            goalId: typeof result.goal === 'object' ? result.goal.id : null,
            status: result.status,
            subTasks: oldState.task.subTasks
          },
          actions: oldState.actions
        }
      }
  
      this.state.set(updatetaskInState)

    });
  }

  editSubTask(task: any):void {

  }

  cloneTask(taskId: string):void {

  }

  addSubtask(task: any): void {
    const dialogRef = this.dialog.open(SubTaskAddComponent, {
      data: {
        task: task
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      this.rxStompService.publish({ destination: '/app/subtasks/add', body: JSON.stringify({
        name: result.name,
        finishDate: result.finishDate.set({ hour: 12 }).toUTC().toISO(),
        taskId: typeof result.task === 'object' ? result.task.id : null,
        priority: result.priority,
        description: result.description
      }) })
    });
  }

}

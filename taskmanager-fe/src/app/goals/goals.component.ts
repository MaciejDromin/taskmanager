import { Component, OnInit } from '@angular/core';
import { GoalsModule } from './goals.module';
import { RxStompService } from '../rx-stomp.service';
import { RxState } from '@rx-angular/state';
import { Message } from '@stomp/stompjs'
import { MatDialog } from '@angular/material/dialog';
import { GoalAddComponent } from '../goal-add/goal-add.component';

interface Goal {
  id: string,
  name: string,
  creationDate: string,
  finishDate: string
}

@Component({
  selector: 'app-goals',
  standalone: true,
  imports: [GoalsModule],
  templateUrl: './goals.component.html',
  styleUrl: './goals.component.css',
  providers: [RxState]
})
export class GoalsComponent implements OnInit {
  goals$ = this.state.select('goals')

  constructor(private rxStompService: RxStompService, private state: RxState<{goals: any}>, public dialog: MatDialog) {
    this.state.set({
      goals: null
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(GoalAddComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      this.rxStompService.publish({ destination: '/app/goals/add', body: JSON.stringify({
        name: result.name,
        finishDate: result.finishDate.set({ hour: 12 }).toUTC().toISO()
      }) })
    });
  }

  ngOnInit() {
    this.rxStompService.watch('/taskmngr/goals').subscribe((goals: Message) => {
      this.state.set({
        goals: JSON.parse(goals.body)
      })
    })
    this.rxStompService.watch('/taskmngr/goals/new').subscribe((goal: Message) => {
      this.state.set({
        goals: [...this.state.get('goals'), JSON.parse(goal.body)]
      })
    })
    this.rxStompService.publish({ destination: '/app/goals', body: "init" })
  }

}

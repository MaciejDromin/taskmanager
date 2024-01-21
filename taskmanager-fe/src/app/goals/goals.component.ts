import { Component, OnInit } from '@angular/core';
import { GoalsModule } from './goals.module';
import { RxStompService } from '../rx-stomp.service';
import { RxState } from '@rx-angular/state';
import { Message } from '@stomp/stompjs'
import { MatDialog } from '@angular/material/dialog';
import { GoalAddComponent } from '../goal-add/goal-add.component';

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
    const dialogRef = this.dialog.open(GoalAddComponent, {
      data: null
    });

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

  deleteGoal(goalId: string): void {
    this.rxStompService.publish({ destination: '/app/goals/delete', body: goalId})

    const deleteGoalFromState = (oldState: {goals: any}) => {
      return {
        goals: oldState.goals.filter((goal: any) => {
          return goal.id !== goalId
        })
      }
    }

    this.state.set(deleteGoalFromState)
  }

  editGoal(goal: any): void {
    const dialogRef = this.dialog.open(GoalAddComponent, {
      data: goal
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return
      }
      this.rxStompService.publish({ destination: '/app/goals/update', body: JSON.stringify({
        id: result.id,
        name: result.name,
        finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
        creationDate: result.creationDate
      }) })

      const updateGoalInState = (oldState: {goals: any}) => {
        return {
          goals: oldState.goals.map((goal: any) => {
            if (goal.id !== result.id) return goal
            return {
              id: result.id,
              name: result.name,
              finishDate: typeof result.finishDate === 'object' ? result.finishDate.set({ hour: 12 }).toUTC().toISO() : result.finishDate,
              creationDate: result.creationDate
            }
          })
        }
      }
  
      this.state.set(updateGoalInState)

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

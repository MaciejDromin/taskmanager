import { Component, OnInit } from '@angular/core';
import { HomeModule } from './home.module';
import { RxStompService } from '../rx-stomp.service';
import { RxState } from '@rx-angular/state';
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

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HomeModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  providers: [
    RxState
  ]
})
export class HomeComponent implements OnInit {
  dailyPlan$ = this.state.select('dailyPlan')

  constructor(private rxStompService: RxStompService, private state: RxState<{dailyPlan: any}>) {
    this.state.set({
      dailyPlan: null
    })
  }

  ngOnInit(): void {
    this.rxStompService.watch('/taskmngr/daily').subscribe((daily: Message) => {
      this.state.set({
        dailyPlan: JSON.parse(daily.body)
      })
    })
    this.rxStompService.publish({ destination: '/app/daily', body: "init" })
  }

  determinePriorityIcon(priority: string): string {
    return priorityIcons[priority]
  }

  determinePriorityColor(priority: string): string {
    return priorityColors[priority]
  } 

  determineStatusColor(status: string): string {
    return statusColors[status]
  } 

}

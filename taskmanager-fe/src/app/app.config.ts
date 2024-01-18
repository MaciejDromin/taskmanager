import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { RxState } from '@rx-angular/state';
import { routes } from './app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { MatLuxonDateModule } from '@angular/material-luxon-adapter'

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClientModule),
    provideAnimations(),
    importProvidersFrom(MatLuxonDateModule),
    RxState
]
};

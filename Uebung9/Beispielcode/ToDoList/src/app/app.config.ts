import { ApplicationConfig, provideZoneChangeDetection, isDevMode } from '@angular/core';
import { provideServiceWorker } from '@angular/service-worker';

import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideServiceWorker('ngsw-worker.js', {
            enabled: !isDevMode(),
            registrationStrategy: 'registerWhenStable:30000'
    }),
    provideHttpClient()
  ]
};

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';  // Corrected import
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatBadgeModule } from '@angular/material/badge';
import { MatMenuModule } from '@angular/material/menu';  // Corrected import
import { AuthenticationModule } from './modules/authentication/authentication.module';
import { JobportalModule } from './modules/jobportal/jobportal.module';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from './modules/authentication/services/authentication.service';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { LoginComponent } from './modules/authentication/components/login/login.component';
import { ContainerComponent } from './modules/jobportal/components/container/container.component';


@NgModule({
  declarations: [
    AppComponent,

  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    AuthenticationModule,
    JobportalModule,
  
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatMenuModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    HttpClientModule,
    MatIconModule,
    MatBadgeModule
    
    
  ],
  providers: [LoginComponent,ContainerComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }

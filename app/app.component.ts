import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { AuthState, OktaAuth } from '@okta/okta-auth-js';
import { filter, from, map, Observable } from 'rxjs';
import { CartService } from './services/cart.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'okta-angular-quickstart';
  isVisible: boolean = false;

  @ViewChild('userBtn') userButton: ElementRef;

  public isAuthenticated$!: Observable<boolean>;
  public isAdmin$: Observable<boolean>;

  name$!: Observable<String>;

  // Test commit on organization. 
  constructor(private _router: Router, private _oktaStateService: OktaAuthStateService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private cartService:CartService) { }

  public ngOnInit(): void {

    this.isAuthenticated$ = this._oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((s: any) => s.isAuthenticated ?? false)
    );

    this.name$ = this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.name?.split(' ')[0] ?? 'User')
    );

    this.isAdmin$ = this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: any) => authState.idToken?.claims.groups.includes("Admin") ?? false)
    );

    // If the window is clicked and the target isn't the dropdown, if the dropdown is visible/open then close it.
    window.onclick = (e) => {
      if (this.isVisible && e.target !== this.userButton.nativeElement) this.toggleDropdown();
    };
  }

  public async signIn(): Promise<void> {
    // This may be useful in the future: { originalUri: '/' }
    await this._oktaAuth.signInWithRedirect().then(_ => {
      this._router.navigate(['/product']);
    }
    );
  }

  public async signOut(): Promise<void> {
    await this._oktaAuth.signOut();
  }

  toggleDropdown() {
    this.isVisible = !this.isVisible;
    document.getElementById("accountDropdown")?.classList.toggle("show");
  }

  getCartLength(): number {
    return this.cartService.length;
  }

}

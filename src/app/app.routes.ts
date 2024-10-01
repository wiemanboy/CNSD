import {Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {CustomersComponent} from "./customers/customers.component";

export const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {path: "accounts", component: AccountsComponent},
  {path: "customers", component: CustomersComponent}
];

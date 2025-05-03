import { Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {MenuComponent} from "./menu/menu.component";
import {Equipment} from "./models/equipment-model";
import {DepartmentComponent} from "./department/department.component";
import {EquipmentComponent} from "./aff-equipment/equipment.component";
import {PieceComponent} from "./piece/piece.component";
import {StockComponent} from "./stock/stock.component";
import {InterventionComponent} from "./intervention/intervention.component";
import {UsersComponent} from "./users/users.component";
import {ProductionComponent} from "./production/production.component";
import {ProfilComponent} from "./profil/profil.component";
import {AffDepartmentComponent} from "./aff-department/aff-department.component";
import {AddEditUserComponent} from "./add-edit-user/add-edit-user.component";
import {AddEditEquipementComponent} from "./add-edit-equipement/add-edit-equipement.component";
import {AddEditDepartementComponent} from "./add-edit-departement/add-edit-departement.component";
import {AddEditPieceComponent} from "./add-edit-piece/add-edit-piece.component";
import {AddEditInterventionComponent} from "./add-edit-intervention/add-edit-intervention.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {path: "", redirectTo: "/login", pathMatch: 'full'},
  {path:'dashboard', component:DashboardComponent},
  {path:'department', component:DepartmentComponent},
  {path:'equipment', component:EquipmentComponent},
  {path:'piece', component:PieceComponent},
  {path:'menu', component:MenuComponent},
  {path:'stock', component:StockComponent},
  {path:'intervention', component:InterventionComponent},
  {path:'users', component:UsersComponent},
  {path:'production', component:ProductionComponent},
  {path:'production/:id', component:ProductionComponent},
  {path:'profil', component:ProfilComponent},
  { path: 'department/:id', component: AffDepartmentComponent },
  {path:"detailsUser/:id",component:UsersComponent},
  {path:"detailsEquipment/:id",component:EquipmentComponent},
  { path: 'add-editUser', component: AddEditUserComponent },
  { path: 'add-editUser/:id', component: AddEditUserComponent },
  { path: 'add-editEquipement/:id', component: AddEditEquipementComponent },
  {path:"add-editEquipement", component:AddEditEquipementComponent},
  {path:"add-editDepartment", component:AddEditDepartementComponent},
  {path:"add-editPiece", component:AddEditPieceComponent},
  {path:"add-editPiece/:id", component:AddEditPieceComponent},
  {path:"add-editIntervention", component:AddEditInterventionComponent},
  {path:"add-editIntervention/:id", component:AddEditInterventionComponent},
  {path:"detailsPiece/:id",component:PieceComponent},
];

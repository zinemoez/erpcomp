import {UserRole} from "./userRole-model";
import {Poste} from "./poste-model";

export class RegisterRequest {
  id!:number;
  name!:string;
  surname!:string;
  password!:string;
  email!:string;
  naissance!:Date;
  cin!:number;
  departmentId!:string;
  poste!:Poste;
  categorie!:string;
  phoneNumber!:string;
  role!:UserRole;
  adresse!:string
}
